package com.water.management.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.water.management.rest.domain.Farmer;

public class FarmerResource extends ResourceSupport {

	private final long id;
	private final String farmName;
	
	
	public FarmerResource(Farmer farmer) {
		id = farmer.getId();
		farmName = farmer.getFarmName();
		
	}

	@JsonProperty("id")
	public Long getResourceId() {
		return id;
	}
	
	public String getFarmName() {
		return farmName;
	}

}
