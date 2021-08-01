package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.entities.WSSalidasAlmacen;
import com.jobreporting.entities.WSSalidasAlmacenLineas;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlmacenDao{
    public static LoggerManager logger = GenericUtility.getLogger(AlmacenDao.class.getName());
    
    public int SaveSalidasAlmacen(WSSalidasAlmacen salidasAlmacen,String fechaWS,String horaWS){
        logger.debug("AlmacenDao - SaveSalidasAlmacen - Welcome");
        int idSalidasAlmacen = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO salidas_almacen (id_worker,observaciones,fechaws,horaws,fechaandroid,horaandroid) VALUES (?,?,?,?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, salidasAlmacen.getIdWorker());
            ps.setString(2, salidasAlmacen.getObservaciones());
            ps.setString(3, fechaWS);
            ps.setString(4, horaWS);
            ps.setString(5, salidasAlmacen.getFechaCreatedAndroid());
            ps.setString(6, salidasAlmacen.getHoraCreatedAndroid());
            logger.debug("ReporteDao - SaveReporte - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted <= 0) {
                logger.debug("AlmacenDao - SaveSalidasAlmacen - Error: ha fallado la insercion");
            }
            logger.debug("AlmacenDao - SaveSalidasAlmacen - OK: reporte insertado correctamente");
            generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                idSalidasAlmacen = generatedKeys.getInt(1);
                logger.debug("AlmacenDao - SaveSalidasAlmacen - idSalidasAlmacen: "+idSalidasAlmacen);
            }
            con.commit();            
        }catch(SQLException sqlEx){
           logger.debug("AlmacenDao - SaveSalidasAlmacen - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("AlmacenDao - SaveSalidasAlmacen - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("AlmacenDao - SaveSalidasAlmacen - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("AlmacenDao - SaveSalidasAlmacen - idWorker: "+idSalidasAlmacen);    
        return idSalidasAlmacen;    
    }
    
    public int SaveSalidasAlmacenLineas(int idSalidasAlmacen,WSSalidasAlmacenLineas linea){
        logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - Welcome");
        int idLinea = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        try{
            String sql = "INSERT INTO salidas_almacen_lineas (id_salidas_almacen,producto,cantidad) VALUES (?,?,?)";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idSalidasAlmacen);
            ps.setString(2, linea.getNombreProducto());
            ps.setDouble(3, linea.getCantidadProducto());
            logger.debug("AlmacenDao - SaveReporte - sql: "+ps.toString());            
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted <= 0) {
                logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - Error: ha fallado la insercion");
            }
            logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - OK: reporte insertado correctamente");
            generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                idLinea = generatedKeys.getInt(1);
                logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - IDReporte: "+idSalidasAlmacen);
            }
            con.commit();
        }catch(SQLException sqlEx){
           logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("AlmacenDao - SaveSalidasAlmacenLineas - idWorker: "+idSalidasAlmacen);    
        return idLinea; 
    }
}
