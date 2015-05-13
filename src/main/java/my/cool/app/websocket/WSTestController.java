package my.cool.app.websocket;

import my.cool.app.Test;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WSTestController {

	@MessageMapping("/test")
    @SendTo("/topic/testfeed")
    public Test publish(Test test) throws Exception {
		System.out.println(test);
        Thread.sleep(3000); // simulated delay
        return test;
    }
}
