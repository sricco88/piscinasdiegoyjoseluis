
package com.jobreporting.servicesBusiness.integration.dataAccess.dao;

import com.jobreporting.entities.WSProducto;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductosDao {
    public static LoggerManager logger = GenericUtility.getLogger(ProductosDao.class.getName());   
     
    public ArrayList<WSProducto> GetProductos(){
        logger.debug("ProductosDao - GetProductos - Welcome");
        ArrayList<WSProducto> productos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM productos";
            ps = con.prepareStatement(sql, 1);
            logger.debug("ProductosDao - GetProductos - sql: "+ps.toString());
            rs = ps.executeQuery();
            while(rs.next()){
                WSProducto producto = new WSProducto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setUnidadMedida(rs.getString("unidad_medida"));
                productos.add(producto);
            } 
        }catch(SQLException sqlEx){
           logger.debug("ProductosDao - GetProductos - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ProductosDao - GetProductos - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(Exception ex){
                logger.debug("ProductosDao - GetProductos - Exception: "+ex.getMessage());
            }
        }  
        logger.debug("ProductosDao - GetProductos - productos: "+productos.size());    
        return productos;
    }
}
