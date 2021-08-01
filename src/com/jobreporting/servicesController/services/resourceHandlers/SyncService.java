package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.servicesBusiness.business.actions.SyncherAction;
import com.jobreporting.servicesController.constants.ExceptionConstants;
import com.jobreporting.servicesController.utilities.ServiceUtility;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;

@Path("/sync")
public class SyncService{
    public static LoggerManager logger = GenericUtility.getLogger(SyncService.class.getName());
  
    @POST
    @Consumes({"*/*"})
    @Produces({"application/octet-stream"})
    public StreamingOutput sync(byte[] requestBStream){
        StreamingOutput responseStream = null;
        WSBaseResponse response = null;
        try{
            if(null != requestBStream){
                Object obj = ServiceUtility.deSerializeServiceRequest(requestBStream);
                if((obj != null) && ((obj instanceof WSBaseRequest))){
                    WSBaseRequest request = (WSBaseRequest)obj;
                    SyncherAction syncherAction = new SyncherAction();
                    response = syncherAction.execute(request);                   
                    if(null != response){
                        response.setStatus(ExceptionConstants.SERVICE_STATUS_SUCCESS);
                    }else{
                        logger.debug("SyncherService - syncherAction: la respuesta es null");
                    }
                }else{                 
                    logger.debug("SyncherService - syncherAction: el request es null");
                }
            }
        }catch(Exception ex){
            logger.debug("SyncherService - syncherAction: ha ocurrido la siguiente excepcion: "+ex.getMessage());
        }try{
            responseStream = ServiceUtility.serializeServiceResponse(response);
        }catch (Exception ex){
            logger.error("SyncherService - syncherAction: Excpetion occurred while serializing the repsonse into stream : " + ex.getMessage());
            logger.error("SyncherService - syncherAction: To resume the service request, null response will be send to prevent the unexpected waiting behaviour in client.");
        }       
        return responseStream;
    }
}
