package com.water.management.rest.domain;

import java.io.Serializable;

public enum OrderStatus implements Serializable{
	REQEUSTED,
	INPROGRESS,
	DELIVERED,
	CANCELLED;
	
	 public String getStatus() {
	        return this.name();
	    }
}
