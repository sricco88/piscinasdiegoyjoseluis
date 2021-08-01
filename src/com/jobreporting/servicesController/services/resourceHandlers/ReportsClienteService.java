package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.servicesBusiness.business.actions.ReportsClienteAction;
import com.jobreporting.servicesController.constants.ExceptionConstants;
import com.jobreporting.servicesController.utilities.ServiceUtility;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;

@Path("/reportscliente")
public class ReportsClienteService{
    public static LoggerManager logger = GenericUtility.getLogger(ReportsService.class.getName());  
    @POST
    @Consumes({"*/*"})
    @Produces({"application/octet-stream"})
    
    public StreamingOutput reportClienteSubmit(byte[] requestBStream){
        StreamingOutput responseStream = null;        
        WSBaseResponse response = null;
        try{
            if(null != requestBStream){
                Object obj = ServiceUtility.deSerializeServiceRequest(requestBStream);
                if((obj != null) && ((obj instanceof WSBaseRequest))){
                    WSBaseRequest request = (WSBaseRequest)obj;
                    ReportsClienteAction reportsClienteAction = new ReportsClienteAction();
                    response = reportsClienteAction.execute(request);
                    if(null != response) {
                        response.setStatus(ExceptionConstants.SERVICE_STATUS_SUCCESS);
                    }else{
                        logger.debug("ReportClienteService - reportSubmit: la respuesta es null");
                    } 
                }else{
                    logger.debug("ReportClienteService - reportSubmit: el request es null");
                }
            }
        }catch(Exception ex){
            logger.debug("ReportClienteService - reportSubmit: ha ocurrido la siguiente excepcion: "+ex.getMessage());
        }   
        try{
            responseStream = ServiceUtility.serializeServiceResponse(response);
        }catch (Exception ex){
            logger.error("ReportClienteService - reportSubmit: Excpetion occurred while serializing the repsonse into stream : " + ex.getMessage());
            logger.error("ReportClienteService - reportSubmit: To resume the service request, null response will be send to prevent the unexpected waiting behaviour in client.");
        }
        return responseStream;     
                    
    }
    
}
