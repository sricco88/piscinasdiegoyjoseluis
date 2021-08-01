package com.jobreporting.entities;

public class WSClienteReport extends WSBaseObject{    
    private static final long serialVersionUID = 1L;
    private int id;
    private int idCliente;
    private String matices;
    private String descripcionPiscina;
    private String descripcionSalaTecnica;
    private String accesorios;
    private String horaCreated;
    private String fechaCreated;

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
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the matices
     */
    public String getMatices() {
        return matices;
    }

    /**
     * @param matices the matices to set
     */
    public void setMatices(String matices) {
        this.matices = matices;
    }

    /**
     * @return the descripcionPiscina
     */
    public String getDescripcionPiscina() {
        return descripcionPiscina;
    }

    /**
     * @param descripcionPiscina the descripcionPiscina to set
     */
    public void setDescripcionPiscina(String descripcionPiscina) {
        this.descripcionPiscina = descripcionPiscina;
    }

    /**
     * @return the descripcionSalaTecnica
     */
    public String getDescripcionSalaTecnica() {
        return descripcionSalaTecnica;
    }

    /**
     * @param descripcionSalaTecnica the descripcionSalaTecnica to set
     */
    public void setDescripcionSalaTecnica(String descripcionSalaTecnica) {
        this.descripcionSalaTecnica = descripcionSalaTecnica;
    }

    /**
     * @return the accesorios
     */
    public String getAccesorios() {
        return accesorios;
    }

    /**
     * @param accesorios the accesorios to set
     */
    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }
    
    /**
     * @return the horaCreated
     */
    public String getHoraCreated() {
        return horaCreated;
    }

    /**
     * @param horaCreated the horaCreated to set
     */
    public void setHoraCreated(String horaCreated) {
        this.horaCreated = horaCreated;
    }

    /**
     * @return the fechaCreated
     */
    public String getFechaCreated() {
        return fechaCreated;
    }

    /**
     * @param fechaCreated the fechaCreated to set
     */
    public void setFechaCreated(String fechaCreated) {
        this.fechaCreated = fechaCreated;
    }
}
