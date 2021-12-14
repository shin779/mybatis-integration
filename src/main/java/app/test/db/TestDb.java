package app.test.db;

import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.test.db.dao.Dao;

public class TestDb {
	private Logger logger = Logger.getLogger(TestDb.class);
	private SqlSession session;
	private static final Integer TIMES = 10;
	private static final Integer LOOP_COUNT = 250;
	private ApplicationContext applicationContext = null;

	@Before
	public void init() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		session = (new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis.xml"))).openSession();
	}

	@After
	public void cleanup() {

		try {
			if (applicationContext != null) {
				((ConfigurableApplicationContext) applicationContext).close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void process() throws Exception {
		logger.info("processSpring start");
		Long now, delta;

		String dummyQuery = "SELECT 1 FROM sysibm.sysdummy1";
		
			logger.info(applicationContext.getBeanDefinitionCount());

		Dao testObjDao = applicationContext.getBean(Dao.class);

		for (int i = 0; i < TIMES; i++) {

			Integer tryCount = (i + 1) * LOOP_COUNT;

			logger.info("=================================");
			logger.info("Start query " + tryCount + " times using sql " + dummyQuery);
			logger.info("=================================");

			/**/
			now = new Date().getTime();
			for (int j = 0; j < tryCount; j++) {
				session.selectList("TEST_OBJ.test");
			}
			delta = (new Date().getTime() - now);
			logger.info("[MyBatis] Executed " + tryCount + " queries in " + delta + "(ms)");
			/**/

			/**/
			now = new Date().getTime();
			for (int j = 0; j < tryCount; j++) {
				testObjDao.test();
			}
			delta = (new Date().getTime() - now);
			logger.info("[Spring + MyBatis] Executed " + tryCount + " queries in " + delta + "(ms)");
			/**/

			logger.info("=================================");
			logger.info("");
		}

	}
}
