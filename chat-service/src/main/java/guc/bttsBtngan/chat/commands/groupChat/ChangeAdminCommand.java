package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ChangeAdminCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.changeAdmin((String)map.get("user_id"), (String)map.get("admin_id"),
					(String)map.get("group_id"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
