package com.jobreporting.servicesBusiness.business.actions;
import com.jobreporting.entities.WSCliente;
import com.jobreporting.entities.WSProducto;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSSyncherRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSSyncherResponse;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ProductosDao;
import java.util.ArrayList;

public class SyncherAction{
    public static LoggerManager logger = GenericUtility.getLogger(SyncherAction.class.getName());
  
    public WSBaseResponse execute(WSBaseRequest request) throws Exception{
        logger.debug("-----------------SYNC ACTION-------------------");
        WSSyncherRequest wsSyncherRequest = (WSSyncherRequest)request;
        WSSyncherResponse wsSyncherResponse = null;
        try{
            wsSyncherResponse = sync(wsSyncherRequest);
        }catch(Exception ex){
            logger.debug("SyncherAction - execute: ha ocurrido la siguiente excepcion: : " + ex.getMessage());            
        }
        return wsSyncherResponse;
    }
  
    public WSSyncherResponse sync(WSSyncherRequest wsSyncherRequest){
        WSSyncherResponse wsSyncherResponse = new WSSyncherResponse();
        ProductosDao productosDao = new ProductosDao();
        ClienteDao clientesDao = new ClienteDao();
        
        ArrayList<WSProducto> productos = productosDao.GetProductos();
        ArrayList<WSCliente> clientes = clientesDao.GetClientes();
        
        wsSyncherResponse.setStatus("200");       
        wsSyncherResponse.setResponse("ok");
        wsSyncherResponse.setClientes(clientes);
        wsSyncherResponse.setProductos(productos);
       
        return wsSyncherResponse;
    }
}
