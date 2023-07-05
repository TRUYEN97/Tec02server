package com.tec02.Service.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.tec02.model.dto.impl.impl.impl.UserDto;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.util.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServeice {
	 private static final String SECRET_KEY = "4d4c26fe23dfe2fc5720e016463b2dd4886d883937414b8e62d1cad0d8f37b22";
	 

	  public String extractUsername(String token) {
	    return extractClaim(token, Claims::getSubject);
	  }

	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
	  }

	  public String generateToken(Map<String, Object> extraClaims, UserDto userDetails) {
	    return Jwts
	      .builder()
	      .setClaims(extraClaims)
	      .setSubject(userDetails.getName())
	      .setIssuedAt(new Date(System.currentTimeMillis()))
	      .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 3600 * 1)) // 1 h
	      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	      .compact();
	  }

	  public boolean isTokenValid(String token, User userDetails) {
		String jwtSaved = userDetails.getUserJwt().getJwt();
		if(jwtSaved == null || token == null || !Util.md5File(token.getBytes()).equals(jwtSaved)) {
			return false;
		}
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }

	  private boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	  }

	  private Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	  }

	  private Claims extractAllClaims(String token) {
	    return Jwts
	      .parserBuilder()
	      .setSigningKey(getSignInKey())
	      .build()
	      .parseClaimsJws(token)
	      .getBody();
	  }

	  private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	    return Keys.hmacShaKeyFor(keyBytes);
	  }
}
