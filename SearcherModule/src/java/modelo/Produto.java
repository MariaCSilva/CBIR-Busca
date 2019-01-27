/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Maria
 */
public class Produto {
    private String nome;
    private Categoria categoria;
    private String preco;
    private String imagemPath; 
    private String loja;    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagemPath() {
        return imagemPath;
    }

    public void setImagemtPath(String imagemPath) {
        this.imagemPath = imagemPath.substring(imagemPath.indexOf("resources"), imagemPath.length());
    }
    
    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", preco=" + preco + ", loja=" + loja + ", pathImagem=" + imagemPath+'}';
    }    
}
