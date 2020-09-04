package com.water.management.rest.order.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.water.management.rest.domain.Order;
import com.water.management.rest.domain.OrderStatus;
import com.water.management.rest.repository.OrderRepository;

@Configuration
public class OrderExecutor implements Runnable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderExecutor.class);

	@Autowired
	private OrderRepository repository;
	
	@Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;
	
	 @Bean("fixedThreadPool")
	 public ExecutorService fixedThreadPool() {
	        return Executors.newFixedThreadPool(5);
	 }
	
	
	private LinkedBlockingQueue<Order> linkedOrders;
	
	public OrderExecutor() {
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			linkedOrders = new LinkedBlockingQueue<Order>(repository.fetchOrdersToBeExecuted());
			/*for (Order order : linkedOrders) {
				Date currentDate = new Date(System.currentTimeMillis());
				if(!order.getStart().after(currentDate)) {
					linkedOrders.remove(order);
				}
			}*/
			LOGGER.info("===================================== Order Executor Poller, running every 600 seconds =============================");
			while (!linkedOrders.isEmpty()) {
				Order order = linkedOrders.poll();
				if (order != null) {
					executorService.submit(new OrderCallable(order));
				}

			}
			
			
		}
	}
	
	private class OrderCallable implements Callable {
		private Order order;
		public OrderCallable(Order order) {
			this.order = order;
		}
		@Override
		public Object call() throws Exception {
			LOGGER.info("Water order with id {} status is INPROGRESS for farm id {}.", order.getId(), order.getFarmerid());
			
			order.setStatus(OrderStatus.INPROGRESS);
			repository.updateScheduledOrder(order);
			
			Date currentDate = new Date(System.currentTimeMillis());
			while(currentDate.before(order.getEnd())){
				currentDate = new Date(System.currentTimeMillis());
			}
			order.setStatus(OrderStatus.DELIVERED);
			
			repository.updateScheduledOrder(order);
			LOGGER.info("Water order with id {} status is DELIVERED for farm id {}.", order.getId(), order.getFarmerid());
			return null;
		}
		
	}
	
}
