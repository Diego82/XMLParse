/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvirgendelcarmen.domxmlparser;

/**
 *
 * @author matinal
 */
public class Cd {
    protected String artista;
    protected String titulo;
    protected String pais;
    protected String precio;
    protected String compania;
    protected int anio;

    public Cd(String artista, String titulo, String pais, String precio, String compania, int anio) {
        this.artista = artista;
        this.titulo = titulo;
        this.pais = pais;
        this.precio = precio;
        this.compania = compania;
        this.anio = anio;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getArtista() {
        return artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPais() {
        return pais;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCompania() {
        return compania;
    }

    public int getAnio() {
        return anio;
    }

    

    

    @Override
    public String toString() {
        return "Cd{" + "artista=" + artista + ", titulo=" + titulo + ", pais=" + pais + ", precio=" + precio + ", anio=" + anio + '}';
    }
    
    
    
}
