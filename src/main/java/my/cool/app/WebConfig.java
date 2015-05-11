package my.cool.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("my.cool.app")
@EnableWebMvc
@ImportResource({ "classpath:webSecurityConfig.xml" })
public class WebConfig extends WebMvcConfigurerAdapter {
 
    public WebConfig() {
        super();
    }
 
}
