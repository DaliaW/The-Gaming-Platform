package guc.bttsBtngan.authentication.service;

import guc.bttsBtngan.authentication.dao.UserRespository;
import guc.bttsBtngan.authentication.model.DAOUser;
import guc.bttsBtngan.authentication.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userRespository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("There is no user with username: "+ username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList());
    }
    public DAOUser save(UserDTO user) throws IllegalAccessException,InstantiationException{
        DAOUser newUser = DAOUser.class.newInstance();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        return userRespository.save(newUser);
    }
}
