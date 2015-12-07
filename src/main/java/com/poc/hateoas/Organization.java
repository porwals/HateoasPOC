package com.poc.hateoas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "organizations")
public class Organization extends ResourceSupport {

	@Id
	private String id;
	
	String orgName;
	String orgId;
	
	/*@DBRef
	List<Account> accounts;

    public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}*/

	@JsonCreator
    public Organization(@JsonProperty("orgName") String orgName, 
    		            @JsonProperty("orgId") String orgId) {
        this.orgName = orgName;
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }
    
    public String getOrgId() {
        return orgId;
    }
}
