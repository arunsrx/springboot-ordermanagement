package com.water.management.rest.repository;

import org.springframework.stereotype.Repository;

import com.water.management.rest.domain.Farmer;

@Repository
public class FarmerRepository extends InMemoryRepository<Farmer> {

	protected void updateIfExists(Farmer original, Farmer updated) {
		original.setFarmName(updated.getFarmName());
	}
}
