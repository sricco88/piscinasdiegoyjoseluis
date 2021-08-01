package com.jobreporting.servicesController.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import com.jobreporting.generic.exception.base.ExceptionDetail;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.responses.WSErrorResponse;


public class ServiceUtility{
    public static LoggerManager logger = GenericUtility.getLogger(ServiceUtility.class.getName());
    public static WSErrorResponse generateErrorResponse(ExceptionDetail exDetail){
	WSErrorResponse errorResponse = new WSErrorResponse();
	errorResponse.setStatus(exDetail.getStatus());
	errorResponse.setCode(exDetail.getCode());
	errorResponse.setUserMessage(exDetail.getUserMessage());
	errorResponse.setErrorMessage(exDetail.getErrorMessage());
	return errorResponse;
    }
	
    public static Object deSerializeServiceRequest(byte[] byteObject){
	Object obj = null;
	try{
            ByteArrayInputStream bInStream = new ByteArrayInputStream(byteObject);
            ObjectInputStream oInStream = new ObjectInputStream(bInStream);
            obj = (Object)oInStream.readObject();
            oInStream.close();
            bInStream.close();
	}catch(Exception ex){
            logger.error("ServiceUtility - deSerializeServiceRequest: ha ocurrido la siguiente excepcion: " + ex.getMessage()); 
	}
	return obj;
    }
	
    public static StreamingOutput serializeServiceResponse(final Object response){
	StreamingOutput responseStream = null;
	responseStream = new StreamingOutput(){
            public void write(OutputStream outStream) throws IOException, WebApplicationException {
	        ObjectOutputStream oOutStream = new ObjectOutputStream(outStream);
	        oOutStream.writeObject(response);
	    }
	};
	return responseStream;
    }    
}
