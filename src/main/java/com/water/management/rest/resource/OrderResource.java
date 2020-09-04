package com.water.management.rest.resource;

import java.text.SimpleDateFormat;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.water.management.rest.domain.Order;
import com.water.management.rest.domain.OrderStatus;

public class OrderResource extends ResourceSupport {

	private final long id;
	private final long farmerid;
	private final OrderStatus status;
	private final String start;
	private final String end;
	private final int hours;
	SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
	
	public OrderResource(Order order) {
		id = order.getId();
		farmerid = order.getFarmerid();
		status = order.getStatus();
		start = formatter.format(order.getStart());
		end = formatter.format(order.getEnd());
		hours = order.getHours();
	}

	@JsonProperty("id")
	public Long getResourceId() {
		return id;
	}
	
	public long getFarmerid() {
		return farmerid;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public long getHours() {
		return hours;
	}
}
