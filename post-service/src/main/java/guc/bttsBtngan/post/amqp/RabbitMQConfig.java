package guc.bttsBtngan.post.amqp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import guc.bttsBtngan.post.commands.SearchPostCommand;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;

import guc.bttsBtngan.post.commands.Command;


@Configuration
public class RabbitMQConfig {

    @Autowired
    private Map<String, Command> commands;
    @Autowired
    private AmqpTemplate amqpTemplate;
//    	@Autowired
//	private ExecutorService threadPool;
    private static final String request_queue = "post_req";
	public static final String reply_queue = "gateway";

    @Bean(name = {request_queue})
    public Queue request_queue() {
        return new Queue(request_queue);
    }

//	@Bean(name = {reply_queue})
//	public Queue reply_queue() {
//		return new Queue(reply_queue);
//	}

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

//	@Bean
//	public ExecutorService executor() {
//		return new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
//	}

//    @RabbitListener(queues = request_queue)
//    public void listen(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
//    	threadPool.submit(() -> {
//        	HashMap<String, Object> map = new HashMap<>();
//        	try {
//        		System.out.println("started processing task: " + payload.get("content"));
//        		payload.put("user_id", headers.get("user_id"));
//				payload.put("timestamp", headers.get("timestamp"));
//    			Object res = commands.get((String)headers.get("command")).execute(payload);
//    			map.put("data", res);
//    		} catch (Exception e) {
//    			map.put("error", e.getMessage());
//    		} finally {
//    			amqpTemplate.convertAndSend((String) headers.get("amqp_replyTo"), map, m -> {
//    	        	m.getMessageProperties().setCorrelationId((String) headers.get("amqp_correlationId"));
//    	        	m.getMessageProperties().setReplyTo((String) headers.get("amqp_replyTo"));
//    	        	return m;
//    			});
//        		System.out.println("finished processing task: " + payload.get("content"));
//    		}
//    	});
//    }

    @RabbitListener(queues = request_queue)
    public void listen(HashMap<String, Object> payload, @Headers Map<String, Object> headers) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            payload.put("user_id", headers.get("user_id"));
            payload.put("timestamp", headers.get("timestamp").toString());
            Object res = commands.get((String)headers.get("command")).execute(payload);
            map.put("data", res);
        } catch (Exception e) {
            map.put("error", e.getMessage());
        } finally {
            amqpTemplate.convertAndSend((String) headers.get("amqp_replyTo"), map, m -> {
                m.getMessageProperties().setCorrelationId((String) headers.get("amqp_correlationId"));
                m.getMessageProperties().setReplyTo((String) headers.get("amqp_replyTo"));
                return m;
            });
        }
    }

    // dummy method for testing
    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> {
        	for(char c='a' ; c<'z'; c++) {
            	Map<String, Object> map = new HashMap<>();
            	map.put("userId", "user_1");
            	map.put("content", "hi"+c);
                final Object response = amqpTemplate.convertSendAndReceive(
                        request_queue, map, m -> {
                            m.getMessageProperties().setHeader("command", "searchPostCommand");
                            m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);
                            return m;
                        });
                System.out.println(response+" "+c);
        	}
        };
    }




}
