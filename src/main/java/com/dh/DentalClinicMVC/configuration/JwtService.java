package com.dh.DentalClinicMVC.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "32a2ed7d367b482ca44759c9b468b54fb5be59a7e17ef4a2b9b601869402dc5d7b8e73d869e13f4184916e7b7523f098583381344af941723e15917ca85e9522491ccff4a377d88662af45434eca9af020cae8e275a5adf17b402dc44850b8c08ba3f4123e58dfdb31ac43b45acc211c790a7ea1ddf7fb5f58577f50269d0dc17c5df95a712ea4fb17225e6cde2003c4abc4d020f2ebfc3b54fc7cc3e062e8573a57d4ee1053c8c840094a701c6e52e060da32e502d5d7f0557422813a05814b09936c329084623696279f974c37b8a3143fc3a08a8d35651d0e34caa61e0caef14527ded9eeff380def9793403383b1190a5868ffb1e4874b2bac9cb70e56a5";

    public String extractUsername(String token) {

      return extractClaim(token, Claims::getSubject);

    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extractor de claims genericos. Se usara para extraer el username
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {

    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
}

}