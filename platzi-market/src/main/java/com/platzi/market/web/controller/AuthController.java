package com.platzi.market.web.controller;

import com.platzi.market.domain.dto.AuthenticationRequest;
import com.platzi.market.domain.dto.AuthenticationResponse;
import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //clase de spring AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;
    @Autowired
    private JWTUtil jwtUtil;


    //recibe peticiones a traves de post por lo cual se debe incluir un @RequestBody como parámetro
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        //se hace dentro de try catch para validar que se haga correctamente
        try {
            //la autenticación se va a hacer con usuario y contraseña
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            //detalles del usuario desde el servicio platziUserDetailsServiceque creamos para generar la seguridad por usuario y contraseña
            UserDetails userDetails = platziUserDetailsService.loadUserByUsername(request.getUsername());
            //se genera el JWT inyectando la clase jwtUtil enviando los user details
            String jwt = jwtUtil.generateToken(userDetails);
            //ocurre cuando se hace bien la autenticación
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }catch (BadCredentialsException e){
            //ocurre la excepción cuando la autenticación no se haga con el usuario y contraseña quemados
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
