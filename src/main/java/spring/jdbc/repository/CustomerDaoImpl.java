package spring.jdbc.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import spring.jdbc.exception.CustomSQLErrorCodeTranslator;
import spring.jdbc.exception.DaoException;
import spring.jdbc.mapper.CustomerRowMapper;
import spring.jdbc.model.Customer;
@Repository
public class CustomerDaoImpl implements CustomerDao{
	// To use JDBC API with JdbcTemplate
	private JdbcTemplate jdbcTemplate;
	// To use JDBC API with NamedParameterJdbcTemplate
	private NamedParameterJdbcTemplate npjdbcTemplate;
	// To use JDBC API with SimpleJdbcInsert
	private SimpleJdbcInsert sjdbcInsert;
	
	@Autowired
	// DataSource to getting a connection && access data sources
	public void setDatasource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		/**
		 * Adding implementation of SQLErrorCodeTranslator to JDBCTemplate using 
		 * setExceptionTranslator() method
		 */
		final CustomSQLErrorCodeTranslator customSQLErrorCodesTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodesTranslator);
		
		npjdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		sjdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("customers");
	}

	public Customer getCustomerByLastname(String lastname) throws DaoException {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * FROM customers WHERE LASTNAME = ?", 
					new CustomerRowMapper(), 
					new Object[] {lastname});
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Customer getCustomerById(Integer id) throws DaoException {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * FROM customers WHERE ID = ?",
					new CustomerRowMapper(),
					new Object[] {id});
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Customer> getAllCustomers() throws DaoException {
		try {
			return jdbcTemplate.query("SELECT * FROM customers", new CustomerRowMapper());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void createCustomer(Customer customer) throws DaoException {
		try {
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("id", customer.getId());
			parameters.put("firstname", customer.getFirstname());
			parameters.put("lastname",customer.getLastname());
			parameters.put("company",customer.getCompany());
			parameters.put("mail",customer.getMail());
			parameters.put("phone",customer.getPhone());
			parameters.put("mobile",customer.getMobile());
			parameters.put("notes",customer.getNotes());
			parameters.put("active",customer.getActive());
			
			sjdbcInsert.execute(parameters);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	public void updateCustomer(Customer customer) throws DaoException {
		try {
			jdbcTemplate.update("UPDATE customers SET lastname = ?, firstname = ?, company = ?, mail= ?, phone = ?, mobile = ?, notes =?, active =? WHERE id = ?",
					customer.getLastname(),
					customer.getFirstname(),
					customer.getCompany(),
					customer.getMail(),
					customer.getPhone(),
					customer.getMobile(),
					customer.getNotes(),
					customer.getActive(),
					customer.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void deleteCustomer(Customer customer) throws DaoException {
		try {
			jdbcTemplate.execute("DELETE FROM customers WHERE id = "+ customer.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
