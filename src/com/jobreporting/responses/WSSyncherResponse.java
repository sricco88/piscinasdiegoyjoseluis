package com.jobreporting.responses;
import com.jobreporting.entities.WSCliente;
import com.jobreporting.entities.WSProducto;
import java.util.ArrayList;

public class WSSyncherResponse extends WSBaseResponse {
    private static final long serialVersionUID = 1L;
    private ArrayList<WSCliente> clientes;
    private ArrayList<WSProducto> productos;    

    /**
     * @return the clientes
     */
    public ArrayList<WSCliente> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<WSCliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the productos
     */
    public ArrayList<WSProducto> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(ArrayList<WSProducto> productos) {
        this.productos = productos;
    } 
}
