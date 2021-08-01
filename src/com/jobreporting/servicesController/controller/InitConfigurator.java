package com.jobreporting.servicesController.controller;

import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.configManager.Localization;
import com.jobreporting.generic.configManager.PropertyManager;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;


public class InitConfigurator {
    public static LoggerManager logger = GenericUtility.getLogger(InitConfigurator.class.getName());
    public String propertiesFilePath = null;
    PropertyManager propertyManager = null;
    public InitConfigurator(String propertiesFilePath){
	this.propertiesFilePath = propertiesFilePath;
    }
	
    public boolean initialize(){
        logger.info("InitConfigurator - initialize");
	boolean flag = true;
	try{
            if(!GenericUtility.safeTrim(propertiesFilePath).equals(GenericConstants.EMPTY_STRING)){
		if(propertiesFilePath.lastIndexOf(GenericConstants.FILE_SEPARATOR) != propertiesFilePath.length() - 1){
                    propertiesFilePath = propertiesFilePath + GenericConstants.FILE_SEPARATOR;
		}		
                /*Load common properties file in memory */
		propertyManager = PropertyManager.createInstance(propertiesFilePath);
		if(propertyManager == null){
                    logger.error("InitConfigurator Failed to load the common properties file.");			
		}else{
                    logger.info("InitConfigurator loaded the common properties file successfully in memory.");
		}			
		/*Load Localization */
		Localization localization = Localization.createInstance(propertiesFilePath);
		if(localization == null){
                    logger.error("InitConfigurator Failed to load the localization.");
		}else {
                    logger.info("InitConfigurator loaded the localization successfully.");
		}
                
                DatabaseConnectionManager.instantiate();
		boolean status = DatabaseConnectionManager.testDBConnection();
		if(status){
                    logger.info("InitConfigurator - initialize: Database connections & pooling are configured successfully. Test Passed.");
		}else{
                    logger.info("InitConfigurator - initialize: Database connections & pooling are FAILED to be configured successfully. Test FAILED.");
                }		
                logger.info("InitConfigurator - initialize: Action Factory loaded the Action Directory successfully.");
            }else{
                logger.info("InitConfigurator - initialize: Properties file path is empty. Init Configurator failed.");		
            }
	}catch(Exception ex){
            logger.error("InitConfigurator - initialize: ha ocurrido la siguiente excepcion : " + ex.getMessage());
            flag = false;
	}
	return flag;		
    }

    void terminate() {
      
    }
   
}
