package spring.jdbc.repository;

import java.util.List;

import spring.jdbc.exception.DaoException;
import spring.jdbc.model.Customer;

public interface CustomerDao {
	/**
	 * Get a customer by lastname
	 * @param lastname
	 * @return the customer
	 * @throws DaoException
	 */
	Customer getCustomerByLastname(String lastname) throws DaoException;
	
	/**
	 * Get a customer by id
	 * @param id
	 * @return the customer
	 * @throws DaoException
	 */
	Customer getCustomerById(Integer id) throws DaoException;
	
	/**
	 * Get all customers
	 * @return a list of customers
	 * @throws DaoException
	 */
	List<Customer> getAllCustomers()throws DaoException;
	
	/**
	 * Create a customer
	 * @param customer : the customer to create
	 * @throws DaoException
	 */
	void createCustomer(Customer customer) throws DaoException;
	
	/**
	 * Update a customer
	 * @param customer : the customer to update
	 * @throws DaoException
	 */
	void updateCustomer(Customer customer) throws DaoException;
	
	/**
	 * Delete a customer
	 * @param customer : the customer to delete
	 * @throws DaoException
	 */
	void deleteCustomer(Customer customer) throws DaoException;
	
	
}
