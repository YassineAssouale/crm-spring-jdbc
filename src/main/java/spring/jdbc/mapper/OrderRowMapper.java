package spring.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.jdbc.model.Customer;
import spring.jdbc.model.Order;

public class OrderRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		Customer customer = new Customer();
		order.setId(rs.getInt("id"));
		order.setLabel(rs.getString("LABEL"));
		order.setAdrEt(rs.getDouble("ADR_ET"));
		order.setTva(rs.getDouble("TVA"));
		order.setNumberOfDays(rs.getDouble("NUMBER_OF_DAYS"));
		order.setStatus(rs.getString("STATUS"));
		order.setType(rs.getString("TYPE"));
		order.setNotes(rs.getString("NOTES"));
		order.setCustomer(customer);
		return order;
	}

}
