/*
 * Licensed To: ThoughtExecution & 9sistemes
 * Authored By: Rishi Raj Bansal
 * Developed in: Sep-Oct 2016
 *
 * ===========================================================================
 * This is FULLY owned and COPYRIGHTED by ThoughtExecution
 * This code may NOT be RESOLD or REDISTRIBUTED under any circumstances, and is only to be used with this application
 * Using the code from this application in another application is strictly PROHIBITED and not PERMISSIBLE
 * ===========================================================================
 */
package com.jobreporting.generic.configManager;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.exception.PropertyManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;

public class PropertyManager extends Properties{
    private static final long serialVersionUID = 1L;
    public static LoggerManager logger = GenericUtility.getLogger(PropertyManager.class.getName());
    private static PropertyManager propertyManager = null;
    private static String propertiesFilePath = null;
    private static String commonPropertyFile = GenericConstants.COMMON_PROPERTIES_FILE_NAME;
    private Map<String,Properties> hmPropertiesContainer = new HashMap<String,Properties>();	
    private static Object lockObject = new Object();
    private PropertyManager(){
        try{
            load(commonPropertyFile);
	}catch(Exception ex) {
            logger.error("PropertyManager - PropertyManager: ha ocurrido la siguiente excepcion: " + ex.getMessage());  
	}
    }
	
    public static PropertyManager createInstance(String propFilePath) { 
        propertiesFilePath = propFilePath;
	if(propertyManager == null){
            synchronized (lockObject){
		propertyManager = new PropertyManager();
            }
	}
        return propertyManager;
    }
	
    public static PropertyManager getPropertyManager(){
	return propertyManager;
    }
    
    public synchronized void load(String propFile) throws Exception {
        Properties props = new Properties();		
	try{
            if(propertiesFilePath.lastIndexOf(GenericConstants.FILE_SEPARATOR) != propertiesFilePath.length() - 1){
		propertiesFilePath = propertiesFilePath + GenericConstants.FILE_SEPARATOR;
            }
            FileInputStream fin = new FileInputStream(propertiesFilePath + propFile) ;
            props.load(fin);
            hmPropertiesContainer.put(propFile.toUpperCase(), props);
            fin.close();
	}catch(Exception ex){
            logger.error("PropertyManager - load: ha ocurrido la siguiente excepcion: " + ex.getMessage());  
	}		
    }
	
    public Properties getProperties(String propFileName) throws PropertyManagerException{
	Properties pReturn = hmPropertiesContainer.get(propFileName.toUpperCase());
	if(pReturn == null){
            try{
		load(propFileName);
		pReturn = hmPropertiesContainer.get(propFileName.toUpperCase());
            }catch(Exception ex) {
		logger.error("PropertyManager - getProperties: ha ocurrido la siguiente excepcion: " + ex.getMessage());  
            }
	}		
	return pReturn;		
    }
	
    public String getProperty(String propFileName, String key) throws PropertyManagerException {
	String value = "";
	Properties prop = hmPropertiesContainer.get(propFileName.toUpperCase());
	if(prop!=null){
            value = (String)prop.get(key);
	}else{
            try{
		load(propFileName);
		prop = hmPropertiesContainer.get(propFileName.toUpperCase());
		if(prop!=null){
                    value = (String)prop.get(key);
		}
            }catch(Exception ex){
		logger.error("PropertyManager - getProperty: ha ocurrido la siguiente excepcion: " + ex.getMessage()); 		
            }
	}		
	return value;
    }

    public static String getPropertiesFilePath() {
	return propertiesFilePath;
    }

    public static void setPropertiesFilePath(String propertiesFilePath) {
	PropertyManager.propertiesFilePath = propertiesFilePath;
    }	
}
