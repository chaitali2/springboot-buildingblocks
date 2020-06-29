package com.stacksimplify.restservices.springbootbuildingblocks.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;


@RestController
public class HelloWorldController {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
//	@RequestMapping(method = RequestMethod.GET, path = "/helloworld")
	@GetMapping("/helloworld1")
	public String helloWorld() {
		return "Hello World1";
	}
	
	@GetMapping("/helloworld-bean")
	public UserDeatils helloWorldBean() {
		return new UserDeatils("Chaitali","Khachane","Silvassa");
	}
	
	@GetMapping("/hello-int")
	public String getMessagesInI18Format(@RequestHeader(name="Accept-Language",required = false) String locale) {
//		return "Hello World I18N";
		return messageSource.getMessage("label.hello", null, new Locale(locale));
	}
	
	@GetMapping("/hello-int2")
	public String getMessagesInI18NFormat2() {
		return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
		
	}
}
