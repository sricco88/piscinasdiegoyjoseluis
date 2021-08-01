package com.jobreporting.servicesController.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import com.jobreporting.generic.common.GenericConstants;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class ApplicationController extends ServletContainer{    
	private static final long serialVersionUID = 1L;
	public static LoggerManager logger = GenericUtility.getLogger(ApplicationController.class.getName());	
	public String propertiesFilePath = null;	
	InitConfigurator initConfigurator = null;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
	logger.info("ApplicationController - init:Loading the Application...");
        logger.info("ApplicationController - init: config: "+config.getInitParameter(GenericConstants.PROPERTIES_FILE_PATH));
	super.init(config);
	try{
            propertiesFilePath = config.getInitParameter(GenericConstants.PROPERTIES_FILE_PATH);
            initConfigurator = new InitConfigurator(propertiesFilePath);
            boolean flag = initConfigurator.initialize();
            if(!flag){
		logger.error("ApplicationController - init: Failed to initialize prerequisites properties from InitConfigurator. System will be terminated.");		
            }else{
		logger.info("ApplicationController - init: initialized prerequisites properties successfully from InitConfigurator.");
            }
	}catch(Exception ex){
            logger.fatal("ApplicationController - init: Problem occurred during initializing the application. Problem occurred in InitConfigurator." + ex.getMessage());
            logger.fatal("ApplicationController - init: System is terminated.");
            System.exit(1);
        }
    }		

    @Override
    public void destroy(){
        logger.info("ApplicationController - destroy: In Destroy: Clearing the resources and terminating Business threads...");
	initConfigurator.terminate();
	logger.info("ApplicationController - destroy: In Destroy: Clearing the resources and terminating Business threads is done.");
    }
}
