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

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.data.GroupChat;
import guc.bttsBtngan.chat.services.GroupChatService;

@RestController
public class GroupChatController {
	
	@Autowired
	private Map<String, Command> commands;
	
	@PostMapping("/groups")
	public String createGroup(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("createGroupCommand").execute(body);
	}
	
	@PostMapping("/groups/admin")
	public String changeAdmin(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("changeAdminCommand").execute(body);
	}
	
	@PostMapping("/groups/join")
	public String joinGroup(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("joinGroupCommand").execute(body);
	}
	
	@PostMapping("/groups/message")
	public String sendGroupMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("sendGroupMessageCommand").execute(body);

	}
	
	@PostMapping("/groups/name")
	public String changeGroupName(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("changeGroupNameCommand").execute(body);
	}
	
	@PostMapping("/groups/leave")
	public String leaveGroup(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("leaveGroupCommand").execute(body);
	}
	
	@DeleteMapping("/groups")
	public String deleteGroup(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("deleteGroupCommand").execute(body);
	}
	
	@DeleteMapping("/groups/message")
	public String deleteMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("deleteMessageCommand").execute(body);
	}
	
	@PutMapping("/groups/message")
	public String updateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("updateMessageCommand").execute(body);
	}
	
	@GetMapping("/groups/{id}")
	public Map<String, Object> getGroup(@PathVariable String id) throws Exception {
		HashMap<String, Object> body = new HashMap<>();
		body.put("id", id);
		return (Map<String, Object>) commands.get("getGroupCommand").execute(body);
	}
}
