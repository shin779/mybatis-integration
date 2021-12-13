package app.test.db.dao;

import java.sql.SQLException;
import java.util.List;

import app.test.db.model.TestObj;

public interface Dao {

	public List<TestObj> test() throws SQLException;
	
}
