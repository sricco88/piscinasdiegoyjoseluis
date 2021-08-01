package com.jobreporting.responses;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSBaseResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    private String status;
    private String response;
  
    //GETTERS
    public String getStatus(){return this.status;}  
    public String getResponse(){return this.response;}
  
    //SETTERS
    public void setStatus(String status){this.status = status;}
    public void setResponse(String response){this.response = response;}
}
