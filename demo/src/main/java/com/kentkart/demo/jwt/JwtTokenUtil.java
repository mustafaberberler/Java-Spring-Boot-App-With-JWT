package com.kentkart.demo.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kentkart.demo.entities.concretes.Information;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;  // 24 hours
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	public JwtTokenUtil() {
		
	}
	
	public JwtTokenUtil(String secretKey) {
		super();
		this.secretKey = secretKey;
	}


	public String generateAccessToken(Information information) {
		return Jwts.builder()
				.setSubject(information.getId() + "," + information.getEmail())
				.setIssuer("Kentkart")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
				
	}
	
	
	public boolean validateAccessToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			return true;   // token is verified successfully
			
		} catch(ExpiredJwtException e) {
			LOGGER.error("JWT expired", e);
		} catch(IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or has only whitespace", ex);
		} catch(MalformedJwtException exc) {
			LOGGER.error("JWT is invalid", exc);
		} catch(UnsupportedJwtException exce) {
			LOGGER.error("JWT is not supported", exce);
		} catch(SignatureException excep) {
			LOGGER.error("Signutare validation failed", excep);
		}
		
		return false;
	}
	
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
}
