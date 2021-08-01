package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.entities.WSCliente;
import com.jobreporting.entities.WSClienteReport;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDao {
    public static LoggerManager logger = GenericUtility.getLogger(ClienteDao.class.getName());
    
    public ArrayList<WSCliente> GetClientes(){
        logger.debug("ClienteDao - GetClientes - Welcome");
        ArrayList<WSCliente> clientes = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id,codigo_cliente,nombre,direccion,poblacion,codigo_postal,telefono,movil,numero_llave,correo_contacto,correo_administracion,contador_individual,dosificador,descalcificador,quimica_incluida,contador,matices,descripcion_piscina,descripcion_sala_tecnica,accesorios FROM clientes WHERE estado!='baja' AND codigo_cliente!=''";
            ps = con.prepareStatement(sql, 1);
            logger.debug("ClienteDao - GetClientes - sql: "+ps.toString());
            rs = ps.executeQuery();
            while(rs.next()){
                WSCliente cliente = new WSCliente();
                cliente.setId(rs.getInt("id"));
                cliente.setCodigo(rs.getString("codigo_cliente").trim());
                cliente.setNombre(rs.getString("nombre").trim());
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setPoblacion(rs.getString("poblacion"));
                cliente.setCodigoPostal(rs.getString("codigo_postal"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setMovil(rs.getString("movil"));
                cliente.setNumeroLlave(rs.getString("numero_llave"));
                cliente.setCorreoContacto(rs.getString("correo_contacto"));
                cliente.setCorreoAdministracion(rs.getString("correo_administracion"));
                cliente.setContadorIndividual(rs.getInt("contador_individual"));
                cliente.setDosificador(rs.getString("dosificador"));
                cliente.setDescalcificador(rs.getString("descalcificador"));                            
                if(rs.getInt("quimica_incluida") == 0){
                    cliente.setQuimicaIncluida("NO");
                }else if(rs.getInt("quimica_incluida") == 1){
                    cliente.setQuimicaIncluida("SI");
                }        
                cliente.setContador(rs.getDouble("contador"));       
                cliente.setMatices(rs.getString("matices"));
                cliente.setDescripcionPiscina(rs.getString("descripcion_piscina"));
                cliente.setDescripcionSalaTecnica(rs.getString("descripcion_sala_tecnica"));
                cliente.setAccesorios(rs.getString("accesorios"));             
                clientes.add(cliente);
            } 
        }catch(SQLException sqlEx){
           logger.debug("ClienteDao - GetClientes - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetClientes - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - GetClientes - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ClienteDao - GetClientes - clientes: "+clientes.size());    
        return clientes;
    }
     
    public WSClienteReport GetDatosImportantes(int idCliente){
        logger.debug("ClienteDao - GetDatosImportantes - Welcome");
        WSClienteReport cliente = new WSClienteReport();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT matices,descripcion_piscina,descripcion_sala_tecnica,accesorios FROM clientes WHERE id = ?";
            con = DatabaseConnectionManager.getConnection();
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idCliente);
            logger.debug("ClienteDao - GetDatosImportantes - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setMatices(rs.getString("matices"));
                cliente.setDescripcionPiscina(rs.getString("descripcion_piscina"));
                cliente.setDescripcionSalaTecnica(rs.getString("descripcion_sala_tecnica"));
                cliente.setAccesorios(rs.getString("accesorios"));
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetDatosImportantes - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetDatosImportantes - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - GetDatosImportantes - Exception: "+ex.getMessage());
            }
        }             
        return cliente;
    }
    
    public boolean UpdateDatosImportantesCliente(int idCliente,String matices,String descripcionPiscina,String descripcionSalaTecnica,String accesorios){
        logger.debug("ClienteDao - UpdateDatosImportantesCliente - Welcome");
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;      
        try{       
            con = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE clientes SET matices=?,descripcion_piscina=?,descripcion_sala_tecnica=?,accesorios=? WHERE id = ?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, matices);
            ps.setString(2, descripcionPiscina);
            ps.setString(3, descripcionSalaTecnica);
            ps.setString(4, accesorios);
            ps.setInt(5, idCliente);            
            logger.debug("ClienteDao - UpdateDatosImportantesCliente - sql: "+ps.toString());
             
            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated > 0){
                isUpdated = true;
                con.commit();
                logger.debug("ClienteDao - UpdateDatosImportantesCliente - Exito - se han podido actualizar los datos importantes del cliete con id: "+idCliente+" correctamente");
            }else{
                logger.debug("ClienteDao - UpdateDatosImportantesCliente - Error - no se han podido actualizar los datos importantes del cliete con id: "+idCliente+", revisa el query");
            }          
        }catch(SQLException sqlEx){
           logger.debug("ClienteDao - UpdateDatosImportantesCliente - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - UpdateDatosImportantesCliente - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - UpdateDatosImportantesCliente - Exception: "+ex.getMessage());
            }
        }  
        return isUpdated;       
    }

    public int GetIDClienteByNombre(String nombre){
        logger.debug("ClienteDao - GetIDClienteByNombre - Welcome");        
        int idCliente = -1;        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM clientes where nombre=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, nombre);
            logger.debug("ClienteDao - GetIDClienteByNombre - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                idCliente = rs.getInt("id");                
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetIDClienteByNombre - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetIDClienteByNombre - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - GetIDClienteByNombre - Exception: "+ex.getMessage());
            }
        }   
        logger.debug("ClienteDao - GetIDClienteByNombre - el cliente con nombre: "+nombre+" tiene el id: "+idCliente);
        return idCliente;
    }
    
    public boolean UpdateLecturaContador(int id,double contador){
        logger.debug("ClienteDao - UpdateLecturaContador - Welcome");
        boolean isUpdated = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;      
        try{       
            con = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE clientes SET contador=? WHERE id=?";
            ps = con.prepareStatement(sql, 1);
            ps.setDouble(1, contador);
            ps.setInt(2, id);            
            logger.debug("ClienteDao - UpdateLecturaContador - sql: "+ps.toString());
             
            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated > 0){
                isUpdated = true;
                con.commit();
                logger.debug("ClienteDao - UpdateLecturaContador - Exito - se han podido actualizar el contador del cliente con id: "+id+" correctamente");
            }else{
                logger.debug("ClienteDao - UpdateLecturaContador - Error - no se han podido actualizar el contador del cliente con id: "+id+", revisa el query");
            }              
        }catch(SQLException sqlEx){
           logger.debug("ClienteDao - UpdateLecturaContador - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - UpdateLecturaContador - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - UpdateLecturaContador - Exception: "+ex.getMessage());
            }
        }  
        return isUpdated;
    }
    
    public boolean TieneContadorIndividual(String nombre){
        logger.debug("ClienteDao - TieneContadorIndividual - Welcome");        
        boolean tieneContadorIndividual = false;        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT contador_individual FROM clientes where nombre=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, nombre);
            logger.debug("ClienteDao - TieneContadorIndividual - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                int contadorIndividual = rs.getInt("contador_individual");
                if(contadorIndividual == 1){
                    tieneContadorIndividual = true;
                }
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - TieneContadorIndividual - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - TieneContadorIndividual - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - TieneContadorIndividual - Exception: "+ex.getMessage());
            }
        }   
        logger.debug("ClienteDao - TieneContadorIndividual - tieneContadorIndividual: "+tieneContadorIndividual);
        return tieneContadorIndividual;
    }
    
    public boolean HayMedicionAnterior(String nombre){
        logger.debug("ClienteDao - HayMedicionAnterior - Welcome");        
        boolean hayMedicionAnterior = false;        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT count(distinct(id)) AS numMediciones FROM reporte WHERE cliente=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, nombre);
            logger.debug("ClienteDao - HayMedicionAnterior - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                int numMediciones = rs.getInt("numMediciones");
                if(numMediciones >= 2){
                    hayMedicionAnterior = true;
                }
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - HayMedicionAnterior - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - HayMedicionAnterior - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - HayMedicionAnterior - Exception: "+ex.getMessage());
            }
        }   
        logger.debug("ClienteDao - HayMedicionAnterior - hayMedicionAnterior: "+hayMedicionAnterior);
        return hayMedicionAnterior;
    }
    
    public double[] GetConsumosDiarioTeorico(String cliente){
        logger.debug("ClienteDao - GetConsumosDiarioTeorico - Welcome");
        double[] consumosDiarioTeorico = new double[12];
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT consumo_teorico_enero,consumo_teorico_febrero,consumo_teorico_marzo,consumo_teorico_abril,consumo_teorico_mayo,consumo_teorico_junio,consumo_teorico_julio,consumo_teorico_agosto,consumo_teorico_setiembre,consumo_teorico_octubre,consumo_teorico_noviembre,consumo_teorico_diciembre FROM clientes WHERE nombre=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, cliente);
            logger.debug("ClienteDao - GetConsumosDiarioTeorico - sql: "+ps.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                consumosDiarioTeorico[0] = rs.getDouble("consumo_teorico_enero");
                consumosDiarioTeorico[1] = rs.getDouble("consumo_teorico_febrero");
                consumosDiarioTeorico[2] = rs.getDouble("consumo_teorico_marzo");
                consumosDiarioTeorico[3] = rs.getDouble("consumo_teorico_abril");
                consumosDiarioTeorico[4] = rs.getDouble("consumo_teorico_mayo");
                consumosDiarioTeorico[5] = rs.getDouble("consumo_teorico_junio");
                consumosDiarioTeorico[6] = rs.getDouble("consumo_teorico_julio");
                consumosDiarioTeorico[7] = rs.getDouble("consumo_teorico_agosto");
                consumosDiarioTeorico[8] = rs.getDouble("consumo_teorico_setiembre");
                consumosDiarioTeorico[9] = rs.getDouble("consumo_teorico_octubre");
                consumosDiarioTeorico[10] = rs.getDouble("consumo_teorico_noviembre");
                consumosDiarioTeorico[11] = rs.getDouble("consumo_teorico_diciembre");
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetConsumosDiarioTeorico - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetConsumosDiarioTeorico - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ClienteDao - GetConsumosDiarioTeorico - Exception: "+ex.getMessage());
            }
        }  
        for(int i=0;i<12;i++){
            logger.debug("ClienteDao - GetConsumosDiarioTeorico - consumosDiarioTeorico["+i+"]: "+consumosDiarioTeorico[i]);
        }
        
        
        return consumosDiarioTeorico;
        
    }
}
