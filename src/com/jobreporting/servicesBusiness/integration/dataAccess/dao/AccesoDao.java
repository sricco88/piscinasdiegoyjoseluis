package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.entities.WSAcceso;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import static com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoDao{
    public static LoggerManager logger = GenericUtility.getLogger(AccesoDao.class.getName());
    
    public WSAcceso GetDatosAcceso(int idWorker){
        logger.debug("AccesoDao - GetDatosAcceso - Welcome");
        WSAcceso datosAcceso = new WSAcceso();
         Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM acceso WHERE id_worker=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idWorker);
            logger.debug("ClienteDao - GetDatosImportantes - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                datosAcceso.setIdWorker(rs.getInt("id_worker"));
                datosAcceso.setToken(rs.getString("token"));
                datosAcceso.setHora(rs.getString("hora"));
                datosAcceso.setFecha(rs.getString("fecha"));
            }
        }catch(SQLException sqlEx){
            logger.debug("AccesoDao - GetDatosAcceso - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("AccesoDao - GetDatosAcceso - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("AccesoDao - GetDatosAcceso - Exception: "+ex.getMessage());
            }
        }  
        return datosAcceso;
    }
    
}
