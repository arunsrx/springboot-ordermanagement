package com.water.management.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.water.management.rest.domain.Farmer;
import com.water.management.rest.domain.Order;

@Component
public class FarmerResourceAssembler extends ResourceAssembler<Farmer, FarmerResource> {
	
	@Autowired
	protected EntityLinks entityLinks;

	private static final String UPDATE_REL = "update";
	private static final String DELETE_REL = "delete";

	@Override
	public FarmerResource toResource(Farmer farmer) {
		
		FarmerResource resource = new FarmerResource(farmer);
		
		final Link selfLink = entityLinks.linkToSingleResource(farmer);
		
		resource.add(selfLink.withSelfRel());
		resource.add(selfLink.withRel(UPDATE_REL));
		resource.add(selfLink.withRel(DELETE_REL));
		
		return resource;
	}
}
