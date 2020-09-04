package com.water.management.rest.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.water.management.rest.domain.Farmer;
import com.water.management.rest.domain.Order;
import com.water.management.rest.repository.FarmerRepository;
import com.water.management.rest.repository.OrderRepository;
import com.water.management.rest.resource.FarmerResource;
import com.water.management.rest.resource.FarmerResourceAssembler;
import com.water.management.rest.resource.OrderResource;
import com.water.management.rest.resource.OrderResourceAssembler;

@CrossOrigin(origins = "*")
@RestController
@ExposesResourceFor(Farmer.class)
@RequestMapping(value = "/farmers", produces = "application/json")
public class FarmerController {
	
	@Autowired
	private FarmerRepository repository;
	
	@Autowired
	private OrderRepository orderrepo;
	
	@Autowired
	private FarmerResourceAssembler assembler;
	
	@Autowired
	private OrderResourceAssembler orderassembler;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<FarmerResource>> findAllFarmers() {
		List<Farmer> Farmers = repository.findAll();
		return new ResponseEntity<>(assembler.toResourceCollection(Farmers), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<FarmerResource> createFarmer(@RequestBody Farmer Farmer) {
		Farmer createdFarmer = repository.create(Farmer);
		return new ResponseEntity<>(assembler.toResource(createdFarmer), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<FarmerResource> findFarmerById(@PathVariable Long id) {
		Optional<Farmer> Farmer = repository.findById(id);

		if (Farmer.isPresent()) {
			return new ResponseEntity<>(assembler.toResource(Farmer.get()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
		boolean wasDeleted = repository.delete(id);
		HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(responseStatus);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<FarmerResource> updateFarmer(@PathVariable Long id, @RequestBody Farmer updatedFarmer) {
		boolean wasUpdated = repository.update(id, updatedFarmer);
		
		if (wasUpdated) {
			return findFarmerById(id);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
	public ResponseEntity<Collection<OrderResource>> findOrdersByFarmerId(@PathVariable Long id) {
		Optional<Farmer> Farmer = repository.findById(id);

		if (Farmer.isPresent()) {
			List<Order> orders = orderrepo.findOrdersByFarmerId(id);
			return new ResponseEntity<>(orderassembler.toResourceCollection(orders), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
