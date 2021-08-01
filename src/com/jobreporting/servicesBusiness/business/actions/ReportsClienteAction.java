package com.jobreporting.servicesBusiness.business.actions;
import com.jobreporting.entities.WSClienteReport;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSClienteReportRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSClienteReportResponse;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;

public class ReportsClienteAction {
    public static LoggerManager logger = GenericUtility.getLogger(ReportsClienteAction.class.getName());
    
    public WSBaseResponse execute(WSBaseRequest request) throws Exception{
        WSClienteReportRequest wsReportsRequest = (WSClienteReportRequest)request;
        WSClienteReportResponse wsClienteReportResponse = new WSClienteReportResponse();
        try{
            wsClienteReportResponse = SaveReportCliente(wsReportsRequest);
        }catch(Exception ex){
            logger.debug("Reports Action: ha ocurrido la siguiente excepcion: " + ex.getMessage());     
        }
        return wsClienteReportResponse;
    }
    
    public WSClienteReportResponse SaveReportCliente(WSClienteReportRequest request){
        logger.debug("--------SaveReportCliente-------");
        WSClienteReportResponse wsClienteReportResponse = new WSClienteReportResponse();         
        boolean isClienteUpdated = false;      
        
        if(request.getCliente() != null){
            ClienteDao clienteDao = new ClienteDao();
            int idCliente = request.getCliente().getIdCliente();
            if(idCliente != -1){
                WSClienteReport clienteBBDD = clienteDao.GetDatosImportantes(idCliente);
                if(clienteBBDD != null){
                   WSClienteReport clienteRequest = request.getCliente();
                   String matices = clienteBBDD.getMatices()+" "+clienteRequest.getMatices();
                   String descripcionPiscina = clienteBBDD.getDescripcionPiscina()+" "+clienteRequest.getDescripcionPiscina();
                   String descripcionSalaTecnica = clienteBBDD.getDescripcionSalaTecnica()+" "+clienteRequest.getDescripcionSalaTecnica();
                   String accesorios = clienteBBDD.getAccesorios()+" "+clienteRequest.getAccesorios();
                   isClienteUpdated = clienteDao.UpdateDatosImportantesCliente(idCliente,matices,descripcionPiscina,descripcionSalaTecnica,accesorios);
                    if(isClienteUpdated){
                       logger.debug("SaveReportCliente - Exito - los datos del cliente con id: "+idCliente+" se han actualizado correctamente");
                    }else{
                       logger.debug("SaveReportCliente - Error - los datos del cliente con id: "+idCliente+" no se han actualizado correctamente");
                    }                   
                }else{
                    logger.debug("SaveReportCliente - Error - no se ha encontrado el cliente con el id especificado: "+idCliente);  
                }
            }else{
                logger.debug("SaveReportCliente - Error - el cliente del request no es null pero el idCliente tiene un valor incorrecto");  
            }     
        }else{
            logger.debug("SaveReportCliente - Error - el cliente del request es null");  
        }   
        
        wsClienteReportResponse.setStatus("200");       
        wsClienteReportResponse.setResponse("ok");
        if(isClienteUpdated){
            wsClienteReportResponse.setIdClienteReporte(request.getCliente().getIdCliente());  
        }else{
            wsClienteReportResponse.setIdClienteReporte(-1);  
        }        
        return wsClienteReportResponse;
    }      
}
