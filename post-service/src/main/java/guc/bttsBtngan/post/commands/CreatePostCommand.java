package guc.bttsBtngan.post.commands;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import org.springframework.stereotype.Component;

import guc.bttsBtngan.post.commands.Command;

@Component
public class CreatePostCommand extends PostCommand {

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        Post post = new Post();

        post.setUserId((String)map.get("user_id"));
//        post.setAdmin_id((String) map.get("admin_id"));TODO
//        post.setName((String) map.get("name"));
        return getService().createPost(post);
    }

}
