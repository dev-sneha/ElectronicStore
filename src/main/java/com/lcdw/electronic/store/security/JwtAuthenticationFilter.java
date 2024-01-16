package com.lcdw.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Autorization

        String requestHeader= request.getHeader("Authorization");
        //Bearer 4084092191ewfjhweiji
        logger.info("Header : {}", requestHeader);
        String username=null;
        String token=null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer")){
            // looking good
            token =requestHeader.substring(7);
            try{
                username= this.jwtHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e){
                logger.info("Illegal argument while fetching the username !!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e){
                logger.info("given jwt token is expired !!");
                e.printStackTrace();
            }
            catch (MalformedJwtException e){
                logger.info("Some changed has done in token  !! Invalid token");
                e.printStackTrace();
            }

        }else {
            logger.info(" Inavlid Header value !!");
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken= this.jwtHelper.validateToken(token,userDetails);
            if(validateToken){
                // set the authentication

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                logger.info("Validation fails");
            }

        }
        filterChain.doFilter(request,response);

    }
}
