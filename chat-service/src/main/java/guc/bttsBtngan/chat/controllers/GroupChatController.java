package guc.bttsBtngan.chat.controllers;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guc.bttsBtngan.chat.data.GroupChat;
import guc.bttsBtngan.chat.services.GroupChatService;

@RestController
public class GroupChatController {
	
	@Autowired
	private GroupChatService service;
	
	@PostMapping("/groups")
	public String createGroup(@RequestBody GroupChat chat) throws InterruptedException, ExecutionException {
		return service.createGroup(chat);
	}
	
	@PostMapping("/groups/admin")
	public String changeAdmin(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.changeAdmin((String)body.get("user_id"), (String)body.get("admin_id"), (String)body.get("group_id"));
	}
	
	@PostMapping("/groups/join")
	public String joinGroup(@RequestBody HashMap<String, Object> body) {
		return service.joinGroup((String)body.get("user_id"), (String)body.get("group_id"));
	}
	
	@PostMapping("/groups/message")
	public String sendGroupMessage(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.sendGroupMessage((String) body.get("user_id"), (String) body.get("group_id"), (String) body.get("content"));
	}
}
