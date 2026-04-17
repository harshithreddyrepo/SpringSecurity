package com.telusko.SpringScProj1.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
	
	private static final String SECRET = "QmFzZTY0U2VjdXJlS2V5X0dlbmVyYXRlZF9SYW5kb21fMTIzNDU2Nzg5MA==";

	public String generateToken(String name)
	{
		
	    Map<String, Object> claims=new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(name)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +1000*60*5))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
				
		    
	}

	private Key getKey() {
		//byte[] b = Decoders.BASE64.decode(getTheSecretKey());
		byte[] b = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(b);
	}

	public String getTheSecretKey()
	{
		try
		{
			KeyGenerator key = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = key.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error in generating key");
		}
		
	}
	
	// 🔑 Extract username
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 🔑 Extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 🔑 Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // 🔑 Validate token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUserName(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 🔑 Check expiration
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 🔑 Parse token (validates signature automatically)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
