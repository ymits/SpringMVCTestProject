package my.cool.app.websocket;

import java.security.Principal;

import my.cool.app.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

@Controller
public class WSTestController {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/no-secure-test")
	@SendTo("/topic/result")
    public Test publish1(Test test) throws Exception {
		System.out.println(test);
        return test;
    }
	
	@Secured("ROLE_USER")
	@MessageMapping("/secure-test")
	@SendTo("/topic/result")
    public Test publish2(Test test) throws Exception {
		System.out.println(test);
        return test;
    }
	
//	@Secured("ROLE_USER")
	@MessageMapping("/user-test")
//    @SendToUser("/queue/user-test")
    public void executeTrade(Test test) {
		System.out.println(test);
//        return test;
		messagingTemplate.convertAndSendToUser("admin", "/queue/user-test", test);
    }
}
