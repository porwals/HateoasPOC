package com.poc.hateoas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "accounts")
public class Account extends ResourceSupport {

	@Id
	private String id;
	
	String orgId;
	String accId;
	String accType;
	String accNumber;
	String bankName;
	String routingNumber;
	
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
    @JsonCreator
    public Account(@JsonProperty("orgId") String orgId,
    					@JsonProperty("accId") String accId,
    					@JsonProperty("accType") String accType,
    					@JsonProperty("accNumber") String accNumber, 
    		            @JsonProperty("bankName") String bankName,
    		            @JsonProperty("routingNumber") String routingNumber) {
    	this.orgId = orgId;
    	this.accId = accId;
    	this.accType = accType;
        this.accNumber = accNumber;
        this.bankName = bankName;
        this.routingNumber = routingNumber;
    }

   
}
