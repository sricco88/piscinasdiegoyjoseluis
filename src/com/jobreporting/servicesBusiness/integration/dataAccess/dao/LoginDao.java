package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.entities.WSAcceso;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.RandomStringUtils;

public class LoginDao {
    public static LoggerManager logger = GenericUtility.getLogger(LoginDao.class.getName());    
    
    public boolean Login(String usuario, String password){
        logger.debug("LoginDao - Login - Welcome");
        boolean isLoginValid = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM workers WHERE usuario=? AND password=?";            
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            logger.debug("LoginDao - Login - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                isLoginValid = true;
            } 
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - Login - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - Login - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - Login - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - Login - isLoginValid: "+isLoginValid);
        return isLoginValid;
    }
    
    public int GetIDWorker(String usuario, String password){
        logger.debug("LoginDao - GetIDWorker - Welcome");
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM workers WHERE usuario = ? AND password = ?";           
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            logger.debug("LoginDao - GetIDWorker - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("id");
            } 
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - GetIDWorker - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - GetIDWorker - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - GetIDWorker - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - GetIDWorker - idWorker: "+id);    
        return id;
    }
    
    public boolean IsTokenCreated(int idWorker){
        logger.debug("LoginDao - IsTokenCreated - Welcome");
        boolean isTokenCreated = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{            
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM acceso WHERE id_worker = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,idWorker);
            logger.debug("LoginDao - IsTokenCreated - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                isTokenCreated = true;
            }             
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - IsTokenCreated - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - IsTokenCreated - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - IsTokenCreated - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - IsTokenCreated - isTokenCreated: "+isTokenCreated);
        return isTokenCreated;  
    }
    
    public int CreateToken(int idWorker){
        logger.debug("LoginDao - CreateToken - Welcome");
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String token = RandomStringUtils.randomAlphanumeric(20);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");          
            Date date = new Date();        
            String fecha = dateFormat.format(date);
            String hora = timeFormat.format(date);        
            String sql = "INSERT INTO acceso (id_worker,token,fecha,hora) VALUES (?,?,?,?)";            
            con = DatabaseConnectionManager.getConnection();       
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1,idWorker);
            ps.setString(2,token);
            ps.setString(3,fecha);
            ps.setString(4,hora);            
            logger.debug("LoginDao - CreateToken - query: "+ps.toString());
            int rowsInserted = ps.executeUpdate();
            if(rowsInserted > 0){
                id = 1;
                con.commit();
            }          
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - CreateToken - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - CreateToken - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - CreateToken - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - CreateToken - idWorker: "+id);    
        return id;
    }
    
    public WSAcceso GetDatosAcceso(int idWorker){
        logger.debug("LoginDao - GetDatosAcceso - Welcome");
        WSAcceso datosAcceso = new WSAcceso();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM acceso WHERE id_worker=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idWorker);
            logger.debug("LoginDao - GetIDWorker - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                datosAcceso.setIdWorker(rs.getInt("id_worker"));
                datosAcceso.setToken(rs.getString("token"));
                datosAcceso.setFecha(rs.getString("fecha"));
                datosAcceso.setHora(rs.getString("hora"));
            }
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - GetDatosAcceso - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - GetDatosAcceso - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - GetDatosAcceso - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - GetDatosAcceso - token: "+datosAcceso.getToken()+",fecha: "+datosAcceso.getFecha()+",hora: "+datosAcceso.getHora());    
        return datosAcceso;
        
        
    }

    public int DeleteToken(int idWorker){
        logger.debug("LoginDao - DeleteToken - Welcome");
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "DELETE FROM acceso WHERE id_worker = ?";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1,idWorker);
            logger.debug("LoginDao - DeleteToken - sql: "+ps.toString());
            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated <= 0) {
                logger.debug("LoginDao - DeleteToken - Error: no se ha podido borrar el token");
            }else{
                id = 1;
            }              
           con.commit();
           try{
               DatabaseConnectionManager.returnConnection(con);
               DatabaseConnectionManager.clearResources(ps, rs);
           }catch (DatabaseConnectionManagerException dcmEx){
               logger.debug("LoginDao - DeleteToken - Exception: "+dcmEx.getMessage());
           }
        
        }catch(SQLException sqlEx){
           logger.debug("LoginDao - DeleteToken - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("LoginDao - DeleteToken - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("LoginDao - DeleteToken - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("LoginDao - DeleteToken - idToken: "+id);    
        return id;       
    }
}
