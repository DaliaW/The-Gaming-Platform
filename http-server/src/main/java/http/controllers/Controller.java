package http.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import http.amqp.RabbitMQConfig;

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
        serviceToCommand.put("notification", "messaging_req");
        serviceToCommand.put("user", "user_req");
        serviceToCommand.put("post", "messaging_req");
        this.serviceToCommand = serviceToCommand;
    }

//    @PostMapping("/test")
//    public void test(){
//        System.out.println("hereeeeeeeeeeee");
//    }
    @SuppressWarnings("unchecked")
    @PostMapping("/")
    public Map<String, Object> handler(@RequestBody Map<String, Object> body,
                                       @RequestHeader Map<String, String> headers, HttpServletResponse servletResponse) {

        String[] route = headers.get("routing-key").split("\\.");
        String service = route[0], command = route[1];
        System.out.println("serviceeeeeeeeeee= "+service+" command= "+command);
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
            System.out.println("auth_res"+auth_res);
            if(auth_res.get("error") != null) {
                servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return auth_res;
            }
            System.out.println("ressssssss= ");
            res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
                    serviceToCommand.get(service), body, m -> {
                        m.getMessageProperties().setHeader("command", command);
//                        m.getMessageProperties().setHeader("user_id","1");

                        m.getMessageProperties().setHeader("user_id", auth_res.get("data").toString());
                        m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);

                        return m;
                    });
        }
        else {
            System.out.println("hereeeeeeeee else");
            res = (Map<String, Object>) amqpTemplate.convertSendAndReceive(
                    serviceToCommand.get(service), body, m -> {
                        m.getMessageProperties().setHeader("command", command);
                        m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);

                        return m;
                    });
            System.out.println("ressssssssss= "+res);
        }

        if(res.get("error") != null) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        return res;
    }

}