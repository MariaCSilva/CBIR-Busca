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
public class Categoria {
     private String peca;
    private String tipoEstampa;

    public String getPeca() {
        return peca;
    }

    public void setPeca(String peca) {
        this.peca = peca;
    }

    public String getTipoEstampa() {
        return tipoEstampa;
    }

    public void setTipoEstampa(String tipoEstampa) {
        this.tipoEstampa = tipoEstampa;
    }

    @Override
    public String toString() {
        return "Categoria{" + "peca=" + peca + ", tipoEstampa=" + tipoEstampa + '}';
    }
   
}
