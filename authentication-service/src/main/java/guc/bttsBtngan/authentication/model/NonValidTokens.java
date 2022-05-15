package guc.bttsBtngan.authentication.model;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;

@Service
public class NonValidTokens implements Serializable {

    HashSet<String> tokens = new HashSet<>();

    public void add(String token){
        tokens.add(token);
    }
    public boolean contains(String token){

        return tokens.contains(token);
    }
    public String toString(){
        return tokens.toString();
    }
    public int size(){
        return tokens.size();
    }
}
