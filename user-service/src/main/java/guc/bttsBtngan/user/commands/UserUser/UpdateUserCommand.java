package guc.bttsBtngan.user.commands.UserUser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateUserCommand extends UserUserCommand{

    @Override
    public Object execute(HashMap<String, Object> map) throws Exception {
        System.out.println(" IN UPDAte COMAND"+(String)(map.get("user_id")));
        String user_id=(String)(map.get("user_id"));
        String new_username=map.containsKey("username")?(String)(map.get("username")):null;
        System.out.println("username= "+new_username);
        String email=map.containsKey("email")?(String)(map.get("email")):null;
        System.out.println("email= "+email);
        String oldPassword=map.containsKey("oldPassword")?(String)(map.get("oldPassword")):null;
        System.out.println("oldPassword= "+oldPassword);
        String newPassword=map.containsKey("newPassword")?(String)(map.get("newPassword")):null;
        System.out.println("newPassword= "+newPassword);
        MultipartFile photo=map.containsKey("photo")?(MultipartFile)(map.get("photo")):null;
        System.out.println("photo= "+photo);
        return getService().updateUser(user_id,new_username,email,oldPassword,newPassword,photo);

    }
}
