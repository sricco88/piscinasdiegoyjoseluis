/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobreporting.requests;

import com.jobreporting.entities.WSSalidasAlmacen;
import com.jobreporting.entities.WSSalidasAlmacenLineas;
import java.util.ArrayList;

/**
 *
 * @author santi
 */
public class WSSalidasAlmacenRequest extends WSBaseRequest{
    private static final long serialVersionUID = 1L;
    private WSSalidasAlmacen salidasAlmacen;
    private ArrayList<WSSalidasAlmacenLineas> lineas;

    /**
     * @return the salidasAlmacen
     */
    public WSSalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    /**
     * @param salidasAlmacen the salidasAlmacen to set
     */
    public void setSalidasAlmacen(WSSalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    /**
     * @return the lineas
     */
    public ArrayList<WSSalidasAlmacenLineas> getLineas() {
        return lineas;
    }

    /**
     * @param lineas the lineas to set
     */
    public void setLineas(ArrayList<WSSalidasAlmacenLineas> lineas) {
        this.lineas = lineas;
    }
    
    
}
