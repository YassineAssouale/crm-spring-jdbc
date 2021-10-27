package spring.jdbc;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

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
import spring.jdbc.exception.DaoException;
import spring.jdbc.model.Customer;
import spring.jdbc.repository.CustomerDaoImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {AppConfig.class}, loader = AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerDaoImplTest {

	@Autowired
	private CustomerDaoImpl customerDao;
	
	@Test
	@Order(1)
	void whenCallingGetAllCustomersMethod_thenReturnListOfCustomers() {
		List<Customer> customers = customerDao.getAllCustomers();
		Assertions.assertEquals(4,customers.size(), "Wrong number of customers");
	}
	
	@Test
	@Order(2)
	void whenCallingGivenCustomerLastname_thenReturningCustomerWithLastname() {
		Customer customer = customerDao.getCustomerByLastname("GILBERT");
		Assertions.assertEquals("GILBERT", customer.getLastname(),"Customer not found - Lastname.");
	}
	@Test
	@Order(3)
	void whenCallingGetCustomerById_thenReturningCustomerById() {
		Customer customer = customerDao.getCustomerById(1);
		Assertions.assertEquals(1, customer.getId(),"Customer not found - ID.");
	}
	@Test
	@Order(4)
	void testAddCustomerUsingSimpleJdbcInsert() {
		Customer customerToAdd = new Customer();
		customerToAdd.setId(5);
		customerToAdd.setFirstname("test");
		customerToAdd.setLastname("test2");
		customerToAdd.setCompany("test");
		customerToAdd.setMail("test@test.com");
		customerToAdd.setNotes("mes notes");
		customerToAdd.setPhone("0645768345");
		customerToAdd.setMobile("09876754");
		customerToAdd.setActive(true);
		
		customerDao.createCustomer(customerToAdd);
		Assertions.assertNotNull(customerDao.getCustomerByLastname("test2"),"Creation KO!");
	
	}
	@Test
	@Order(5)
	void testUpdateCustomer() {
		Customer customer = customerDao.getCustomerById(3);
		customer.setFirstname("Test update");
		customerDao.updateCustomer(customer);
		
		Assertions.assertEquals("Test update",customerDao.getCustomerById(3).getFirstname());
	}
	
	@Test
	@Order(6)
	void testDeleteCustomer() {
		customerDao.deleteCustomer(customerDao.getCustomerById(5));
		
		Assertions.assertThrows(DaoException.class,()->{customerDao.getCustomerById(5);});
	}
}
