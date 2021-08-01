package com.jobreporting.responses;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSErrorResponse extends WSBaseResponse{	
    private static final long serialVersionUID = 1L;
    private String code;
    private String userMessage;
    private String errorMessage;
	
    //GETTERS
    public String getCode(){return code;} 
    public String getUserMessage(){return userMessage;}
    public String getErrorMessage(){return errorMessage;}
    
    //SETTERS
    public void setCode(String code){this.code = code;}
    public void setUserMessage(String userMessage){this.userMessage = userMessage;}
    public void setErrorMessage(String errorMessage){this.errorMessage = errorMessage;}
}
