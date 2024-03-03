package com.menuqr.controllers;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.menuqr.helpers.ResponseHandler;
import com.menuqr.helpers.Util;
import com.menuqr.models.AdminModel;
import com.menuqr.repository.IAdminRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;

@RestController
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired 
	private IAdminRepository adminRepository;
	
	@CrossOrigin
	@PostMapping(value="Admin/Login")
	public ResponseEntity<Object> Login(String username, String password,HttpServletResponse response){
		logger.info("AdminModel Login Method "+LocalDateTime.now());
		AdminModel admin = new AdminModel();
		try {
			if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
				return ResponseHandler.generateResponse("Username or Password Not Present","Invalid Credential", HttpStatus.BAD_REQUEST,false,"" );
			}
			
			else {
				admin = adminRepository.getUsername(username);
				if(admin == null) {
					return ResponseHandler.generateResponse("Admin Doesn't Exists","AdminModel Doesn't Exists", HttpStatus.NOT_FOUND,false,"" );
				}
				else {
					String encodedPassword = Util.hashPassword(password);
					if(admin.getPassword().equalsIgnoreCase(encodedPassword)) {
						String encodeToken = Util.encodeJWT(admin.getId(),"Admin");
						 Cookie cookie = new Cookie("authToken", encodeToken);
						 cookie.setHttpOnly(true);
						 response.addCookie(cookie);
						return ResponseHandler.generateResponse("Logged in Successfully","Logged in Successfully", HttpStatus.OK,true,"");

					}
					return ResponseHandler.generateResponse("Incorrect Password","Incorrect Password", HttpStatus.FORBIDDEN,false,"" );
				}
			}
				
		}
		catch(Exception ex) {
			logger.error("AdminModel Login Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	
	@CrossOrigin
	@PostMapping(value="Admin/test")
	public ResponseEntity<Object> test(String Authorization){
		logger.info("AdminModel Login Method "+LocalDateTime.now());
		try {
			JSONObject decode = Util.decodeJWT(Authorization);
			return ResponseHandler.generateResponse("","Something went wrong", HttpStatus.OK,true,decode.toString() );
				
		}
		catch(Exception ex) {
			logger.error("AdminModel Login Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
}
