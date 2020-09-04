package com.water.management.rest.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Order implements Identifiable {

	private Long id;
	
	private Long farmerid;
	private OrderStatus status;
	private Date start;
	private Date end;
	private int hours;
	
	
	public Long getFarmerid() {
		return farmerid;
	}

	public void setFarmerid(Long farmerid) {
		this.farmerid = farmerid;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(String start) throws ParseException {
	   SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
       this.start = formatter.parse(start);
	}
	
	public void setStart(Date start) {
		   this.start = start;
		}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public int getHours() {
		return this.hours;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
		Date enddate = DateUtils.addSeconds(this.start, this.hours);
	    this.setEnd(enddate);
	}
}
