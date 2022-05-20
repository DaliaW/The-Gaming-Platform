package guc.bttsBtngan.http.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import guc.bttsBtngan.http.amqp.RabbitMQConfig;

@RestController
public class Controller {
	
	private AmqpTemplate amqpTemplate;
	private Map<String, String> serviceToCommand;
	
	@Autowired
	public Controller(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
		Map<String, String> serviceToCommand = new HashMap<>();
		serviceToCommand.put("chat", "messaging_req");
		serviceToCommand.put("authentication", "authentication_req");
		serviceToCommand.put("notification", "notification_req");
		serviceToCommand.put("user", "messaging_req");
		serviceToCommand.put("post", "messaging_req");
		this.serviceToCommand = serviceToCommand;
	}


	@SuppressWarnings("unchecked")
	@PostMapping("/")
	public Map<String, Object> handler(@RequestBody Map<String, Object> body,
			@RequestHeader Map<String, String> headers, HttpServletResponse servletResponse) {

		String[] route = headers.get("routing-key").split("\\.");
		String service = route[0], command = route[1];
		Map<String, Object> res = null;
		//TODO: get signup command name from girls team
		if(!("loginCommand".equals(command) || "signup".equals(command))) {
			//authentication
			Map<String, Object> auth_body = new HashMap<>();
			auth_body.put("token", headers.get("token-x"));
			final Map<String, Object> auth_res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
					serviceToCommand.get("authentication"), auth_body, m -> {
	        	m.getMessageProperties().setHeader("command", "verifyCommand");
	    		m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);
	        	return m;
	        });
			if(auth_res.get("error") != null) {
				servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return auth_res;
			}
			res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
					serviceToCommand.get(service), body, m -> {
	        	m.getMessageProperties().setHeader("command", command);
	        	m.getMessageProperties().setHeader("user_id", auth_res.get("data").toString());
	    		m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);

	        	return m;
	        });
		}
		else {
			res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
					serviceToCommand.get(service), body, m -> {
	        	m.getMessageProperties().setHeader("command", command);
	    		m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);

	        	return m;
	        });
		}

		if(res.get("error") != null) {
			servletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		}
		return res;	
	}

}
