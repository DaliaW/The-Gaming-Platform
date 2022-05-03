package guc.bttsBtngan.chat.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public String joinGroup(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.joinGroup((String)body.get("user_id"), (String)body.get("group_id"));
	}
	
	@PostMapping("/groups/message")
	public String sendGroupMessage(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.sendGroupMessage((String) body.get("user_id"), (String) body.get("group_id"), (String) body.get("content"));
	}
	
	@PostMapping("/groups/name")
	public String changeGroupName(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.changeGroupName((String)body.get("user_id"), (String)body.get("name"), (String)body.get("group_id"));
	}
	
	@PostMapping("/groups/leave")
	public String leaveGroup(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.leaveGroup((String)body.get("user_id"), (String)body.get("group_id"));
	}
	
	@DeleteMapping("/groups")
	public String deleteGroup(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.deleteGroup((String) body.get("group_id"), (String) body.get("user_id"));
	}
	
	@DeleteMapping("/groups/message")
	public String deleteMessage(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.deleteMessage((String) body.get("group_id"), (String) body.get("user_id"), (String) body.get("message_id"));
	}
	
	@PutMapping("/groups/message")
	public String updateMessage(@RequestBody HashMap<String, Object> body) throws InterruptedException, ExecutionException {
		return service.updateMessage((String) body.get("group_id"), (String) body.get("user_id"),
					(String) body.get("message_id"), (String) body.get("content"));
	}
	
	@GetMapping("/groups/{id}")
	public Map<String, Object> getGroup(@PathVariable String id) throws InterruptedException, ExecutionException {
		return service.getGroup(id);
	}
}
