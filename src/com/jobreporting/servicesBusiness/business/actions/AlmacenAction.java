package com.jobreporting.servicesBusiness.business.actions;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jobreporting.entities.WSSalidasAlmacen;
import com.jobreporting.entities.WSSalidasAlmacenLineas;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSSalidasAlmacenRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSSalidasAlmacenResponse;
import static com.jobreporting.servicesBusiness.business.actions.ReportsAction.logger;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.AlmacenDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class AlmacenAction {
    public static LoggerManager logger = GenericUtility.getLogger(AlmacenAction.class.getName());
    public WSBaseResponse execute(WSBaseRequest request) throws Exception{
        WSSalidasAlmacenRequest wsSalidasAlmacenRequest = (WSSalidasAlmacenRequest)request;
        WSSalidasAlmacenResponse wsSalidasAlmacenResponse = new WSSalidasAlmacenResponse();
        try{
            wsSalidasAlmacenResponse = SaveSalidasAlmacen(wsSalidasAlmacenRequest);
        }catch(Exception ex){
            logger.debug("Almacen Action: ha ocurrido la siguiente excepcion: " + ex.getMessage());     
        }
        return wsSalidasAlmacenResponse;
    }
    
    public WSSalidasAlmacenResponse SaveSalidasAlmacen(WSSalidasAlmacenRequest request){
        logger.debug("--------SaveSalidasAlmacen-------");
        WSSalidasAlmacenResponse wsSalidasAlmacenResponse = new WSSalidasAlmacenResponse();
        int idSalidasAlmacen = -1;
        int idLineaAlmacen = -1;
        AlmacenDao almacenDao = new AlmacenDao();
        WSSalidasAlmacen salidasAlmacen = new WSSalidasAlmacen();
        salidasAlmacen = request.getSalidasAlmacen();
        JSch jsch = new JSch();
        Session session = null;
        String currentDirectorio = "";        
        
        //PRIMERO GUARDAMOS LA CABECERA
        Date date = new Date();
        String horaWS = new SimpleDateFormat("HH:mm:ss").format(date);
        String fechaWS = new SimpleDateFormat("yyyy-MM-dd").format(date);
        
        idSalidasAlmacen = almacenDao.SaveSalidasAlmacen(salidasAlmacen,fechaWS,horaWS);
        if(idSalidasAlmacen != -1){
            for(WSSalidasAlmacenLineas linea : request.getLineas()){
                idLineaAlmacen = almacenDao.SaveSalidasAlmacenLineas(idSalidasAlmacen,linea);
            }
            
            //CREAMOS SESSION SFTP          
            try{
                session = jsch.getSession("root","10.132.0.9");              
                session.setConfig("PreferredAuthentications","publickey,gssapi-with-mic,keyboard-interactive,password");
                jsch.addIdentity("/opt/bitnami/apache-tomcat/webapps/nginx");
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
            }catch(Exception ex){
                logger.debug("Exception creando objeto para conexion sftp: "+ex.getMessage());
            }
            
            //CREAMOS EL DIRECTORIO REMOTO PADRE SAIDSALIDASALMCEN
            String pathRemoto = "/var/www/html/backoffice/diegoyjoseluis/main/salidas_almacen";
            try{
                if(!session.isConnected()){session.connect();}
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();
                boolean existeDirectorio = false;
                currentDirectorio = "SA"+idSalidasAlmacen;
                Vector<ChannelSftp.LsEntry> directoryEntries = channelSftp.ls(pathRemoto);
                for(ChannelSftp.LsEntry directorio : directoryEntries){               
                    if(directorio.getFilename().equals(currentDirectorio)){
                        existeDirectorio = true;
                    }
                }

                if(!existeDirectorio){
                    channelSftp.mkdir(pathRemoto+"/"+currentDirectorio);
                    channelSftp.chmod(509,pathRemoto+"/"+currentDirectorio);                
                }else{
                    logger.debug("El directorio remoto ya existe.");
                }
                channelSftp.disconnect();
            }catch(Exception ex){
                logger.debug("Exception: error al crear directorio padre remoto: "+ex.getMessage());
            }
                
            //CREATE PDF AND CORREO
            try{
                if(!session.isConnected()){session.connect();}       
                ChannelExec channelExec = (ChannelExec)session. openChannel("exec");                
                InputStream in = channelExec.getInputStream();                    
                String command = "php -f /var/www/html/backoffice/diegoyjoseluis/main/callSalidasAlmacenPublisher.php idSalidasAlmacen="+idSalidasAlmacen;                
                channelExec.setCommand(command);
                channelExec.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String linea;
                while((linea = reader.readLine()) != null){
                    builder.append(linea);                    
                }                
                logger.debug("DYJ creando pdf salidas almacen: "+builder.toString());
                channelExec.disconnect();
                session.disconnect();
            }catch(JSchException ex1){
                logger.debug("Exception subiendo PDF salidas almacen: "+ex1.getMessage());
            }catch(IOException ex2){
                logger.debug("Exception subiendo PDF salidas almacen: "+ex2.getMessage());
            }
            
        }
        wsSalidasAlmacenResponse.setStatus("200");       
        wsSalidasAlmacenResponse.setResponse("ok");
        wsSalidasAlmacenResponse.setIdSalidasAlmacen(idSalidasAlmacen);        
        return wsSalidasAlmacenResponse;
    }
}
