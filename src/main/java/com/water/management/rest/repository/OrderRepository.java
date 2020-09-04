package com.water.management.rest.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.water.management.rest.domain.Order;
import com.water.management.rest.domain.OrderStatus;

@Repository
public class OrderRepository extends InMemoryRepository<Order> {

	protected void updateIfExists(Order original, Order updated) {
		if(!original.getStatus().equals(OrderStatus.REQEUSTED) ) {
			throw new RuntimeException("Only orders in requested state/status can be modified/cancelled.");
		}
		original.setStart(updated.getStart());
		original.setHours(updated.getHours());
		original.setStatus(updated.getStatus());
	}
	
	public boolean findOverlappingOrder(Order neworder) {
		List<Order> orders = elements.stream().filter(e -> e.getFarmerid().equals(neworder.getFarmerid())).collect(Collectors.toList());
		for (Order order : orders) {
			if(neworder.getStart().before(order.getEnd())){
				return false;
			}
		}
		return true;
	}
	
	public List<Order> findOrdersByFarmerId(Long farmid) {
		return elements.stream().filter(e -> e.getFarmerid().equals(farmid)).collect(Collectors.toList());
	}
	
	public List<Order> fetchOrdersToBeExecuted() {
		Date currentDate = new Date(System.currentTimeMillis());
		return elements.stream().filter(e -> e.getStatus().equals(OrderStatus.REQEUSTED)).filter(x -> currentDate.after(x.getStart())).collect(Collectors.toList());
	}
	
	public boolean updateScheduledOrder(Order updated) {
		Optional<Order> element = findById(updated.getId());
		element.ifPresent(original -> updateScheduledOrder(original, updated));
		return element.isPresent();
	}

	private void updateScheduledOrder(Order original, Order updated) {
		original.setStart(updated.getStart());
		original.setHours(updated.getHours());
		original.setStatus(updated.getStatus());
	}
}
