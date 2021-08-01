package com.jobreporting.entities;

public class WSInformacionGeneral extends WSBaseObject{
    private static final long serialVersionUID = 1L;
    private int id;
    private int idWorker;
    private String cliente;
    private String horaInicio;
    private String horaFin;
    private String observaciones;
    private String incidencias;
    private String firmante;
    private byte[] firma;
    private byte[] foto1;
    private byte[] foto2;
    private byte[] foto3;
    private byte[] foto4;
    private byte[] foto5;
    private String horaCreated;
    private String fechaCreated;
    private String asistencia;
    private String clienteot;
    private String direccionot;

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
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the horaInicio
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the incidencias
     */
    public String getIncidencias() {
        return incidencias;
    }

    /**
     * @param incidencias the incidencias to set
     */
    public void setIncidencias(String incidencias) {
        this.incidencias = incidencias;
    }

    /**
     * @return the firmante
     */
    public String getFirmante() {
        return firmante;
    }

    /**
     * @param firmante the firmante to set
     */
    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    /**
     * @return the firma
     */
    public byte[] getFirma() {
        return firma;
    }

    /**
     * @param firma the firma to set
     */
    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    /**
     * @return the foto1
     */
    public byte[] getFoto1() {
        return foto1;
    }

    /**
     * @param foto1 the foto1 to set
     */
    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    /**
     * @return the foto2
     */
    public byte[] getFoto2() {
        return foto2;
    }

    /**
     * @param foto2 the foto2 to set
     */
    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    /**
     * @return the foto3
     */
    public byte[] getFoto3() {
        return foto3;
    }

    /**
     * @param foto3 the foto3 to set
     */
    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    /**
     * @return the foto4
     */
    public byte[] getFoto4() {
        return foto4;
    }

    /**
     * @param foto4 the foto4 to set
     */
    public void setFoto4(byte[] foto4) {
        this.foto4 = foto4;
    }

    /**
     * @return the foto5
     */
    public byte[] getFoto5() {
        return foto5;
    }

    /**
     * @param foto5 the foto5 to set
     */
    public void setFoto5(byte[] foto5) {
        this.foto5 = foto5;
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

    /**
     * @return the asistencia
     */
    public String getAsistencia() {
        return asistencia;
    }

    /**
     * @param asistencia the asistencia to set
     */
    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    /**
     * @return the clienteot
     */
    public String getClienteot() {
        return clienteot;
    }

    /**
     * @param clienteot the clienteot to set
     */
    public void setClienteot(String clienteot) {
        this.clienteot = clienteot;
    }

    /**
     * @return the direccionot
     */
    public String getDireccionot() {
        return direccionot;
    }

    /**
     * @param direccionot the direccionot to set
     */
    public void setDireccionot(String direccionot) {
        this.direccionot = direccionot;
    }

    
}
