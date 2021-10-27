package spring.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.jdbc.model.Customer;
// Mapping rows of a java.sql.ResultSet on a per-row basis to a java object (Customer)
public class CustomerRowMapper implements RowMapper<Customer>{

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		final Customer customer = new Customer();
		
		customer.setId(rs.getInt("id"));
		customer.setFirstname(rs.getString("firstname"));
		customer.setLastname(rs.getString("lastname"));
		customer.setCompany(rs.getString("company"));
		customer.setMail(rs.getString("mail"));
		customer.setPhone(rs.getString("phone"));
		customer.setMobile(rs.getString("mobile"));
		customer.setNotes(rs.getString("notes"));
		customer.setActive(rs.getBoolean("active"));
		
		return customer;
	}
}
