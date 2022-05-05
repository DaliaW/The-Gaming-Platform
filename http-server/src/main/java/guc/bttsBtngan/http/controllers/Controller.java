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
		serviceToCommand.put("authentication", "messaging_req");
		serviceToCommand.put("notification", "messaging_req");
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
		Map<String, Object> auth_res = null;
//		if(!("login".equals(command) || "signup".equals(command))) {
//			//authentication
//			if(auth_res.get("error") != null) {
//				servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				return auth_res;
//			}
//		}
		Map<String, Object> res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
				serviceToCommand.get(service), body, m -> {
        	m.getMessageProperties().setHeader("command", command);
//        	if(auth_res != null)
//        		m.getMessageProperties().setHeader("user_id", (String)auth_res.get("data"));
    		m.getMessageProperties().setHeader("user_id", "user_1");
    		m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);

        	return m;
        });
		if(res.get("error") != null) {
			servletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		}
		return res;	
	}

}
