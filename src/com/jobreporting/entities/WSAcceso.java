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
public class WSAcceso extends WSBaseObject{    
    private static final long serialVersionUID = 1L;
    private int idWorker;
    private String token;
    private String fecha;
    private String hora; 

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
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }
}
