package com.menuqr.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseHandler {
	//Standard Response format
	public static ResponseEntity<Object> generateResponse(String message, String userFriendlyMessage, HttpStatus status,boolean success, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        	map.put("data", responseObj);	
        	map.put("message", message);
        	map.put("userFriendlyMessage", userFriendlyMessage);
        	map.put("success", success);
        	map.put("status", status.value());
        	         
            return new ResponseEntity<Object>(map,status);
    }
}

