package com.jobreporting.requests;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSOneTimeAuthRequest extends WSBaseRequest{
    private static final long serialVersionUID = 1L;
    private String usuario;
    private String password;   

    //GETTERS
    public String getUsuario(){ return usuario;}  
    public String getPassword(){ return password;}
    
    //SETTERS
    public void setUsuario(String usuario){ this.usuario = usuario;}    
    public void setPassword(String password){this.password = password;}
}
