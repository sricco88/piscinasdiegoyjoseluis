/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobreporting.responses;

/**
 *
 * @author santi
 */
public class WSSalidasAlmacenResponse extends WSBaseResponse{
    private static final long serialVersionUID = 1L;
    private int idSalidasAlmacen; 

    /**
     * @return the idSalidasAlmacen
     */
    public int getIdSalidasAlmacen() {
        return idSalidasAlmacen;
    }

    /**
     * @param idSalidasAlmacen the idSalidasAlmacen to set
     */
    public void setIdSalidasAlmacen(int idSalidasAlmacen) {
        this.idSalidasAlmacen = idSalidasAlmacen;
    }
    
}
