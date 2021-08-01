package com.jobreporting.servicesBusiness.business.actions;
import com.jobreporting.entities.WSAcceso;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSTokenRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSTokenResponse;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.AccesoDao;

public class TokenAction {
    public static LoggerManager logger = GenericUtility.getLogger(TokenAction.class.getName());
    
    public WSBaseResponse execute(WSBaseRequest request) throws Exception{
        logger.debug("-----------------TOKEN ACTION-------------------");
        WSTokenRequest wsTokenRequest = (WSTokenRequest)request;
        WSTokenResponse wsTokenResponse = null;
        try{
            wsTokenResponse = token(wsTokenRequest);
        }catch(Exception ex){
            logger.debug("SyncherAction - execute: ha ocurrido la siguiente excepcion: : " + ex.getMessage());            
        }
        return wsTokenResponse;    
    }
    
    public WSTokenResponse token(WSTokenRequest wsTokenRequest){
        WSTokenResponse wsTokenResponse = new WSTokenResponse();
        AccesoDao accesoDao = new AccesoDao();
        WSAcceso datosAcceso = accesoDao.GetDatosAcceso(wsTokenRequest.getIdWorker());
        wsTokenResponse.setStatus("200");       
        wsTokenResponse.setResponse("ok");
        wsTokenResponse.setAcceso(datosAcceso);
        return wsTokenResponse;
    } 
}
