package com.jobreporting.servicesBusiness.business.actions;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jobreporting.classes.FechaContador;
import com.jobreporting.entities.WSInformacionGeneral;
import com.jobreporting.entities.WSMediciones;
import com.jobreporting.entities.WSProductoReport;
import com.jobreporting.entities.WSTareas;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.requests.WSBaseRequest;
import com.jobreporting.requests.WSReportsRequest;
import com.jobreporting.responses.WSBaseResponse;
import com.jobreporting.responses.WSReportsResponse;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ReporteDao;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.imageio.ImageIO;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class ReportsAction{
    public static LoggerManager logger = GenericUtility.getLogger(ReportsAction.class.getName());   
    
    public WSBaseResponse execute(WSBaseRequest request) throws Exception{
        WSReportsRequest wsReportsRequest = (WSReportsRequest)request;
        WSReportsResponse wsReportsResponse = new WSReportsResponse();
        try{
            wsReportsResponse = SaveReport(wsReportsRequest);
        }catch(Exception ex){
            logger.debug("Reports Action: ha ocurrido la siguiente excepcion: " + ex.getMessage());     
        }
        return wsReportsResponse;
    }
  
    public WSReportsResponse SaveReport(WSReportsRequest request){
        //SFTP VARIABLES
        JSch jsch = new JSch();
        Session session = null;
        String pathLocal = "";   
        String currentDirectorio = "";
        String signRelativePath = "";
        String foto1RelativePath = "";
        String foto2RelativePath = "";
        String foto3RelativePath = "";
        String foto4RelativePath = "";
        String foto5RelativePath = "";        
        
        logger.debug("--------SaveReport-------");
        WSReportsResponse wsReportsResponse = new WSReportsResponse();     
        int idReporte = -1; 
        int idTareas = -1;
        int idMediciones = -1;
        int idProducto = -1;
        boolean alarmaFuga = false;        
        
        ClienteDao clienteDao = new ClienteDao();
        ReporteDao reporteDao = new ReporteDao();
        WSInformacionGeneral reporte = new WSInformacionGeneral();
        reporte = request.getReporte();
        int idCliente = clienteDao.GetIDClienteByNombre(reporte.getCliente());      
        
        //FIRST INSERT REPORT CABECERA     
        Date date = new Date();
        String horaReceived = new SimpleDateFormat("HH:mm:ss").format(date);
        String fechaReceived = new SimpleDateFormat("yyyy-MM-dd").format(date);
        idReporte = reporteDao.SaveReporte(reporte,horaReceived,fechaReceived);
        if(idReporte != -1){
            //INSERT TAREAS
            WSTareas tareas = new WSTareas();
            tareas = request.getTareas();
            idTareas = reporteDao.SaveTareas(idReporte,tareas);  
            
            //INSERT MEDICIONES
            WSMediciones mediciones = new WSMediciones();
            mediciones = request.getMediciones();
            idMediciones = reporteDao.SaveMediciones(idReporte,mediciones);
            
            //INSERT PRODUCTOS
            ArrayList<WSProductoReport> productos = new ArrayList<>();
            productos = request.getProductos();
            for(WSProductoReport producto : productos){
                idProducto = reporteDao.SaveProducto(idReporte,producto);
            }
            
            //UPDATE CONTADOR IN TABLE CLIENTE IF VALUE COMMING FROM REPORT IS NOT NULL AND DIFRENTE THAN 0.0
            if(request.getMediciones() != null && request.getMediciones().getContador() !=0.0){                
                if(idCliente != -1){
                    boolean isClienteUpdated = clienteDao.UpdateLecturaContador(idCliente,request.getMediciones().getContador());
                    if(isClienteUpdated){
                        logger.debug("ReportAction - SaveReport - Exito: se ha actualizado correctamente el contador del cliente con id: "+idCliente);
                    }else{
                        logger.debug("ReportAction - SaveReport - Error: no se ha actualizado correctamente el contador del cliente con id: "+idCliente);
                    }
                }else{
                    logger.debug("ReportAction - SaveReport - Error: imposible actualizar el contador del cliente, no se ha encondo su id");
                }               
            }
            
            
            //CALCULAMOS SI SE HA PRODUCIDO UN CONSUMO DE AGUA ELEVADO EN EL CLIENTE
            boolean tieneContadorIndividual = false;
            tieneContadorIndividual = clienteDao.TieneContadorIndividual(reporte.getCliente());
            double consumoDiarioReal = 0;
            int numDias = 0;
            double consumoDiarioTeoricoMesAnterior = 0;
            double consumoDiarioTeoricoMesActual = 0;
            
            //1-TIENE CONTADOR INDIVIDUAL?
            if(tieneContadorIndividual){
                //2-HAY AL MENOS UNA MEDICION ANTERIOR?
                boolean hayMedicionAnterior = clienteDao.HayMedicionAnterior(reporte.getCliente());
                if(hayMedicionAnterior){
                    //DEFINO CIERTAS VARIABLES NECESARIAS PARA LOS CALCULOS
                    double[] consumosDiarioTeorico = clienteDao.GetConsumosDiarioTeorico(reporte.getCliente());
                    
                    //DATOS RELACIONES CON LA MEDICION ANTERIOR
                    FechaContador fechaContadorAnterior = reporteDao.GetFechaContadorAnteriorMedicion(reporte.getCliente());
                    String fechaMedicionAnterior = fechaContadorAnterior.getFecha();
                    double contadorMedicionAnterior = fechaContadorAnterior.getContador();
                    
                    //DATOS RELACIONADOS CON LA MEDICION ACTUAL
                    double contadorMedicionActual = mediciones.getContador();
                    String fechaMedicionActual = reporte.getFechaCreated();
                    
                    logger.debug("ReportAction - SaveReport - contadorMedicionActual: "+contadorMedicionActual);
                    logger.debug("ReportAction - SaveReport - fechaMedicionActual: "+fechaMedicionActual);
                    
                    numDias = Days.daysBetween(new LocalDate(fechaMedicionAnterior), new LocalDate(fechaMedicionActual)).getDays(); 
                    logger.debug("ReportAction - SaveReport - numDias: "+numDias);
                    
                    if(numDias <= 31 && numDias >= 0){
                        if(numDias == 0){
                            numDias++;
                        }
                        
                        //MIRAR SI MEDICIONES CAEN EN MISMO MES
                        LocalDate dateMedicionAnterior = new LocalDate(fechaMedicionAnterior);
                        int mesMedicionAnterior = dateMedicionAnterior.getMonthOfYear();

                        LocalDate dateMedicionActual = new LocalDate(fechaMedicionActual);
                        int mesMedicionActual = dateMedicionActual.getMonthOfYear();

                        logger.debug("ReportAction - SaveReport - Mes medicion anterior: "+mesMedicionAnterior);
                        logger.debug("ReportAction - SaveReport - Mes medición actual: "+mesMedicionActual);               

                        //CALCULAMOS CONSUMO DIARIO REAL                       
                        consumoDiarioReal = (contadorMedicionActual - contadorMedicionAnterior) / numDias;
                        consumoDiarioReal = Math.round(consumoDiarioReal * 100.0) / 100.0;
                        logger.debug("ReportAction - SaveReport - consumoDiarioReal: "+consumoDiarioReal);                    

                        //BUSCAMOS EL CONSUMO TEORICO DEL MES INICIAL/ANTERIOR
                        consumoDiarioTeoricoMesAnterior = consumosDiarioTeorico[mesMedicionAnterior -1];
                        logger.debug("ReportAction - SaveReport - consumoDiarioTeoricoMesAnterior: "+consumoDiarioTeoricoMesAnterior);

                        //BUSCAMOS EL CONSUMO TEORICO DEL MES ACTUAL
                        consumoDiarioTeoricoMesActual = consumosDiarioTeorico[mesMedicionActual -1];
                        logger.debug("ReportAction - SaveReport - consumoDiarioTeoricoMesActual: "+consumoDiarioTeoricoMesActual);               

                        //COMPARAMOS SI HEMOS SUPERADO EL UMBRAL DE CONSUMO DIARIO
                        if((consumoDiarioReal >= consumoDiarioTeoricoMesAnterior) || (consumoDiarioReal >= consumoDiarioTeoricoMesActual)){
                           logger.debug("ReportAction - SaveReport - Se ha superado el umbral de consumo, enviar correo.");
                           alarmaFuga = true;
                        } 
                    }else{
                        //EL RANGO DE DIAS DE LAS MEDICIONES SUPERA UN MES, ENVIO DE CORREO
                        logger.debug("ReportAction - SaveReport - Rango dias mediciones supera 1 mes.");
                    }                
                }             
            }
            
            if(idReporte != -1){ 
                pathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/";
                //CREAMOS OBJETO DE CONEXION SFTP
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
            
                //CREAMOS EL DIRECTORIO LOCAL PADRE RIDREPORTE SOLO EN CASO DE QUE HAYA ALGUNA FOTO O FIRMA Y EL DIRECTORIO LOCAL NO EXISTA
                if(request.getReporte().getFirma() != null || request.getReporte().getFoto1() != null || request.getReporte().getFoto2() != null || request.getReporte().getFoto3() != null || request.getReporte().getFoto4() != null || request.getReporte().getFoto5() != null){        
                    if(!Files.exists(Paths.get(pathLocal))){
                        try{
                            File file = new File(pathLocal);
                            file.setExecutable(true);
                            file.setReadable(true);
                            file.setWritable(true);
                            if(!file.exists()){
                                if(file.mkdir()){
                                    logger.debug("directorio creado correctamente");
                                }else{
                                    logger.debug("no se ha podido crear el directorio");
                                }
                            }
                            //using PosixFilePermission to set file permissions 770
                            Set<PosixFilePermission> perms = new HashSet<>();
                            //add owners permission
                            perms.add(PosixFilePermission.OWNER_READ);
                            perms.add(PosixFilePermission.OWNER_WRITE);
                            perms.add(PosixFilePermission.OWNER_EXECUTE);
                            //add group permissions
                            perms.add(PosixFilePermission.GROUP_READ);
                            perms.add(PosixFilePermission.GROUP_WRITE);
                            perms.add(PosixFilePermission.GROUP_EXECUTE);
                            Files.setPosixFilePermissions(Paths.get(pathLocal), perms); 
                        }catch(IOException ex){
                            logger.debug("IOException al dar permisos al directorio padre local: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("El directorio padre local ya existe");
                    }
                }else{
                    logger.debug("El directorio padre local no se creara porque no no hay ni firma ni fotos.");
                }     
            
                //CREAMOS EL DIRECTORIO REMOTO PADRE RIDREPORTE SI NO EXISTE SE CREARA IGUALMENTE AUNQUE NO HAYA NI FIRMA NI FOTO 
                String pathRemoto = "/var/www/html/backoffice/diegoyjoseluis/main/reportes";
                try{
                    if(!session.isConnected()){session.connect();}
                    ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                    channelSftp.connect();
                    boolean existeDirectorio = false;
                    currentDirectorio = "R"+idReporte;
                    Vector<LsEntry> directoryEntries = channelSftp.ls(pathRemoto);
                    for(LsEntry directorio : directoryEntries){               
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
            
                //AHORA QUE TENEMOS LOS DIRECTORIOS PADRES LISTOS EMPEZAMOS
                //GUARDAMOS FIRMA O FOTO EN DIRECTORIO PADRE LOCAL Y LUEGO LOS SUBIMOS POR SFTP AL DIRECTORIO PADRE REMOTO

                //FIRMA
                if(request.getReporte().getFirma() != null){
                    //CREAMOS LA FIRMA LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFIRMA                
                    try{
                        String signPathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/firma.jpg";
                        InputStream in_firma = new ByteArrayInputStream(request.getReporte().getFirma());
                        BufferedImage bImageFromConvert_firma = ImageIO.read(in_firma);
                        File f = new File(signPathLocal);
                        ImageIO.write(bImageFromConvert_firma, "jpg", new File(signPathLocal)); 
                        f.setReadable(true,false);
                        in_firma.reset();  
                        bImageFromConvert_firma.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo firma: "+ex.getMessage()); 
                    }

                    //SUBIRMOS POR SFTP LA FIRMA A NGINX. COMPRABMOS PRIMERO QUE LA FIRMA EXISTE EN DIRECTORIO LOCAL
                    Path pathFirma = Paths.get(pathLocal+"firma.jpg");                
                    if(Files.exists(pathFirma)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FIRMA LOCAL
                            File firma = new File(pathLocal+"firma.jpg");
                            InputStream firmaUpload = new FileInputStream(firma);   

                            String pathFirmaRemoto = pathRemoto+"/"+currentDirectorio+"/firma.jpg";                         
                            channelSftp.put(firmaUpload,pathFirmaRemoto);
                            channelSftp.chmod(509,pathFirmaRemoto);
                            signRelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/firma.jpg";
                            channelSftp.disconnect();
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la firma a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La firma no se ha creado correctamente.");
                        signRelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/firma.jpg";
                    } 
                } 
                
                //FOTO1
                if(request.getReporte().getFoto1() != null){
                    //CREAMOS LA FOTO1 LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFOTO1                
                    try{
                        String foto1PathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/foto1.jpg";
                        InputStream in_foto1 = new ByteArrayInputStream(request.getReporte().getFoto1());
                        BufferedImage bImageFromConvert_foto1 = ImageIO.read(in_foto1);
                        File f = new File(foto1PathLocal);
                        ImageIO.write(bImageFromConvert_foto1, "jpg", new File(foto1PathLocal)); 
                        f.setReadable(true,false);
                        in_foto1.reset();  
                        bImageFromConvert_foto1.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo foto1: "+ex.getMessage()); 
                    }
                    
                    //SUBIRMOS POR SFTP LA FOTO1 A NGINX. COMPRABMOS PRIMERO QUE LA FOTO1 EXISTE EN DIRECTORIO LOCAL
                    Path pathFoto1 = Paths.get(pathLocal+"foto1.jpg");                
                    if(Files.exists(pathFoto1)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FOTO1 LOCAL
                            File foto1 = new File(pathLocal+"foto1.jpg");
                            InputStream foto1Upload = new FileInputStream(foto1);   

                            String pathFoto1Remoto = pathRemoto+"/"+currentDirectorio+"/foto1.jpg";                         
                            channelSftp.put(foto1Upload,pathFoto1Remoto);
                            channelSftp.chmod(509,pathFoto1Remoto);
                            foto1RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto1.jpg";
                            channelSftp.disconnect();
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la foto1 a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La foto1 no se ha creado correctamente.");
                        foto1RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto1.jpg";
                    }                    
                }
                
                //FOTO2
                if(request.getReporte().getFoto2() != null){
                    //CREAMOS LA FOTO2 LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFOTO2                
                    try{
                        String foto2PathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/foto2.jpg";
                        InputStream in_foto2 = new ByteArrayInputStream(request.getReporte().getFoto2());
                        BufferedImage bImageFromConvert_foto2 = ImageIO.read(in_foto2);
                        File f = new File(foto2PathLocal);
                        ImageIO.write(bImageFromConvert_foto2, "jpg", new File(foto2PathLocal)); 
                        f.setReadable(true,false);
                        in_foto2.reset();  
                        bImageFromConvert_foto2.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo foto2: "+ex.getMessage()); 
                    }
                    
                    //SUBIRMOS POR SFTP LA FOTO2 A NGINX. COMPRABMOS PRIMERO QUE LA FOTO1 EXISTE EN DIRECTORIO LOCAL
                    Path pathFoto2 = Paths.get(pathLocal+"foto2.jpg");                
                    if(Files.exists(pathFoto2)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FOTO2 LOCAL
                            File foto2 = new File(pathLocal+"foto2.jpg");
                            InputStream foto2Upload = new FileInputStream(foto2);   

                            String pathFoto2Remoto = pathRemoto+"/"+currentDirectorio+"/foto2.jpg";                         
                            channelSftp.put(foto2Upload,pathFoto2Remoto);
                            channelSftp.chmod(509,pathFoto2Remoto);
                            foto2RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto2.jpg";
                            channelSftp.disconnect();
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la foto2 a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La foto2 no se ha creado correctamente.");
                        foto2RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto2.jpg";
                    }                    
                }
                
                //FOTO3
                if(request.getReporte().getFoto3() != null){
                    //CREAMOS LA FOTO3 LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFOTO3                
                    try{
                        String foto3PathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/foto3.jpg";
                        InputStream in_foto3 = new ByteArrayInputStream(request.getReporte().getFoto3());
                        BufferedImage bImageFromConvert_foto3 = ImageIO.read(in_foto3);
                        File f = new File(foto3PathLocal);
                        ImageIO.write(bImageFromConvert_foto3, "jpg", new File(foto3PathLocal)); 
                        f.setReadable(true,false);
                        in_foto3.reset();  
                        bImageFromConvert_foto3.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo foto3: "+ex.getMessage()); 
                    }
                    
                    //SUBIRMOS POR SFTP LA FOTO3 A NGINX. COMPRABMOS PRIMERO QUE LA FOTO1 EXISTE EN DIRECTORIO LOCAL
                    Path pathFoto3 = Paths.get(pathLocal+"foto3.jpg");                
                    if(Files.exists(pathFoto3)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FOTO3 LOCAL
                            File foto3 = new File(pathLocal+"foto3.jpg");
                            InputStream foto3Upload = new FileInputStream(foto3);   

                            String pathFoto3Remoto = pathRemoto+"/"+currentDirectorio+"/foto3.jpg";                         
                            channelSftp.put(foto3Upload,pathFoto3Remoto);
                            channelSftp.chmod(509,pathFoto3Remoto);
                            foto3RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto3.jpg";
                            channelSftp.disconnect();
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la foto3 a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La foto3 no se ha creado correctamente.");
                        foto3RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto3.jpg";
                    }                    
                }
                
                //FOTO4
                if(request.getReporte().getFoto4() != null){
                    //CREAMOS LA FOTO4 LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFOTO4              
                    try{
                        String foto4PathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/foto4.jpg";
                        InputStream in_foto4 = new ByteArrayInputStream(request.getReporte().getFoto4());
                        BufferedImage bImageFromConvert_foto4 = ImageIO.read(in_foto4);
                        File f = new File(foto4PathLocal);
                        ImageIO.write(bImageFromConvert_foto4, "jpg", new File(foto4PathLocal)); 
                        f.setReadable(true,false);
                        in_foto4.reset();  
                        bImageFromConvert_foto4.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo foto4: "+ex.getMessage()); 
                    }
                    
                    //SUBIRMOS POR SFTP LA FOTO4 A NGINX. COMPRABMOS PRIMERO QUE LA FOTO1 EXISTE EN DIRECTORIO LOCAL
                    Path pathFoto4 = Paths.get(pathLocal+"foto4.jpg");                
                    if(Files.exists(pathFoto4)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FOTO4 LOCAL
                            File foto4 = new File(pathLocal+"foto4.jpg");
                            InputStream foto4Upload = new FileInputStream(foto4);   

                            String pathFoto4Remoto = pathRemoto+"/"+currentDirectorio+"/foto4.jpg";                         
                            channelSftp.put(foto4Upload,pathFoto4Remoto);
                            channelSftp.chmod(509,pathFoto4Remoto);
                            foto4RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto4.jpg";
                            channelSftp.disconnect();
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la foto4 a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La foto4 no se ha creado correctamente.");
                        foto4RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto4.jpg";
                    }                    
                }
                
                //FOTO5
                if(request.getReporte().getFoto5() != null){
                    //CREAMOS LA FOTO5 LOCAL UTILIZANDO EL ARRAY DE BYTES DEL REQUEST.GETFOTO5              
                    try{
                        String foto5PathLocal = "/home/media/diegoyjoseluis/R"+idReporte+"/foto5.jpg";
                        InputStream in_foto5 = new ByteArrayInputStream(request.getReporte().getFoto5());
                        BufferedImage bImageFromConvert_foto5 = ImageIO.read(in_foto5);
                        File f = new File(foto5PathLocal);
                        ImageIO.write(bImageFromConvert_foto5, "jpg", new File(foto5PathLocal)); 
                        f.setReadable(true,false);
                        in_foto5.reset();  
                        bImageFromConvert_foto5.flush();
                    }catch(IOException ex){
                       logger.debug("Exception subiendo foto5: "+ex.getMessage()); 
                    }
                    
                    //SUBIRMOS POR SFTP LA FOTO5 A NGINX. COMPRABMOS PRIMERO QUE LA FOTO1 EXISTE EN DIRECTORIO LOCAL
                    Path pathFoto5 = Paths.get(pathLocal+"foto5.jpg");                
                    if(Files.exists(pathFoto5)){
                        try{
                            if(!session.isConnected()){session.connect();}
                            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                            channelSftp.connect();                           
                            //ME UBICO EN EL DIRECTORIO DESTINO
                            channelSftp.cd(pathRemoto+"/"+currentDirectorio);                            

                            //OBTENGO FOTO5 LOCAL
                            File foto5 = new File(pathLocal+"foto5.jpg");
                            InputStream foto5Upload = new FileInputStream(foto5);   

                            String pathFoto5Remoto = pathRemoto+"/"+currentDirectorio+"/foto5.jpg";                         
                            channelSftp.put(foto5Upload,pathFoto5Remoto);
                            channelSftp.chmod(509,pathFoto5Remoto);
                            foto5RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto5.jpg";
                            channelSftp.disconnect();                            
                        }catch(Exception ex){
                            logger.debug("Exception: hay algun problema para subir la foto5 a directorio remoto: "+ex.getMessage());
                        }
                    }else{
                        logger.debug("La foto5 no se ha creado correctamente.");
                        foto5RelativePath = "/var/www/html/backoffice/diegoyjoseluis/main/reportes/R"+idReporte+"/foto5.jpg";
                    }                    
                }
            }    
           
            //UPDATE FIRMA Y FOTO PATH INTO REPORT TABLE USING IDREPORTE
            if(!signRelativePath.equals("") || !foto1RelativePath.equals("") || !foto2RelativePath.equals("") || !foto3RelativePath.equals("") || !foto4RelativePath.equals("") || !foto5RelativePath.equals("")){
                boolean isMultimediaUpdated = reporteDao.UpdateMultimediaContentPaths(idReporte,signRelativePath,foto1RelativePath,foto2RelativePath,foto3RelativePath,foto4RelativePath,foto5RelativePath);
            }            
            
            //CREATE PDF
            try{
                if(!session.isConnected()){session.connect();}       
                ChannelExec channelExec = (ChannelExec)session. openChannel("exec");                
                InputStream in = channelExec.getInputStream();                    
                String command = "php -f /var/www/html/backoffice/diegoyjoseluis/main/callReportPublisher.php idReporte="+idReporte;                
                channelExec.setCommand(command);
                channelExec.connect();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String linea;
                while((linea = reader.readLine()) != null){
                    builder.append(linea);                    
                }                
                logger.debug("DYJ creando pdf: "+builder.toString());
                channelExec.disconnect();
                session.disconnect();
            }catch(JSchException ex1){
                logger.debug("Exception subiendo PDF: "+ex1.getMessage());
            }catch(IOException ex2){
                logger.debug("Exception subiendo PDF: "+ex2.getMessage());
            }
            
            //SEND ALARM(EMAIL) IF NECESSARY
            if(alarmaFuga){
               try{                      
                    session.disconnect();                  
                    session = jsch.getSession("root","10.132.0.9");              
                    session.setConfig("PreferredAuthentications","publickey,gssapi-with-mic,keyboard-interactive,password");
                    jsch.addIdentity("/opt/bitnami/apache-tomcat/webapps/nginx");
                    java.util.Properties config = new java.util.Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.connect();              
                   
                    if(!session.isConnected()){session.connect();}       
                    ChannelExec channelExec = (ChannelExec)session. openChannel("exec");                
                    InputStream in = channelExec.getInputStream();          
                    
                    String command = "php -f /var/www/html/backoffice/diegoyjoseluis/main/callAlarmaConsumo.php idCliente="+idCliente+" consumoDiarioReal="+consumoDiarioReal+" numDias="+numDias+" consumoDiarioTeoricoMesAnterior="+consumoDiarioTeoricoMesAnterior+" consumoDiarioTeoricoMesActual="+consumoDiarioTeoricoMesActual;                
                    channelExec.setCommand(command);
                    channelExec.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String linea;
                    while((linea = reader.readLine()) != null){
                        builder.append(linea);                    
                    }                
                    logger.debug("DYJ enviando alarma por consumo de agua elevado: "+builder.toString());
                    channelExec.disconnect();
                    session.disconnect();
                }catch(JSchException ex1){
                    logger.debug("Exception alarma consumo de agua elevado: "+ex1.getMessage());
                }catch(IOException ex2){
                    logger.debug("Exception enviando alarma por consumo de agua elevado: "+ex2.getMessage());
                }             
            }
        }    
                     
        wsReportsResponse.setStatus("200");       
        wsReportsResponse.setResponse("ok");
        wsReportsResponse.setIdReporte(idReporte);        
        return wsReportsResponse;
    }
}
