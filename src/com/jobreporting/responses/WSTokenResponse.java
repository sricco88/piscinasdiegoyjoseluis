/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobreporting.responses;
import com.jobreporting.entities.WSAcceso;

/**
 *
 * @author santi
 */
public class WSTokenResponse extends WSBaseResponse{
    private static final long serialVersionUID = 1L;
    private WSAcceso acceso;

    /**
     * @return the acceso
     */
    public WSAcceso getAcceso() {
        return acceso;
    }

    /**
     * @param acceso the acceso to set
     */
    public void setAcceso(WSAcceso acceso) {
        this.acceso = acceso;
    }
    
    
}
