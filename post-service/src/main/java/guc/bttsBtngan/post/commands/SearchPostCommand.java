package guc.bttsBtngan.post.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SearchPostCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().searchPosts((String)map.get("content"),(String)map.get("user_id"));
    }
}
