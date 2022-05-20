package guc.bttsBtngan.authentication.dao;

import guc.bttsBtngan.authentication.model.CachedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CachedTokenRepository {
    public static final String HASH_KEY = "Token";

    @Autowired
    private RedisTemplate template;

    public CachedToken save(CachedToken cachedToken){
        template.opsForHash().put(HASH_KEY, cachedToken.getToken(), cachedToken);
        return cachedToken;
    }
    public List<CachedToken> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }
    public CachedToken findCachedTokenByToken (String token){
        System.out.println("Called the db");
        return ((CachedToken) template.opsForHash().get(HASH_KEY,token));

    }
    public String deleteCachedToken(String token){
        template.opsForHash().delete(HASH_KEY,token);
        return "Token deleted successfully";
    }

}
