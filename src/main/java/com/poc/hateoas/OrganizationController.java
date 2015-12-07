package com.poc.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.EntityLinks;
//import org.springframework.core.annotation.Order;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ExposesResourceFor(Organization.class)
@RequestMapping("/organizations")
public class OrganizationController {
	
	@Autowired EntityLinks entityLinks;

    @RequestMapping
    @ResponseBody
    public HttpEntity<List<Organization>> organizations() {

    	List<Organization> orgResources = getMongoTemplate().findAll(Organization.class);
    	for(Organization org : orgResources)
    	{
//    		org.add(linkTo(methodOn(OrganizationController.class).organization(org.orgId)).withSelfRel());
    		org.add(entityLinks.linkToSingleResource(Organization.class, org.orgId));
    		org.add(entityLinks.linkFor(Greeting.class).withRel("Home"));
    		org.add(linkTo(methodOn(AccountController.class).accounts(org.orgId)).withRel("Accounts"));
    	}
        
    	return new ResponseEntity<List<Organization>>(orgResources, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Organization> organization(@PathVariable String orgId) { 
    	
    	Query searchUserQuery = new Query(Criteria.where("orgId").is(orgId));
    	// find the saved organization.
    	List<Organization> listOfOrg = getMongoTemplate().find(searchUserQuery, Organization.class);
    	Organization org = null;
    	if(null != listOfOrg && null != listOfOrg.get(0))
    	{
    		org = listOfOrg.get(0);
    		org.add(linkTo(methodOn(OrganizationController.class).organization(orgId)).withSelfRel());
//    		org.add(this.entityLinks.linkFor(Greeting.class).withRel("Home"));
    		org.add(entityLinks.linkToCollectionResource(Organization.class).withRel("Organizations"));
    	}
    	
//    	List<Organization> orgResources = new  ArrayList<Organization>();
//        Organization orgResource = new Organization("Test1", "A1");
//        orgResource.add(linkTo(methodOn(OrganizationController.class).organization(1L)).withSelfRel());
//        orgResources.add(orgResource);
//        Organization orgResource2 = new Organization("Test2", "A2");
//        orgResource2.add(linkTo(methodOn(OrganizationController.class).organization(2L)).withSelfRel());
//        orgResources.add(orgResource2);
    		return new ResponseEntity<Organization>(org, HttpStatus.OK);
    	}
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Organization> saveOrganization(@RequestBody Organization org) {
		
    	MongoOperations mongoTmp = getMongoTemplate();
    	mongoTmp.insert(org, "organizations");
    	
    	org.add(linkTo(methodOn(OrganizationController.class).organization(org.orgId)).withSelfRel());
    	org.add(this.entityLinks.linkFor(Greeting.class).withRel("Home"));
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(linkTo(OrganizationController.class).slash(org.orgId).toUri());
    	
    	return new ResponseEntity<Organization>(headers, HttpStatus.CREATED);
    }
    
    private MongoOperations getMongoTemplate()
    {
    	ApplicationContext ctx = 
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    	MongoOperations mongoOperation = 
                (MongoOperations) ctx.getBean("mongoTemplate");
    	return mongoOperation;
    }
}
