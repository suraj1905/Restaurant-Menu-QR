package com.menuqr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTConfig {
	
	public static String secretKey;

    @Value("${jwt.secret}")
    public void setkey(String key) {
    	secretKey = key;
    }
	
}
