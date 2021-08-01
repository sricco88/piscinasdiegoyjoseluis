package com.jobreporting.responses;

public class WSReportsResponse extends WSBaseResponse{
    private static final long serialVersionUID = 1L;
    private int idReporte; 

    /**
     * @return the idReporte
     */
    public int getIdReporte() {
        return idReporte;
    }

    /**
     * @param idReporte the idReporte to set
     */
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }
}
