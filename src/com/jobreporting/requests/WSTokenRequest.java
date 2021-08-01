/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobreporting.requests;

/**
 *
 * @author santi
 */
public class WSTokenRequest extends WSBaseRequest{
    private static final long serialVersionUID = 1L;
    private int idWorker;

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
}
