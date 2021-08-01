package com.jobreporting.requests;
import com.jobreporting.entities.WSClienteReport;

public class WSClienteReportRequest extends WSBaseRequest{
    private static final long serialVersionUID = 1L;
    private WSClienteReport cliente;

    /**
     * @return the cliente
     */
    public WSClienteReport getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(WSClienteReport cliente) {
        this.cliente = cliente;
    }
    
}
