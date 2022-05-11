package guc.bttsBtngan.authentication.service;

import guc.bttsBtngan.authentication.config.JwtTokenUtil;
import guc.bttsBtngan.authentication.dao.UserRepository;
import guc.bttsBtngan.authentication.model.DAOUser;
import guc.bttsBtngan.authentication.model.NonValidTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NonValidTokens nonValidTokens;

    @Autowired
    private CacheManager cacheManager;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("Wrong credentials");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    public String getToken(String userName, String password) throws Exception{
        authenticate(userName, password);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userName);

        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;

    }
    public String login(String username, String password) throws Exception {
        final String token = getToken(username,password);
        userDetailsService.tokenToUser(token);
        return token;
    }
    @CacheEvict(value="token", key = "#token")
    public String logout(String token) throws Exception{
        System.out.println("I am here 1 and non valid tokens size is "+ nonValidTokens.size());
        int curSize = nonValidTokens.size();
        nonValidTokens.add(token);
        if(nonValidTokens.size() == curSize){
            System.out.println("I am here 2");
            throw new Exception("Already logged out");
        }
        else
        nonValidTokens.add(token);
        System.out.println("I am here 3 and non valid tokens size is "+ nonValidTokens.size() );

        return "logged out successfully";
    }
    public long verify(String token) throws Exception{
        Cache cache = cacheManager.getCache("token");
        System.out.println("c"+cache);
        if(jwtTokenUtil.getExpirationDate(token).before( new Date())){
           throw new Exception("Token Expired");
        }
        if(nonValidTokens.contains(token)){
            throw new Exception("Token is no longer acceptable");
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println("name"+username);
        DAOUser user = userRepository.findByUsername(username);
        System.out.println("username= "+user.getId());
        return user.getId();
    }
}
