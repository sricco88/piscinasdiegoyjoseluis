package com.jobreporting.servicesBusiness.business.actions;
import com.jobreporting.entities.WSAcceso;
import com.jobreporting.entities.WSCliente;
import com.jobreporting.entities.WSProducto;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSOneTimeAuthRequest;
import com.jobreporting.responses.WSOneTimeAuthResponse;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.LoginDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ProductosDao;
import java.util.ArrayList;

public class OneTimeAuthAction{
    public static LoggerManager logger = GenericUtility.getLogger(OneTimeAuthAction.class.getName());    
    
    public WSOneTimeAuthResponse execute(WSBaseRequest request){
        logger.debug("-----------------AUTH ACTION-------------------");
        WSOneTimeAuthRequest wsOneTimeAuthRequest = (WSOneTimeAuthRequest)request;
        WSOneTimeAuthResponse wsOneTimeAuthResponse = null;
        try{
            wsOneTimeAuthResponse = oneTimeAuth(wsOneTimeAuthRequest);
        }catch(Exception ex){
            logger.debug("OneTimeAuthAction - execute: ha ocurrido la siguiente excepcion: : " + ex.getMessage());            
        }    
        return wsOneTimeAuthResponse;
    }
  
    public WSOneTimeAuthResponse oneTimeAuth(WSOneTimeAuthRequest wsOneTimeAuthRequest){
        WSOneTimeAuthResponse wsOneTimeAuthResponse = new WSOneTimeAuthResponse();          
        LoginDao loginDao = new LoginDao(); 
        ProductosDao productosDao = new ProductosDao();
        ClienteDao clientesDao = new ClienteDao();
        boolean isLoginValid = loginDao.Login(wsOneTimeAuthRequest.getUsuario(),wsOneTimeAuthRequest.getPassword());        
        if(isLoginValid){ 
            int idWorker = loginDao.GetIDWorker(wsOneTimeAuthRequest.getUsuario(),wsOneTimeAuthRequest.getPassword());
            if(idWorker != -1){
                boolean doesTokenExists = loginDao.IsTokenCreated(idWorker);                
                if(doesTokenExists){
                    int isTokenDeleted = loginDao.DeleteToken(idWorker);
                    if(isTokenDeleted != -1){
                        int isTokenCreated = loginDao.CreateToken(idWorker);
                        if(isTokenCreated != -1){
                            WSAcceso datosAcceso = loginDao.GetDatosAcceso(idWorker);                        
                            if(datosAcceso != null){                           
                                wsOneTimeAuthResponse.setLogin(true);
                                wsOneTimeAuthResponse.setMensaje("Usuario validado correctamente."); 
                                wsOneTimeAuthResponse.setStatus("200");
                                wsOneTimeAuthResponse.setResponse("ok");                            
                                wsOneTimeAuthResponse.setDatosAcceso(datosAcceso);
                                ArrayList<WSProducto> productos = productosDao.GetProductos();
                                wsOneTimeAuthResponse.setProductos(productos);
                                ArrayList<WSCliente> clientes = clientesDao.GetClientes();
                                wsOneTimeAuthResponse.setClientes(clientes);                            
                            }else{
                                wsOneTimeAuthResponse.setLogin(false);
                                wsOneTimeAuthResponse.setMensaje("Hubo algun problema recuperando los datos de acceso."); 
                                wsOneTimeAuthResponse.setStatus("200");
                                wsOneTimeAuthResponse.setResponse("ok");
                                wsOneTimeAuthResponse.setDatosAcceso(null);
                                wsOneTimeAuthResponse.setProductos(null);
                                wsOneTimeAuthResponse.setClientes(null);
                            }                         
                        }else{
                            wsOneTimeAuthResponse.setLogin(false);
                            wsOneTimeAuthResponse.setMensaje("Ha habido algun problema creando el token de acceso. Ponte en contacto con el administrador.");
                            wsOneTimeAuthResponse.setStatus("200");
                            wsOneTimeAuthResponse.setResponse("ok");
                            wsOneTimeAuthResponse.setDatosAcceso(null);
                            wsOneTimeAuthResponse.setProductos(null);
                            wsOneTimeAuthResponse.setClientes(null);
                        } 
                    }else{
                        wsOneTimeAuthResponse.setLogin(false);
                        wsOneTimeAuthResponse.setMensaje("Ha habido algun problema borrando el token de acceso. Ponte en contacto con el administrador.");
                        wsOneTimeAuthResponse.setStatus("200");
                        wsOneTimeAuthResponse.setResponse("ok");
                        wsOneTimeAuthResponse.setDatosAcceso(null);
                        wsOneTimeAuthResponse.setProductos(null);
                        wsOneTimeAuthResponse.setClientes(null);
                    }
                }else{
                    int isTokenCreated = loginDao.CreateToken(idWorker);
                    if(isTokenCreated != -1){
                        WSAcceso datosAcceso = loginDao.GetDatosAcceso(idWorker);                        
                        if(datosAcceso != null){                           
                            wsOneTimeAuthResponse.setLogin(true);
                            wsOneTimeAuthResponse.setMensaje("Usuario validado correctamente."); 
                            wsOneTimeAuthResponse.setStatus("200");
                            wsOneTimeAuthResponse.setResponse("ok");                            
                            wsOneTimeAuthResponse.setDatosAcceso(datosAcceso);
                            ArrayList<WSProducto> productos = productosDao.GetProductos();
                            wsOneTimeAuthResponse.setProductos(productos);
                            ArrayList<WSCliente> clientes = clientesDao.GetClientes();
                            wsOneTimeAuthResponse.setClientes(clientes);                            
                        }else{
                            wsOneTimeAuthResponse.setLogin(false);
                            wsOneTimeAuthResponse.setMensaje("Hubo algun problema recuperando los datos de acceso."); 
                            wsOneTimeAuthResponse.setStatus("200");
                            wsOneTimeAuthResponse.setResponse("ok");
                            wsOneTimeAuthResponse.setDatosAcceso(null);
                            wsOneTimeAuthResponse.setProductos(null);
                            wsOneTimeAuthResponse.setClientes(null);
                        }                         
                    }else{
                        wsOneTimeAuthResponse.setLogin(false);
                        wsOneTimeAuthResponse.setMensaje("Ha habido algun problema creando el token de acceso. Ponte en contacto con el administrador.");
                        wsOneTimeAuthResponse.setStatus("200");
                        wsOneTimeAuthResponse.setResponse("ok");
                        wsOneTimeAuthResponse.setDatosAcceso(null);
                        wsOneTimeAuthResponse.setProductos(null);
                        wsOneTimeAuthResponse.setClientes(null);
                    } 
                }
            }else{
                wsOneTimeAuthResponse.setLogin(false);
                wsOneTimeAuthResponse.setMensaje("El usuario y/o contraseña son incorrectos. Comprueba los datos o ponte en contacto con el administrador");
                wsOneTimeAuthResponse.setStatus("200");
                wsOneTimeAuthResponse.setResponse("ok");
                wsOneTimeAuthResponse.setDatosAcceso(null);
                wsOneTimeAuthResponse.setProductos(null);
                wsOneTimeAuthResponse.setClientes(null);
            }   
        }else{           
            wsOneTimeAuthResponse.setLogin(false);
            wsOneTimeAuthResponse.setMensaje("El usuario y/o contraseña son incorrectos. Comprueba los datos o ponte en contacto con el administrador");          
            wsOneTimeAuthResponse.setStatus("200");
            wsOneTimeAuthResponse.setResponse("ok");
            wsOneTimeAuthResponse.setDatosAcceso(null);  
            wsOneTimeAuthResponse.setProductos(null);
            wsOneTimeAuthResponse.setClientes(null);
        }
        return wsOneTimeAuthResponse;
    }
}
