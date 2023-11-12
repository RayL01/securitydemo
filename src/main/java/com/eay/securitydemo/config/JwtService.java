package com.eay.securitydemo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: shadyyyyyl
 * @Date: 2023/11/11/22:58
 * @Description:
 */
@Component
public class JwtService {
  private static final String SECRET_KEY = "df94032e1485352ecabb4202e7db8e84bd81497a43d80ecf3765f7c43578ed42";


  /**
   * extract username from the jwt token(subject is mapped to username in jwt so that's why calling getSubject)
   * @param token
   * @return
   */
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * extract claim from the jwt token
   * @param token
   * @param claimsTFunction
   * @return
   * @param <T>
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
    final Claims claims = extractAllClaims(token);
    return claimsTFunction.apply(claims);
  }
  private Claims extractAllClaims(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJwt(token)
            .getBody();
  }
  public String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);
  }
  public String generateToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails
  ){
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();

  }
  boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }


  /**
   * if the exp date from the jwt token is before today, then it's invalid
   * @param token
   * @return
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
