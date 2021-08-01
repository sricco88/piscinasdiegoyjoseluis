/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobreporting.entities;

/**
 *
 * @author santi
 */
public class WSSalidasAlmacenLineas extends WSBaseObject{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idSalidasAlmacen;
    private String nombreProducto;
    private double cantidadProducto;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

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

    /**
     * @return the nombreProducto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * @param nombreProducto the nombreProducto to set
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * @return the cantidadProducto
     */
    public double getCantidadProducto() {
        return cantidadProducto;
    }

    /**
     * @param cantidadProducto the cantidadProducto to set
     */
    public void setCantidadProducto(double cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
    
    
}
