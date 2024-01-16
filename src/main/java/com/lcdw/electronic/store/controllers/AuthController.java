package com.lcdw.electronic.store.controllers;

import com.lcdw.electronic.store.dtos.JwtRequest;
import com.lcdw.electronic.store.dtos.JwtResponse;
import com.lcdw.electronic.store.dtos.UserDto;
import com.lcdw.electronic.store.exceptions.BadApiRequestException;
import com.lcdw.electronic.store.security.JwtHelper;
import com.lcdw.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        String token=this.helper.generateToken(userDetails);

       UserDto userDto= mapper.map(userDetails,UserDto.class);
        JwtResponse response= JwtResponse.builder()
                .jwtToken(token)
                .user(userDto).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email,String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
        try{
            authenticationManager.authenticate(authentication);
        }
        catch(BadCredentialsException e){
            throw new BadApiRequestException("Invalid Username or password !!");

        }
    }


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrent(Principal principal){
        String name= principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name),UserDto.class), HttpStatus.OK);

    }


}
