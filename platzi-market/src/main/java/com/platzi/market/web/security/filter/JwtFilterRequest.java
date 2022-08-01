package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Clase que verifica peticiones jwt para que se ejecute cada que existe una petición
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    //servicio que hace la autenticación.
    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //capturo el encabezado del request
        String authorizationHeader=request.getHeader("Authorization");
        //si viene header y comienza con la palabra Bearer -> jwt debe utilizar Bearer
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            //substring luego de la palabra Bearer + espacio
            String jwt=authorizationHeader.substring(7);
            String username=jwtUtil.extractUserName(jwt);

            //verificar en el contexto que aun no haya autenticación para este usuario
            if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=platziUserDetailsService.loadUserByUsername(username);
                //preguntar si el jwt es correcto
                if(jwtUtil.validateToken(jwt,userDetails)){
                    //se levanta sesión para el usuario
                    // userDetails.getAuthorities donde se mandan los roles pero como no hay no mandamos nada
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //que usuario, SO
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //se asigna la autenticación para no hacer este filtro ya que se valida arriba en SecurityContextHolder.getContext().getAuthentication()==null
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
