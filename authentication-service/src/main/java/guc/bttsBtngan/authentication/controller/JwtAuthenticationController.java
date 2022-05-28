package guc.bttsBtngan.authentication.controller;

import guc.bttsBtngan.authentication.config.JwtTokenUtil;
import guc.bttsBtngan.authentication.model.JwtRequest;
import guc.bttsBtngan.authentication.model.JwtResponse;
import guc.bttsBtngan.authentication.model.UserDTO;
import guc.bttsBtngan.authentication.service.AuthService;
import guc.bttsBtngan.authentication.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    AuthService authService;

    @Autowired
    JwtUserDetailsService userDetailsService;

    private String username;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {

        final String token = authService.login(authenticationRequest.getUsername(),authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/log-out", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorization) throws Exception {
        authorization = authorization.substring(7);
        System.out.println("The authorization token is "+ authorization);
        return ResponseEntity.ok(authService.logout(authorization));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }
   // @RequestMapping(value="/hello", method = RequestMethod.GET)


    @RequestMapping(value="/verify", method = RequestMethod.POST)
    public String verify(@RequestHeader ("Authorization") String token) throws Exception{
        //String token = header.get("Authorization").substring(7);
        token = token.substring(7);
        System.out.println("The token is "+token);
       return authService.verify(token);
    }

    public String hello(){
       // System.out.println(cacheManager.getCache("token").get("Mazenelgamed").get());

        return "hello";
    }



}
