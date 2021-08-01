package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.classes.FechaContador;
import com.jobreporting.entities.WSInformacionGeneral;
import com.jobreporting.entities.WSMediciones;
import com.jobreporting.entities.WSProductoReport;
import com.jobreporting.entities.WSTareas;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteDao{
    public static LoggerManager logger = GenericUtility.getLogger(ReporteDao.class.getName());    
    
    public int SaveReporte(WSInformacionGeneral reporte,String horaReceived,String fechaReceived){
        logger.debug("ReporteDao - SaveReporte - Welcome");
        int idReporte = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO reporte (cliente,id_worker,hora_inicio,hora_fin,observaciones,incidencias,firmante,hora_created,fecha_created,hora_received,fecha_received,asistencia,clienteot,direccionot) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, reporte.getCliente());
            ps.setInt(2, reporte.getIdWorker());
            ps.setString(3, reporte.getHoraInicio());
            ps.setString(4, reporte.getHoraFin());
            ps.setString(5, reporte.getObservaciones());
            ps.setString(6, reporte.getIncidencias());
            ps.setString(7,reporte.getFirmante());
            ps.setString(8, reporte.getHoraCreated());
            ps.setString(9, reporte.getFechaCreated());
            ps.setString(10, horaReceived);
            ps.setString(11, fechaReceived);
            ps.setString(12, reporte.getAsistencia());
            ps.setString(13, reporte.getClienteot());
            ps.setString(14, reporte.getDireccionot());
            
            logger.debug("ReporteDao - SaveReporte - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted <= 0) {
                logger.debug("ReporteDao - SaveReporte - Error: ha fallado la insercion");
            }
            logger.debug("ReporteDao - SaveReporte - OK: reporte insertado correctamente");
            generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                idReporte = generatedKeys.getInt(1);
                logger.debug("ReporteDao - SaveReporte - IDReporte: "+idReporte);
            }
            con.commit();        
        }catch(SQLException sqlEx){
           logger.debug("ReporteDao - SaveReporte - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - SaveReporte - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - SaveReporte - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ReporteDao - SaveReporte - idWorker: "+idReporte);    
        return idReporte;        
    }
    
    public int SaveTareas(int idReporte,WSTareas tareas){
        logger.debug("ReporteDao - SaveTareas - Welcome");
        int idTareas = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO reporte_tareas (id_reporte,recoger_hojas,limpieza_fondo,limpieza_skimmers,limpieza_prefiltro,limpieza_descalcificador,cepillado_paredes,limpieza_bordes,rebosadero,lavado_enjuague_filtro,limpieza_envaciado) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idReporte);
            ps.setInt(2, tareas.getRecogerHojas());
            ps.setInt(3, tareas.getLimpiezaFondo());
            ps.setInt(4, tareas.getLimpiezaSkimmers());
            ps.setInt(5, tareas.getLimpiezaPreFiltro());
            ps.setInt(6, tareas.getLimpiezaDescalcificador());
            ps.setInt(7, tareas.getCepilladoParedes());
            ps.setInt(8, tareas.getLimpiezaBordes());
            ps.setInt(9, tareas.getRebosadero());
            ps.setString(10, tareas.getLavadoEnjuagueFiltro());
            ps.setString(11, tareas.getLimpiezaEnVaciado());
            logger.debug("ReporteDao - SaveTareas - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted > 0){
                idTareas = 1;
                con.commit();
            }    
        }catch(SQLException sqlEx){
           logger.debug("ReporteDao - SaveTareas - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - SaveTareas - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - SaveTareas - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ReporteDao - SaveTareas - idTareas: "+idTareas);    
        return idTareas;        
    }

    public int SaveMediciones(int idReporte,WSMediciones mediciones){
        logger.debug("ReporteDao - SaveMediciones - Welcome");
        int idMediciones = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO reporte_mediciones (id_reporte,contador,tiempo_llenado,cloro,ph,sal,isocianurico,temperatura,turbidez,sal_descalcificada,dureza_in,dureza_out,fosfatos,alcalinidad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idReporte);
            ps.setDouble(2, mediciones.getContador());
            ps.setString(3, mediciones.getTiempoLlenado());
            ps.setDouble(4, mediciones.getCloro());
            ps.setDouble(5, mediciones.getPh());
            ps.setDouble(6, mediciones.getSal());
            ps.setDouble(7, mediciones.getIsocianurico());
            ps.setDouble(8, mediciones.getTemperatura());
            ps.setInt(9, mediciones.getTurbidez());
            ps.setDouble(10, mediciones.getSalDescalcificada());
            ps.setDouble(11, mediciones.getDurezaIn());
            ps.setDouble(12, mediciones.getDurezaOut());
            ps.setDouble(13, mediciones.getFosfatos()); 
            ps.setDouble(14, mediciones.getAlcalinidad());
            logger.debug("ReporteDao - SaveMediciones - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted > 0){
                idMediciones = 1;
                con.commit();
            } 
        }catch(SQLException sqlEx){
           logger.debug("ReporteDao - SaveMediciones - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - SaveMediciones - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - SaveMediciones - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ReporteDao - SaveMediciones - idMediciones: "+idMediciones);    
        return idMediciones;
    }
    
    public int SaveProducto(int idReporte,WSProductoReport producto){
        logger.debug("ReporteDao - SaveProducto - Welcome");
        int idProducto = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO reporte_productos (id_reporte,nombre,cantidad) VALUES (?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idReporte);
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getCantidad());
            logger.debug("ReporteDao - SaveProducto - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted > 0){
                idProducto = 1;
                con.commit();
            }
        }catch(SQLException sqlEx){
           logger.debug("ReporteDao - SaveProducto - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - SaveProducto - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - SaveProducto - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ReporteDao - SaveProducto - idProducto: "+idProducto);    
        return idProducto;
    }
    
    public boolean UpdateMultimediaContentPaths(int id,String firma,String foto1,String foto2,String foto3,String foto4,String foto5){
        logger.debug("ReporteDao - UpdateMultimediaContentPaths - Welcome");
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;      
        try{       
            con = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE reporte SET firma=?,foto1=?,foto2=?,foto3=?,foto4=?,foto5=? WHERE id=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, firma);
            ps.setString(2, foto1);
            ps.setString(3, foto2);
            ps.setString(4, foto3);
            ps.setString(5, foto4);
            ps.setString(6, foto5);
            ps.setInt(7, id);
            
            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated > 0){
                isUpdated = true;
                con.commit();
                logger.debug("ReporteDao - UpdateMultimediaContentPaths - Exito - se han podido actualizar los paths del contenido multimedia del reporte con id: "+id+" correctamente");
            }else{
                logger.debug("ReporteDao - UpdateMultimediaContentPaths - Error - no se han podido actualizar los paths del contenido multimedia del reporte con id: "+id+", revisa el query");
            }    
        }catch(SQLException sqlEx){
            logger.debug("ReporteDao - UpdateMultimediaContentPaths - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - UpdateMultimediaContentPaths - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - UpdateMultimediaContentPaths - Exception: "+ex.getMessage());
            }
        } 
        return isUpdated;
    }

    public void PublishReportData(int idReporte){
        logger.debug("ReporteDao - PublishReportData - Welcome");
        int responseCode = 0;
        StringBuffer response = new StringBuffer();
        HttpURLConnection urlConnection = null;
        try{
            String urlPath = "https://diegoyjoseluis.9sistemes.com/main/callReportPublisher.php?idReporte="+idReporte;
            logger.debug("ReporteDao - PublishReportData - urlPath: " + urlPath);
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("GET");

            logger.info("ReporteDao - PublishReportData - Communicating with web server with GET... ");
            responseCode = urlConnection.getResponseCode();
            logger.info("ReporteDao - PublishReportData - Response Code returned : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.debug(response.toString());
            if(responseCode == 200){
                logger.debug("ReporteDao - PublishReportData - Success response returned from web server.");
            }else{
                String cause = "ReporteDao - PublishReportData - Response code is not 200/OK, response code : " + responseCode;
                cause = cause + " " + response.toString();
                logger.debug("ReporteDao - PublishReportData - Codigo Incorrecto: "+cause);
            }
        }catch(Exception ex){
            logger.debug("ReporteDao - PublishReportData - Exception: "+ex.getMessage());
        }
    }
    
    public FechaContador GetFechaContadorAnteriorMedicion(String nombre){
        logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - Welcome");        
        FechaContador fechaContador = new FechaContador();  
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT reporte.fecha_created,reporte_mediciones.contador FROM reporte INNER JOIN reporte_mediciones ON reporte.id=reporte_mediciones.id_reporte WHERE cliente=? ORDER BY reporte.id DESC LIMIT 1, 1";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, nombre);
            logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){               
                fechaContador.setFecha(rs.getString("fecha_created"));
                fechaContador.setContador(rs.getDouble("contador"));              
            }            
        }catch(SQLException sqlEx){
            logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - Exception: "+ex.getMessage());
            }
        }   
        logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - fechaMedicionAnterior: "+fechaContador.getFecha());
        logger.debug("ReporteDao - GetFechaContadorAnteriorMedicion - contadorMedicionAnterior: "+fechaContador.getContador());
        return fechaContador;
    }

}
