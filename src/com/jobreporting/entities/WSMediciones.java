package com.jobreporting.entities;

public class WSMediciones extends WSBaseObject{
    private static final long serialVersionUID = 1L;
    private double contador;
    private String tiempoLlenado;
    private double cloro;
    private double ph;
    private double sal;
    private double isocianurico;
    private double temperatura;
    private int turbidez;
    private double salDescalcificada;
    private double durezaIn;
    private double durezaOut;
    private double fosfatos;
    private double alcalinidad;
    
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
     * @return the tiempoLlenado
     */
    public String getTiempoLlenado() {
        return tiempoLlenado;
    }

    /**
     * @param tiempoLlenado the tiempoLlenado to set
     */
    public void setTiempoLlenado(String tiempoLlenado) {
        this.tiempoLlenado = tiempoLlenado;
    }

    /**
     * @return the cloro
     */
    public double getCloro() {
        return cloro;
    }

    /**
     * @param cloro the cloro to set
     */
    public void setCloro(double cloro) {
        this.cloro = cloro;
    }

    /**
     * @return the ph
     */
    public double getPh() {
        return ph;
    }

    /**
     * @param ph the ph to set
     */
    public void setPh(double ph) {
        this.ph = ph;
    }

    /**
     * @return the sal
     */
    public double getSal() {
        return sal;
    }

    /**
     * @param sal the sal to set
     */
    public void setSal(double sal) {
        this.sal = sal;
    }

    /**
     * @return the isocianurico
     */
    public double getIsocianurico() {
        return isocianurico;
    }

    /**
     * @param isocianurico the isocianurico to set
     */
    public void setIsocianurico(double isocianurico) {
        this.isocianurico = isocianurico;
    }

    /**
     * @return the temperatura
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * @param temperatura the temperatura to set
     */
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * @return the turbidez
     */
    public int getTurbidez() {
        return turbidez;
    }

    /**
     * @param turbidez the turbidez to set
     */
    public void setTurbidez(int turbidez) {
        this.turbidez = turbidez;
    }

    /**
     * @return the salDescalcificada
     */
    public double getSalDescalcificada() {
        return salDescalcificada;
    }

    /**
     * @param salDescalcificada the salDescalcificada to set
     */
    public void setSalDescalcificada(double salDescalcificada) {
        this.salDescalcificada = salDescalcificada;
    }

    /**
     * @return the durezaIn
     */
    public double getDurezaIn() {
        return durezaIn;
    }

    /**
     * @param durezaIn the durezaIn to set
     */
    public void setDurezaIn(double durezaIn) {
        this.durezaIn = durezaIn;
    }

    /**
     * @return the durezaOut
     */
    public double getDurezaOut() {
        return durezaOut;
    }

    /**
     * @param durezaOut the durezaOut to set
     */
    public void setDurezaOut(double durezaOut) {
        this.durezaOut = durezaOut;
    }

    /**
     * @return the fosfatos
     */
    public double getFosfatos() {
        return fosfatos;
    }

    /**
     * @param fosfatos the fosfatos to set
     */
    public void setFosfatos(double fosfatos) {
        this.fosfatos = fosfatos;
    }

    /**
     * @return the alcalinidad
     */
    public double getAlcalinidad() {
        return alcalinidad;
    }

    /**
     * @param alcalinidad the alcalinidad to set
     */
    public void setAlcalinidad(double alcalinidad) {
        this.alcalinidad = alcalinidad;
    }    
}
