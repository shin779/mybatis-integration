package app.test.db;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.test.db.dao.Dao;
import app.test.db.model.TestObj;

public class TestDb {
	private Logger logger = Logger.getLogger(TestDb.class);
	private SqlSession session;
	private static final Integer TIMES = 10;
	private static final Integer LOOP_COUNT = 250;
	private ApplicationContext applicationContext = null;
	
	@Before
	public void init() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("**/applicationContext.xml");
		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		session = sqlSessionFactory.openSession();
	}
	
	@After
	public void cleanup() {
		
		try {
			if (applicationContext != null) {
				((ConfigurableApplicationContext)applicationContext).close();
			}

		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void process() throws Exception {
		logger.info("processSpring start");
		Long now, delta;
		
		String dummyQuery = "SELECT 1 FROM sysibm.sysdummy1";
		
		Dao testObjDao = applicationContext.getBean(Dao.class);
		
		for (int i =0;i<TIMES;i++) {
			
			Integer tryCount = (i+1)*LOOP_COUNT;

			logger.info("=================================");
			logger.info("Start query "+tryCount+" times using sql "+dummyQuery);
			logger.info("=================================");
			
			/**/
			now = System.currentTimeMillis();
			for (int j = 0; j < tryCount; j++) {
				session.selectList("TEST_OBJ.test");
			}
			delta = (System.currentTimeMillis()-now);
		    logger.info("[Spring DBCP] Executed "+tryCount+" queries in "+delta+"(ms)");
			/**/
		    
			/**/
			now = System.currentTimeMillis();
			for (int j = 0; j < tryCount; j++) {
				testObjDao.test();
			}
			delta = (System.currentTimeMillis()-now);
		    logger.info("[Spring DBCP] Executed "+tryCount+" queries in "+delta+"(ms)");
			/**/
		    
			logger.info("=================================");
			logger.info("");
		}

	}
}
