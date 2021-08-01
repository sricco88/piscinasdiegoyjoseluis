package com.jobreporting.generic.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.configManager.PropertyManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;

public class DatabaseConnectionManager {
    public static LoggerManager logger = GenericUtility.getLogger(DatabaseConnectionManager.class.getName());
    private static PropertyManager propertyManager 	= PropertyManager.getPropertyManager();
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;
    private static final String autoCommit;
    private static final String maxActivePoolSize;
    private static DataSource dataSource;
    private static DatabaseConnectionManager dbConManager = null;
    private static Object lockObject = new Object();
    static{
        Properties prop = propertyManager.getProperties(GenericConstants.DATABASE_PROPERTIES_FILE_NAME);
	driver = prop.getProperty(GenericConstants.DATABASE_DRIVER);
	url = prop.getProperty(GenericConstants.DATABASE_URL);
	username = prop.getProperty(GenericConstants.DATABASE_USERNAME);
	password = prop.getProperty(GenericConstants.DATABASE_PASSWORD);
	autoCommit = prop.getProperty(GenericConstants.DATABASE_AUTO_COMMIT);
	maxActivePoolSize = prop.getProperty(GenericConstants.DATABASE_ACTIVE_MAX_POOL_SIZE);		
    }	
	
    private void setupDataSource(){
        try{
            logger.info("DatabaseConnectionManager - setupDataSource: Loading the database driver...");
            Class.forName(driver);
            logger.info("DatabaseConnectionManager - setupDataSource: Configuring the connection factory...");            
            ConnectionFactory conFactory = new DriverManagerConnectionFactory(url, username, password);			
            GenericObjectPool connectionPool = new GenericObjectPool();
            connectionPool.setMaxActive(Integer.parseInt(maxActivePoolSize));
            PoolableConnectionFactory poolConFactory = new PoolableConnectionFactory(conFactory, connectionPool, null, null, false, Boolean.parseBoolean(autoCommit));
            logger.info("DatabaseConnectionManager - setupDataSource: Creating datasource...");
            dataSource = new PoolingDataSource(connectionPool);
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - setupDataSource: ha ocurrido la siguiente excepcion: " + ex.getMessage());          
	}
    }
    
    public static synchronized Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
	
    public static void instantiate() throws DatabaseConnectionManagerException{
        logger.info("DatabaseConnectionManager - instantiate:");
        try{
            if(null == dbConManager){
                synchronized(lockObject){
                    dbConManager = new DatabaseConnectionManager();
                    dbConManager.setupDataSource();
		}
            }
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - instantiate: ha ocurrido la siguiente excepcion: " + ex.getMessage());           
	}
    }
	
    public static synchronized void returnConnection(Connection con) throws DatabaseConnectionManagerException{
        try{
            if(null != con){
                con.close();
            }
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - returnConnection: ha ocurrido la siguiente excepcion: " + ex.getMessage());       
	}
    }
	
    public static synchronized void clearResources(Statement stmt, ResultSet rs) throws DatabaseConnectionManagerException{
        try{
            if(null != stmt){
                stmt.close();
                stmt = null;
            }
            if(null != rs){
		rs.close();
		rs = null;
            }
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - clearResources: ha ocurrido la siguiente excepcion: " + ex.getMessage());       
	}
    }
	
    public static synchronized void clearResources(Statement... stmts){
	try{
            for(Statement stmt : stmts){
		if(null != stmt){
                    stmt.close();
                    stmt = null;
		}
            }
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - clearResources: ha ocurrido la siguiente excepcion: " + ex.getMessage());     
	}
    }
	
    public static synchronized void clearResources(ResultSet... sets) throws DatabaseConnectionManagerException{
	try{
            for(ResultSet rs : sets){
                if(null != rs){
                    rs.close();
                    rs = null;
		}
            }
	}catch(Exception ex){
            logger.error("DatabaseConnectionManager - clearResources: ha ocurrido la siguiente excepcion: " + ex.getMessage());     	
	}
    }
	
    public static boolean testDBConnection(){
	Connection con = null;
	try{
            con = DatabaseConnectionManager.getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            if(null != metaData){
		logger.info("DatabaseConnectionManager - testDBConnection: Database product Name: " + metaData.getDatabaseProductName());
		logger.info("DatabaseConnectionManager - testDBConnection: Database product Version: " + metaData.getDatabaseProductVersion());
		logger.info("DatabaseConnectionManager - testDBConnection: Test DB connection is successful.");
		return true;
            }else{
		logger.error("DatabaseConnectionManager - testDBConnection: Test DB connection failed.");
		return false;
            }
	}catch(Exception ex){
           logger.error("DatabaseConnectionManager - testDBConnection: ha ocurrido la siguiente excepcion: " + ex.getMessage());     	
            return false;
	}finally{
            DatabaseConnectionManager.returnConnection(con);
	}
    }
}
