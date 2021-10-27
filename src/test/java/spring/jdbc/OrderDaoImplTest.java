package spring.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import spring.jdbc.config.AppConfig;
import spring.jdbc.repository.CustomerDao;
import spring.jdbc.repository.OrderDao;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
public class OrderDaoImplTest {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Test
	@Order(1)
	void testGetById() {
		Assertions.assertEquals("Formation Java", orderDao.getById(1).getLabel());
	}
	
	@Test
	@Order(2)
	void whenCallingGetAllOrdersMethod_thenReturningListOfOrders() {
		Assertions.assertEquals(3,orderDao.getAllOrders().size());
	}
	
	@Test
	@Order(3)
	void testGetOrderByCustomer() {
		Assertions.assertEquals(2, orderDao.getOrdersByCustomer(1).size());
	}
	
	@Test
	@Order(4)
	void testGetOrderByTypeAndStatus() {
		Assertions.assertEquals(1, orderDao.getOrdersByTypeAndStatus("Forfait", "En cours").size());
	}
	
	@Test
	@Order(5)
	void testCreateOrder() {
		spring.jdbc.model.Order order = new spring.jdbc.model.Order(
			null, 
			"Ordre1",
			5000.0,
			10.0,
			20.0,
			"En cours",
			"Forfait",
			"notes", 
			customerDao.getCustomerById(2));
		int av = orderDao.getAllOrders().size();
		orderDao.createOrder(order);
		Assertions.assertEquals(av + 1, orderDao.getAllOrders().size());
	}
	
	@Test
	@Order(6)
	void testUpdateOrder() {
		spring.jdbc.model.Order order = orderDao.getById(2);
		order.setLabel("Order2");
		orderDao.updateOrder(order);
		Assertions.assertEquals("Order2", orderDao.getById(2).getLabel());
	}
	
	@Test
	@Order(7)
	void testGetOrderForNumberOfDays() {
		Assertions.assertEquals(1, orderDao.getOrdersForNumberOfDays(3.0).size());
	}
	@Test
	@Order(8)
	void testUpdateOrderStatus() {
		orderDao.updateOrderStatus("En cours", "En attente", "Forfait");
		Assertions.assertEquals("En cours", orderDao.getById(2).getStatus());
	}
	
	@Test
	@Order(9)
	void testDeleteOrder() {
		int av = orderDao.getAllOrders().size();
		orderDao.deleteOrder(orderDao.getById(4));
		Assertions.assertEquals(av - 1, orderDao.getAllOrders().size());
	}
}
