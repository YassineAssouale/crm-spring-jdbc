package spring.jdbc.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator{
	@Override
	protected DataAccessException customTranslate(String task, String sql, SQLException sqlException) {
		if(sqlException.getErrorCode() == 23505) {
			/**
			 * Exception thrown when an attempt to insert or update data results in violation 
			 * of an primary key or unique constraint. Note that this is not necessarily a purely relational concept; 
			 * unique primary keys are required by most database types.
			 */
			return new DuplicateKeyException("Custom Exception translator - Integrity contraint violation.");
		}
		return null;
	}
}
