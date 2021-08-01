package com.jobreporting.responses;

public class WSClienteReportResponse extends WSBaseResponse{
    private static final long serialVersionUID = 1L;
    private int idClienteReporte;

    /**
     * @return the idClienteReporte
     */
    public int getIdClienteReporte() {
        return idClienteReporte;
    }

    /**
     * @param idClienteReporte the idClienteReporte to set
     */
    public void setIdClienteReporte(int idClienteReporte) {
        this.idClienteReporte = idClienteReporte;
    }
    
}
