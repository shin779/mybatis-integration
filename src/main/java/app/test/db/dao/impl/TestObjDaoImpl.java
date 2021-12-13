package app.test.db.dao.impl;

import app.test.db.dao.Dao;
import app.test.db.model.TestObj;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

@Component("testObjDao")
public class TestObjDaoImpl extends SqlSessionDaoSupport implements Dao {

	private final SqlSessionFactory sqlSessionFactory;

	public TestObjDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<TestObj> test() throws SQLException {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectList("TEST_OBJ.test");
		}
	}
}
