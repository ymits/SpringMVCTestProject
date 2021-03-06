package my.cool.app;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalControllerExceptionHandler {
	
	@Autowired
    private MessageSource messageSource;

    // Basic example
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleException(HttpServletRequest request, HttpServletResponse response, HttpSession session, Exception ex, Locale locale) {
    	System.out.println(ex);
    	System.out.println(locale);
    	String message = messageSource.getMessage("app.test", null, locale);
    	System.out.println(message);
        return "{error:400}";
    }

}
