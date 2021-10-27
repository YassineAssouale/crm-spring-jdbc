package spring.jdbc.repository;

import java.util.List;
import spring.jdbc.model.User;
import spring.jdbc.exception.DaoException;


public interface UserDao {
	List<User> getAll() throws DaoException;

	User getById(Integer id) throws DaoException;

	User getByUsername(String username) throws DaoException;

	void createUser(User user) throws DaoException;

	void udpateUser(User user) throws DaoException;

	void deleteUser(User user) throws DaoException;
}
