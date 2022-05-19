package guc.bttsBtngan.post.commands;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class PostRecommendCommand extends PostCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        return getService().postRecommend((String)map.get("userId"));
    }
}
