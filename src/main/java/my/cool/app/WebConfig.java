package my.cool.app;

import java.util.Locale;

import javax.servlet.ServletContext;

import my.cool.app.token.TransactionTokenInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@EnableScheduling
@Configuration
@ComponentScan("my.cool.app")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
 
	@Autowired
    private TransactionTokenInterceptor transactionTokenInterceptor;
	
    public WebConfig() {
        super();
    }

	@Bean
	public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(SessionRepository<S> sessionRepository, ServletContext servletContext) {
		SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
		sessionRepositoryFilter.setServletContext(servletContext);
		return sessionRepositoryFilter;
	}


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionTokenInterceptor);
    }
    
    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver=new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("ja"));
        localeResolver.setCookieName("i18next");
        return localeResolver;
    }
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/message");
        return messageSource;
    }
}
