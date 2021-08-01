package com.jobreporting.requests;
import com.jobreporting.entities.WSInformacionGeneral;
import com.jobreporting.entities.WSMediciones;
import com.jobreporting.entities.WSTareas;
import com.jobreporting.entities.WSProductoReport;
import java.util.ArrayList;

public class WSReportsRequest extends WSBaseRequest{
    private static final long serialVersionUID = 1L;
    private WSInformacionGeneral reporte;
    private WSTareas tareas;
    private WSMediciones mediciones;
    private ArrayList<WSProductoReport> productos;   

    /**
     * @return the reporte
     */
    public WSInformacionGeneral getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(WSInformacionGeneral reporte) {
        this.reporte = reporte;
    }

    /**
     * @return the tareas
     */
    public WSTareas getTareas() {
        return tareas;
    }

    /**
     * @param tareas the tareas to set
     */
    public void setTareas(WSTareas tareas) {
        this.tareas = tareas;
    }

    /**
     * @return the mediciones
     */
    public WSMediciones getMediciones() {
        return mediciones;
    }

    /**
     * @param mediciones the mediciones to set
     */
    public void setMediciones(WSMediciones mediciones) {
        this.mediciones = mediciones;
    }

    /**
     * @return the productos
     */
    public ArrayList<WSProductoReport> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(ArrayList<WSProductoReport> productos) {
        this.productos = productos;
    }
}
