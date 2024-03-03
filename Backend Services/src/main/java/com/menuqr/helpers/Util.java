package com.menuqr.helpers;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import com.menuqr.JWTConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Util {
	
	//Encrypting user data
	public static String encodeJWT(int id, String subject) {
		String jwtToken = Jwts.builder().claim("id", id)
										.setSubject(subject)
										.setId(UUID.randomUUID().toString())
										.setIssuedAt(Date.valueOf(LocalDate.now()))
										.setExpiration(null)
										.setIssuer("WebSparks")
										.signWith(Keys.hmacShaKeyFor(JWTConfig.secretKey.getBytes()))
										.compact();
		return jwtToken;

	}
	
	
	public static String encodeJWTClient(int id,String subject) {
		String jwtToken = Jwts.builder().claim("id", id)
										.setSubject(subject)
										.setId(UUID.randomUUID().toString())
										.setIssuedAt(Date.valueOf(LocalDate.now()))
										.setExpiration(null)
										.setIssuer("WebSparks")
										.signWith(Keys.hmacShaKeyFor(JWTConfig.secretKey.getBytes()))
										.compact();
		return jwtToken;
	}
	
	//Decoding 
	public static JSONObject decodeJWT(String token) {
		Key key = Keys.hmacShaKeyFor(JWTConfig.secretKey.getBytes(StandardCharsets.UTF_8));
		Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		Claims claims = jws.getBody();
		JSONObject json = new JSONObject();
		json.put("id", claims.get("id", Integer.class));
		json.put("Issuer", claims.getIssuer());
		return json;
	}
	
	//ValidateRequiredInputparameters
	public static String ValidateRequiredInputparameters(Object[] requiredKeys,Object inputModel) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
		JSONObject obj=(JSONObject) JSONValue.parse(mapper.writeValueAsString(inputModel));
		for(Object fields:requiredKeys) {
			if(obj.get(fields)==null) {
				return fields+" is missing";
			}
		}
		return "true";
		
	}
	
	//Validating mobile number
	public static boolean isValidMobileNumber(String mobileNumber) {
		if(mobileNumber.length()!=10) {
			return false;
		}
		else if(mobileNumber.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	//Device Key generation
	public static String generateDeviceKey() throws Exception{
		String digits = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++)
			sb.append(digits.charAt(rnd.nextInt(digits.length())));
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(sb.toString().getBytes());
		byte[] digest = md.digest();  
		StringBuffer genaratedKey = new StringBuffer();
		for (int i = 0;i<digest.length;i++) {
			genaratedKey.append(Integer.toHexString(0xFF & digest[i]));
	      }
		return genaratedKey.toString();
	}
	
	
	//OTP generation
	public static int generateOtp(String mobileNumber){
		Random rnd = new Random();
	    int otp = rnd.nextInt(999999);
//	    RestTemplate restTemplate = new RestTemplate();
//        // Make the GET request and retrieve the response
//        String responseBody = restTemplate.getForObject("https://www.fast2sms.com/dev/bulkV2?authorization=1ZpBcs8YlavSQAb5RTwJE7gu9FMd2D6xoImGLikVHCPjX4UeKfS3WRzY0NLx4yQVbPAolZ2hnBvcuEUO&variables_values="
//        		+ "123456&route=otp&numbers="+mobileNumber, String.class);
	    return otp;
	}
	
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
	}

	
}
