/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import static javax.imageio.ImageIO.read;
import modelo.Categoria;
import modelo.Resultado;
import modelo.Produto;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Maria
 */
public class Searcher {

    private Produto produto;    

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }
    
    public List<Resultado>imageSearch(String pathImagem, 
                                      Class metodo, 
                                      String pathIndex) 
        throws IOException, ParseException{
         BufferedImage img = null;
        boolean passed = false;
        if (pathImagem.length() > 0) {
            File f = new File(pathImagem);
            if (f.exists()) {
                try {
                    img = read(f);
                    passed = true;
                    System.out.println(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!passed) {
            System.out.println("Nenhuma Imagem encontrada");
            System.out.println("Executando \"Buscador <consulta imagem>\" procurar por <imagem>.");
            System.exit(1);
        }
        Path p = Paths.get(pathIndex);
        IndexReader ir = DirectoryReader.
            open(FSDirectory.open(p));
        ImageSearcher searcher = 
            new GenericFastImageSearcher(31, metodo);
        ImageSearchHits hits = searcher.search(img, ir);
        List<Resultado> files = new ArrayList<>();
        for (int i = 0; i < hits.length(); i++) {
            String fileName = ir.document(hits.documentID(i)).
                    getValues(DocumentBuilder.
                              FIELD_NAME_IDENTIFIER)[0];
            Resultado result = new Resultado();
            produto = new Produto();
            produto.setImagemtPath(fileName);
            DecimalFormat df = new DecimalFormat("#,###.00");
            String scoreFormatado = df.format(hits.score(i));
            result.setScore(scoreFormatado);
            result.setPosicao(i);
            result.setProduto(produto);
            searchTexto(result);
            files.add(result);
        }
        return files;
    }         

    private void textInfoSearch(Resultado resultado,String indexPath) 
                throws IOException, ParseException {        
            Analyzer analyzer = new StandardAnalyzer();
            Directory directory = (Directory) FSDirectory.
                open(Paths.get(indexPath));
            DirectoryReader ireader = DirectoryReader.
                open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            QueryParser parser = new QueryParser("caminho", analyzer);
            Query query = parser.
                parse(resultado.getProduto().getImagemPath());
            ScoreDoc[] hits = isearcher.
                search(query, null, 1180).scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                Categoria cat = new Categoria();
                if (hitDoc.toString().
                    contains(resultado.getProduto().getImagemPath())) {
                    for (String t : hitDoc.getValues("nome")) {
                        produto.setNome(t);
                    }
                    for (String pc : hitDoc.getValues("peca")) {
                        cat.setPeca(pc);
                    }
                    for (String p : hitDoc.getValues("preco")) {
                        produto.setPreco(p);
                    }
                    for (String l : hitDoc.getValues("loja")) {
                        produto.setLoja(l);
                    }                    
                    for (String estp : hitDoc.getValues("estampa")) {
                        cat.setTipoEstampa(estp);
                    }
                    produto.setCategoria(cat);
                }
            }
            ireader.close();
            directory.close();
        }
    
    public List<Resultado> searchCEDD(String path) throws IOException, ParseException {
            List<Resultado> produtosCEDD;
            produtosCEDD = imageSearch(path, CEDD.class,"C:\\Users\\Maria\\Documents\\NetBeansProjects\\SearcherModule\\web\\resources\\indexCEDD" );
            return produtosCEDD;
        }

        public List<Resultado> searchFCTH(String path) throws IOException, ParseException {

              List<Resultado> produtosFCTH;
              produtosFCTH = imageSearch(path,FCTH.class , "C:\\Users\\Maria\\Documents\\NetBeansProjects\\SearcherModule\\web\\resources\\indexFCTH");            
              return produtosFCTH;

        }

         public void searchTexto(Resultado resultado) throws IOException, ParseException {
            String indexPath = "C:\\Users\\Maria\\Documents\\NetBeansProjects\\SearcherModule\\web\\resources\\indexInfo";         
            textInfoSearch(resultado , indexPath); 
        }
    //   
    }


    
