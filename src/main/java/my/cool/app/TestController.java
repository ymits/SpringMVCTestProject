package my.cool.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
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
	
	@Secured("ROLE_ADMIN")
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
