package my.cool.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(method = RequestMethod.GET)
    public List<Test> index(
    		@RequestParam(value = "keyStr", required = false) String keyStr,
            @RequestParam(value = "keyInt", required = false) Integer keyInt,
            @RequestParam(value = "keyBoolean", required = false) Boolean keyBoolean,
            @RequestParam(value = "keyDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date keyDate,
            @RequestParam(value = "keyEnum", required = false) TestEnum keyEnum) {
        System.out.println("keyStr:"+keyStr);
        System.out.println("keyInt:"+keyInt);
        System.out.println("keyBoolean:"+keyBoolean);
        System.out.println("keyDate:"+keyDate);
        System.out.println("TestEnum:"+keyEnum);
        
        List<Test> results = new ArrayList<Test>();
        results.add(new Test());
        results.add(new Test());
		return results;
    }
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Test show(@PathVariable String id) {
        return new Test();
    }
	
	@RequestMapping(method=RequestMethod.PUT)
	public Test create(@RequestBody Test test) {
		System.out.println("test:"+test);
        return test;
    }
	
	@RequestMapping(value="/error/error", method = RequestMethod.GET)
    public Test showError() {
		throw new RuntimeException();
    }

}
