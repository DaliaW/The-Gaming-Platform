package guc.bttsBtngan.authentication.service;

import guc.bttsBtngan.authentication.config.JwtTokenUtil;
import guc.bttsBtngan.authentication.dao.UserRepository;
import guc.bttsBtngan.authentication.model.DAOUser;
import guc.bttsBtngan.authentication.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("There is no user with username: "+ username);
        }
        ArrayList<? extends GrantedAuthority> record = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                record);
    }
    @CachePut(cacheNames = "token", key="#token")
    public HashMap<String, Object> tokenToUser(String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser user = userRepository.findByUsername(username);
        HashMap<String, Object> record = new HashMap<>();
        record.put("username",user.getUsername());
        record.put("email",user.getEmail());
        record.put("isBanned",user.isBanned());
        record.put("isModerator",user.isModerator());
        record.put("photo_ref",user.getPhoto_ref());
        record.put("id",user.getId());
        return record;

    }
    public DAOUser save(UserDTO user) throws IllegalAccessException,InstantiationException{
        DAOUser newUser = DAOUser.class.newInstance();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setBanned(user.isBanned());
        newUser.setModerator(user.isModerator());
        newUser.setPhoto_ref(user.getPhoto_ref());
        return userRepository.save(newUser);
    }
}
