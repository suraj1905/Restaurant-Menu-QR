package com.menuqr.controllers;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.menuqr.helpers.ResponseHandler;
import com.menuqr.helpers.Util;
import com.menuqr.models.AdminModel;
import com.menuqr.models.ClientModel;
import com.menuqr.models.LoginInputModel;
import com.menuqr.repository.IAdminRepository;
import com.menuqr.repository.IClientRepository;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;

@RestController
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired 
	private IClientRepository clientRepository;
	
	@Autowired 
	private IAdminRepository adminRepository;

	
	@CrossOrigin
	@PostMapping(value="Client/Register")
	public ResponseEntity<Object> Add(@RequestBody ClientModel client){
		logger.info("Client Add Method "+LocalDateTime.now());
		try {
			ClientModel clientPresent = clientRepository.getUser(client.getEmail());
			if(clientPresent != null) {
				return ResponseHandler.generateResponse("User already Exists","User already Exists", HttpStatus.CONFLICT,false,"" );
			}
			if(Util.isValidMobileNumber(client.getMobileNumber())) {
			client.setIsactive(1);
			String encodePassword = Util.hashPassword(client.getPassword());
			client.setPassword(encodePassword);
			client=clientRepository.saveAndFlush(client);
			return ResponseHandler.generateResponse("Registered Successfully!","Registered Successfully!", HttpStatus.OK,true,"" );
			}
			
			return ResponseHandler.generateResponse("Invalid Mobile Number","Invalid Mobile Number", HttpStatus.BAD_REQUEST,false,"" );
		}
		catch(Exception ex) {
			logger.error("Client Add Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	

	@CrossOrigin
	@PostMapping(value="Client/Login")
	public ResponseEntity<Object> Login(@RequestBody LoginInputModel input,HttpServletResponse response){
		logger.info("Client Login Method "+LocalDateTime.now());
		ClientModel client = new ClientModel();
		try {
			if(input.getUsername() == null || input.getUsername().isEmpty() || input.getPassword() == null || input.getPassword().isEmpty()) {
				return ResponseHandler.generateResponse("Username or Password Not Present","Invalid Credential", HttpStatus.BAD_REQUEST,false,"" );
			}
			else {
				client = clientRepository.getUser(input.getUsername());
				if(client == null) {
					return ResponseHandler.generateResponse("Client Doesn't Exists","Client Doesn't Exists", HttpStatus.NOT_FOUND,false,"" );
				}
				else {
					String encodedPassword = Util.hashPassword(input.getPassword());
					if(client.getPassword().equalsIgnoreCase(encodedPassword)) {
						String encodeToken = Util.encodeJWT(client.getId(),"Client");
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
			logger.error("Client Login Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Client/GetAll")
	public ResponseEntity<Object> GetAll(@RequestHeader(value = "Authorization", required = false) String Authorization){
		logger.info("Client GetAll Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			AdminModel admin = adminRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(admin.getIsMasterAdmin() !=0) {
				clientRepository.getAll();
				return ResponseHandler.generateResponse("Clients fetched Successfully!","Clients fetched Successfully!", HttpStatus.OK,true,"" );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Client GetAll Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@PutMapping(value="Client/Update")
	public ResponseEntity<Object> Update(@RequestHeader(value = "Authorization", required = false) String Authorization,ClientModel updatedClient){
		logger.info("Client Update Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() !=0 && client.getMobileNumber().equalsIgnoreCase(updatedClient.getMobileNumber())) {
				client.setName(updatedClient.getName().isEmpty() || updatedClient.getName()==null ? client.getName() : updatedClient.getName());
				client.setEmail(updatedClient.getEmail().isEmpty() || updatedClient.getEmail()==null ? client.getEmail() : updatedClient.getEmail());
				clientRepository.save(client);
				return ResponseHandler.generateResponse("Updated Successfully!","Updated Successfully!", HttpStatus.OK,true,"" );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Client Update Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Client/Get")
	public ResponseEntity<Object> Get(@RequestHeader(value = "Authorization", required = false) String Authorization, String mobileNumber){
		logger.info("Client Get Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() !=0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				return ResponseHandler.generateResponse("Details fetched Successfully!","Details fetched Successfully!", HttpStatus.OK,true,client );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Client Get Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
}
