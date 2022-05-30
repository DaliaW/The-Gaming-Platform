package guc.bttsBtngan.authentication.service;

import guc.bttsBtngan.authentication.config.JwtTokenUtil;
import guc.bttsBtngan.authentication.dao.CachedTokenRepository;
import guc.bttsBtngan.authentication.dao.UserRepository;
import guc.bttsBtngan.authentication.model.CachedToken;
import guc.bttsBtngan.authentication.model.DAOUser;
import guc.bttsBtngan.authentication.model.NonValidTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    @Autowired
    private CachedTokenRepository cachedTokenRepository;

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
        String userId = userDetailsService.tokenToId(token);
        DAOUser user = userRepository.findByUsername(username);
        if(user.isBanned()){
            throw new Exception("User is banned! can't login.");
        }

        cacheAToken(token,userId);

        return token;
    }
    @CacheEvict(value="token", key = "#token")
    public String logout(String token) throws Exception{
        try{
            verify(token);
        } catch(Exception e){
            throw e;
        }
        cachedTokenRepository.deleteCachedToken(token);
        return "logged out successfully";
    }
    @CachePut(cacheNames = "token", key="#token")
    public String verify(String token) throws Exception{
//        Cache cache = cacheManager.getCache("token");
       CachedToken cachedToken = cachedTokenRepository.findCachedTokenByToken(token);
        if(cachedToken == null){
            throw new Exception("token is not in cache");
        }
        DAOUser user = userRepository.findById(cachedToken.getUserId());
        if(user.isBanned()){
            cachedTokenRepository.deleteCachedToken(token);
            throw new Exception("This user is banned , Action can't be performed");
        }
//        if(jwtTokenUtil.getExpirationDate(token).before( new Date())){
//           throw new Exception("Token Expired");
//        }
//        if(!tokenRepository.existsByToken(token)){
//            throw new Exception("Token is no longer acceptable");
//        }
        return cachedToken.getUserId();
    }

    public void cacheAToken(String token, String userId){
        CachedToken t = new CachedToken();
        t.setToken(token);
        t.setUserId(userId);
        cachedTokenRepository.save(t);
    }

}
