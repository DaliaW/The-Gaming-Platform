package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

@Component
public class ChangeAdminCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = getService().changeAdmin((String)map.get("user_id"), (String)map.get("admin_id"),
					(String)map.get("group_id"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
