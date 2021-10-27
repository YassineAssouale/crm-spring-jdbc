package spring.jdbc.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import spring.jdbc.exception.CustomSQLErrorCodeTranslator;
import spring.jdbc.exception.DaoException;
import spring.jdbc.mapper.OrderRowMapper;
import spring.jdbc.model.Order;

@Repository
public class OrderDaoImpl implements OrderDao{
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert sjdbcInsert;
	
	@Autowired
	void setDataSource (DataSource dataSource) {
	
		jdbcTemplate = new JdbcTemplate(dataSource);
		sjdbcInsert= new SimpleJdbcInsert(dataSource).withTableName("orders") ;
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
	}
	
	@Override
	public Order getById(Integer id) throws DaoException {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * FROM orders WHERE id = ?", 
					new OrderRowMapper(), 
					new Object[] {id});
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public List<Order> getAllOrders() throws DaoException {
		try {
			return jdbcTemplate.query(
					"SELECT * FROM orders", 
					new OrderRowMapper());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Order> getOrdersByCustomer(Integer id) throws DaoException {
		try {
			return jdbcTemplate.query(
					"SELECT * FROM orders WHERE CUSTOMER_ID = ?", 
					new OrderRowMapper(), 
					new Object[] {id});
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Order> getOrdersByTypeAndStatus(String type, String status) throws DaoException {
		try {
			return jdbcTemplate.query(
					"SELECT * FROM orders WHERE TYPE = ? AND STATUS = ?", 
					new OrderRowMapper(), 
					new Object[] {type, status});
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Order> getOrdersForNumberOfDays(Double days) throws DaoException {
		try {
			return jdbcTemplate.query(
					"SELECT * FROM orders WHERE NUMBER_OF_DAYS = ? ", 
					new OrderRowMapper(), 
					new Object[] {days});
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void createOrder(Order order) throws DaoException {
		try {
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("ID", order.getId());
			parameters.put("CUSTOMER_ID", order.getCustomer().getId());
			parameters.put("LABEL", order.getLabel());
			parameters.put("ADR_ET", order.getAdrEt());
			parameters.put("NUMBER_OF_DAYS", order.getNumberOfDays());
			parameters.put("TVA", order.getTva());
			parameters.put("STATUS", order.getStatus());
			parameters.put("TYPE", order.getType());
			parameters.put("NOTES", order.getNotes());
			
			sjdbcInsert.execute(parameters);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public void updateOrder(Order order) throws DaoException {
		try {
			jdbcTemplate.update(
					"UPDATE orders SET CUSTOMER_ID = ?, LABEL = ?, ADR_ET = ?, NUMBER_OF_DAYS = ?, TVA = ?, STATUS = ?, TYPE = ?, NOTES = ? WHERE ID = ?",
					order.getCustomer().getId(),
					order.getLabel(),
					order.getAdrEt(), 
					order.getNumberOfDays(), 
					order.getTva(), 
					order.getStatus(), 
					order.getType(), 
					order.getNotes(), 
					order.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public void updateOrderStatus(String newStatus, String oldStatus, String type) throws DaoException {
		try {
			jdbcTemplate.update(
					"UPDATE orders SET STATUS = ? WHERE STATUS = ? AND TYPE = ?",
					newStatus, oldStatus, type);
					
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void deleteOrder(Order order) throws DaoException {
		try {
			jdbcTemplate.update("DELETE FROM ORDERS WHERE ID = ?", order.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
}
