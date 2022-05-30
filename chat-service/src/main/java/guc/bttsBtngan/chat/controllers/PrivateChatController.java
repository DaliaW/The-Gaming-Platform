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
import org.springframework.web.bind.annotation.RestController;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.data.PrivateChat;
import guc.bttsBtngan.chat.services.PrivateChatService;

@RestController
public class PrivateChatController {
	
	@Autowired
	private Map<String, Command> commands;
	
	@PostMapping("/private")
	public String createPrivateChat(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("createPrivateChatCommand").execute(body);
	}
	
	@PostMapping("/private/message")
	public String sendPrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("sendPrivateMessageCommand").execute(body);
	}

	@DeleteMapping("/private")
	public String deletePrivateChat(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("deletePrivateChatCommand").execute(body);
	}
	
	@DeleteMapping("/private/message")
	public String deletePrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("deletePrivateMessageCommand").execute(body);
	}
	
	@PutMapping("/private/message")
	public String updatePrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return (String) commands.get("updatePrivateMessageCommand").execute(body);
	}
	
	@GetMapping("/private/{id}")
	public Map<String, Object> getPrivateChat(@PathVariable String id, @RequestBody HashMap<String, Object> body) throws Exception {
		body.put("private_id", id);
		return (Map<String, Object>) commands.get("getPrivateChatCommand").execute(body);
	}
}
