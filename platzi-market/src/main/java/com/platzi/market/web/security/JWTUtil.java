package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTUtil {

    //token que se venza en 10 horas
    private static final String KEY="pl4tz1";
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();
    }

    //función para validar que el token es correcto
    //creado para el usuario y no haya vencido
    public boolean validateToken(String token,UserDetails userDetails){
        return userDetails.getUsername().equals(extractUserName(token))&&!isTokenExpired(token);
    }

    //validar que sea el usuario
    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }

    //validar el token no haya vencido
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    //retornar los Claims (objetos dentro del token)
    // contenido del token
    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(KEY)
                .parseClaimsJws(token).getBody();    }
}

