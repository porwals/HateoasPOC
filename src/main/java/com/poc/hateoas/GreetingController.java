package com.poc.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/greeting")
@ExposesResourceFor(Greeting.class)
public class GreetingController {

	@Autowired EntityLinks entityLinks;
	
    private static final String TEMPLATE = "Hello, %s!";
    
    @ResponseBody
    @RequestMapping
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
       // greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());
        
		//Organization resource = new Organization("AmEx", "1");
		//greeting.add(linkTo(methodOn(GreetingController.class).getOrganization(1L)).withSelfRel());
        //greeting.add(entityLinks.linkToSingleResource(Organization.class, 1L));

        greeting.add(entityLinks.linkToCollectionResource(Organization.class).withRel("Organizations"));
        
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
/*    
    @RequestMapping(value = "organizations/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Organization> getOrganization(@PathVariable Long orgId) { return null;}*/
}
