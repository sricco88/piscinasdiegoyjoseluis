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
public class WSSalidasAlmacen extends WSBaseObject{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idWorker;
    private String observaciones;
    private String horaCreatedAndroid;
    private String fechaCreatedAndroid;

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
     * @return the idWorker
     */
    public int getIdWorker() {
        return idWorker;
    }

    /**
     * @param idWorker the idWorker to set
     */
    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the horaCreatedAndroid
     */
    public String getHoraCreatedAndroid() {
        return horaCreatedAndroid;
    }

    /**
     * @param horaCreatedAndroid the horaCreatedAndroid to set
     */
    public void setHoraCreatedAndroid(String horaCreatedAndroid) {
        this.horaCreatedAndroid = horaCreatedAndroid;
    }

    /**
     * @return the fechaCreatedAndroid
     */
    public String getFechaCreatedAndroid() {
        return fechaCreatedAndroid;
    }

    /**
     * @param fechaCreatedAndroid the fechaCreatedAndroid to set
     */
    public void setFechaCreatedAndroid(String fechaCreatedAndroid) {
        this.fechaCreatedAndroid = fechaCreatedAndroid;
    }
}
