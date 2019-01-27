/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import searchers.Searcher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Resultado;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Maria
 */
@ManagedBean
@ViewScoped
public class ImagemMB {
    
    private UploadedFile uploadedFile;
    private List<Resultado> resultadosCEDD;
    private List<Resultado> resultadosFCTH;
//    private StreamedContent imagemConsulta;
    private String imageFilePathRel;
    public ImagemMB() {
    }
    public void upload(FileUploadEvent event) throws IOException, ParseException{
//        , InterruptedException
        uploadedFile = event.getFile();
//        File directory = new File("C:\\uploads");
        File directory = new File("C:\\Users\\Maria\\Documents\\NetBeansProjects\\SearcherModule\\web\\resources\\uploads");
        directory.mkdirs();
        String filename = FilenameUtils.getBaseName(UUID.randomUUID().toString());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(directory.toPath(), filename + "-", "." + extension);
        Files.copy(uploadedFile.getInputstream(), file, StandardCopyOption.REPLACE_EXISTING);
        
        String imageFilePath = "C:\\Users\\Maria\\Documents\\NetBeansProjects\\SearcherModule\\web\\resources\\uploads\\" + file.getFileName().toString();

           
//        String imageFilePath = "C:\\uploads\\" + file.getFileName().toString();
//        loadImage(imageFilePath,uploadedFile.getContentType());
//        imagemPath.substring(imagemPath.indexOf("resources"), imagemPath.length())
        imageFilePathRel = imageFilePath.substring(imageFilePath.indexOf("resources"), imageFilePath.length());
        Searcher searcher = new Searcher();
        System.out.println("Path Original "+imageFilePath);
        System.out.println("Path upload rel "+imageFilePathRel);
//        System.out.println(imageFilePathRel);
        
        try{            
            resultadosFCTH = searcher.searchFCTH(imageFilePath);
            resultadosFCTH.remove(resultadosFCTH.get(0));
        }catch(IOException e){
             System.out.println(""+e.getMessage());
        }
     
        try{            
            resultadosCEDD = searcher.searchCEDD(imageFilePath);
            resultadosCEDD.remove(resultadosCEDD.get(0));
        }catch(IOException e){
             System.out.println(""+e.getMessage());   
        }
//        System.out.println(imageFilePath);
//        relativeFilePath = "resources\\uploads\\"+file.getFileName().toString();
        //        FacesMessage message = new FacesMessage("Succesful", imageFilePath + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
//        System.out.println(contextPath);



    }

//    public void loadImage(String  caminho, String tipo) throws FileNotFoundException{
//        InputStream in = new FileInputStream(new File(caminho));
//        imagemConsulta = new DefaultStreamedContent(in, tipo, caminho);    
//        
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getImageFilePathRel() {
        return imageFilePathRel;
    }

    public void setImageFilePathRel(String imageFilePathRel) {
        this.imageFilePathRel = imageFilePathRel;
    }

    public List<Resultado> getResultadosCEDD() {
        return resultadosCEDD;
    }

    public void setResultadosCEDD(List<Resultado> resultadosCEDD) {
        this.resultadosCEDD = resultadosCEDD;
    }

    public List<Resultado> getResultadosFCTH() {
        return resultadosFCTH;
    }

    public void setResultadosFCTH(List<Resultado> resultadosFCTH) {
        this.resultadosFCTH = resultadosFCTH;
    }

   
   
}
