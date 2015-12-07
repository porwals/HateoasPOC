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
@RequestMapping(value = "organizations/{orgId}/accounts")
@ExposesResourceFor(Account.class)
public class AccountController {
	
	@Autowired EntityLinks entityLinks;

	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<Account>> accounts(@PathVariable String orgId) {

    	Query searchUserQuery = new Query(Criteria.where("orgId").is(orgId));
    	// find the saved accounts.
    	List<Account> listOfAcc = getMongoTemplate().find(searchUserQuery, Account.class);
    	if(null != listOfAcc && !listOfAcc.isEmpty())
    	{
	    	for(Account acc : listOfAcc)
	    	{
//	    		acc.add(entityLinks.linkToSingleResource(Account.class, acc.accId).withSelfRel());
	    		acc.add(linkTo(methodOn(AccountController.class).getAccount(orgId, acc.accId)).withSelfRel());
	    		acc.add(entityLinks.linkToSingleResource(Organization.class, orgId).withRel("organization"));
	    	}
    	}
        return new ResponseEntity<List<Account>>(listOfAcc, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{accId}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Account> getAccount(@PathVariable String orgId, @PathVariable String accId) { 
    	
    	Query searchUserQuery = new Query(Criteria.where("orgId").is(orgId).
    			andOperator(Criteria.where("accId").is(accId)));
    	// find the saved account.
    	List<Account> accounts = getMongoTemplate().find(searchUserQuery, Account.class);
    	Account account = null;
    	if(null != accounts && null != accounts.get(0))
    	{
    		account = accounts.get(0);
    		account.add(linkTo(methodOn(AccountController.class).getAccount(orgId, account.accId)).withSelfRel());
    		account.add(linkTo(methodOn(AccountController.class).accounts(orgId)).withRel("accounts"));
    	}
    	
    	return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Organization> saveAccount(@PathVariable String orgId, @RequestBody Account acc) {
		
    	MongoOperations mongoTmp = getMongoTemplate();
    	acc.setOrgId(orgId);
    	mongoTmp.insert(acc, "accounts");
    	
    	acc.add(linkTo(methodOn(AccountController.class).getAccount(orgId, acc.accId)).withSelfRel());
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setLocation(linkTo(methodOn(AccountController.class).getAccount(orgId, acc.accId)).toUri());
    	
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
