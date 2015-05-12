/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvirgendelcarmen.domxmlparser;

import java.util.Comparator;

/**
 *
 * @author matinal
 */
public class CompareCdByArtist implements Comparator<Cd>{
    @Override
    public int compare(Cd a, Cd b){
        return a.artista.compareTo(b.artista);
    }
    
}
