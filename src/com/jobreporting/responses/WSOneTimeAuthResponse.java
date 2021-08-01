package com.jobreporting.responses;
import com.jobreporting.entities.WSAcceso;
import com.jobreporting.entities.WSCliente;
import com.jobreporting.entities.WSProducto;
import java.util.ArrayList;

public class WSOneTimeAuthResponse extends WSBaseResponse{       
    private static final long serialVersionUID = 1L;  
    private boolean login;
    private String mensaje;
    private WSAcceso datosAcceso;
    private ArrayList<WSProducto> productos;
    private ArrayList<WSCliente> clientes;

    /**
     * @return the login
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(boolean login) {
        this.login = login;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the datosAcceso
     */
    public WSAcceso getDatosAcceso() {
        return datosAcceso;
    }

    /**
     * @param datosAcceso the datosAcceso to set
     */
    public void setDatosAcceso(WSAcceso datosAcceso) {
        this.datosAcceso = datosAcceso;
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
    
     /**
     * @return the clientes
     */
    public ArrayList<WSCliente> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<WSCliente> clientes){
        this.clientes = clientes;
    }
}
