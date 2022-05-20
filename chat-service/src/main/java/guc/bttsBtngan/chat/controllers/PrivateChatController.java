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

import guc.bttsBtngan.chat.data.PrivateChat;
import guc.bttsBtngan.chat.services.PrivateChatService;

@RestController
public class PrivateChatController {
	
	@Autowired
	private PrivateChatService service;
	
	@PostMapping("/private")
	public String createPrivateChat(@RequestBody PrivateChat chat) throws Exception {
		return service.createPrivateChat(chat);
	}
	
	@PostMapping("/private/message")
	public String sendPrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return service.sendPrivateMessage((String) body.get("user_id"), (String) body.get("private_id"),
				(String) body.get("content"), (String) body.get("timestamp"));
	}

	@DeleteMapping("/private")
	public String deletePrivateChat(@RequestBody HashMap<String, Object> body) throws Exception {
		return service.deletePrivateChat((String) body.get("user_id"), (String) body.get("private_id"));
	}
	
	@DeleteMapping("/private/message")
	public String deletePrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return service.deletePrivateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("message_id"));
	}
	
	@PutMapping("/private/message")
	public String updatePrivateMessage(@RequestBody HashMap<String, Object> body) throws Exception {
		return service.updateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("message_id"), (String) body.get("content"));
	}
	
	@GetMapping("/private/{id}")
	public Map<String, Object> getPrivateChat(@PathVariable String id, @RequestBody HashMap<String, Object> body) throws Exception {
		return service.getPrivateChat((String) body.get("user_id"), id);
	}
}
