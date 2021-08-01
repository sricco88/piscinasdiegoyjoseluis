package com.jobreporting.generic.utilities;

import com.jobreporting.generic.loggerManager.LoggerManager;
public class GenericUtility {
    public static LoggerManager getLogger(String className) {
	return new LoggerManager(className);
    }
    
    public static String safeTrim(String s){
        if((s == null) || s.equals("null")){
            return "";
	}else{
            return s.trim();
	}
    }
}
