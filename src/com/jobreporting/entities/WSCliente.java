package com.jobreporting.entities;
public class WSCliente extends WSBaseObject{    
    private static final long serialVersionUID = 1L;
    private int id;
    private String codigo;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String codigoPostal;
    private String telefono;
    private String movil;
    private String numeroLlave;
    private String correoContacto;
    private String correoAdministracion;
    private int contadorIndividual;
    private String dosificador;
    private String descalcificador;
    private String quimicaIncluida;
    private double contador;
    private String matices;
    private String descripcionPiscina;
    private String descripcionSalaTecnica;
    private String accesorios;

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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the poblacion
     */
    public String getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the movil
     */
    public String getMovil() {
        return movil;
    }

    /**
     * @param movil the movil to set
     */
    public void setMovil(String movil) {
        this.movil = movil;
    }

    /**
     * @return the numeroLlave
     */
    public String getNumeroLlave() {
        return numeroLlave;
    }

    /**
     * @param numeroLlave the numeroLlave to set
     */
    public void setNumeroLlave(String numeroLlave) {
        this.numeroLlave = numeroLlave;
    }

    /**
     * @return the correoContacto
     */
    public String getCorreoContacto() {
        return correoContacto;
    }

    /**
     * @param correoContacto the correoContacto to set
     */
    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    /**
     * @return the correoAdministracion
     */
    public String getCorreoAdministracion() {
        return correoAdministracion;
    }

    /**
     * @param correoAdministracion the correoAdministracion to set
     */
    public void setCorreoAdministracion(String correoAdministracion) {
        this.correoAdministracion = correoAdministracion;
    }
    
    /**
     * @return the contadorIndividual
     */
    public int getContadorIndividual() {
        return contadorIndividual;
    }

    /**
     * @param contadorIndividual the contadorIndividual to set
     */
    public void setContadorIndividual(int contadorIndividual) {
        this.contadorIndividual = contadorIndividual;
    }    

    /**
     * @return the dosificador
     */
    public String getDosificador() {
        return dosificador;
    }

    /**
     * @param dosificador the dosificador to set
     */
    public void setDosificador(String dosificador) {
        this.dosificador = dosificador;
    }

    /**
     * @return the descalcificador
     */
    public String getDescalcificador() {
        return descalcificador;
    }

    /**
     * @param descalcificador the descalcificador to set
     */
    public void setDescalcificador(String descalcificador) {
        this.descalcificador = descalcificador;
    }

    /**
     * @return the quimicaIncluida
     */
    public String getQuimicaIncluida() {
        return quimicaIncluida;
    }

    /**
     * @param quimicaIncluida the quimicaIncluida to set
     */
    public void setQuimicaIncluida(String quimicaIncluida) {
        this.quimicaIncluida = quimicaIncluida;
    }

    /**
     * @return the contador
     */
    public double getContador() {
        return contador;
    }

    /**
     * @param contador the contador to set
     */
    public void setContador(double contador) {
        this.contador = contador;
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
}
