package app.test.db.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.Properties;

public class DbcpDataSourceFactory implements DataSourceFactory {

	private Logger logger = Logger.getLogger(DbcpDataSourceFactory.class);

	private String username;
	private String password;
	private String driver;
	private String url;
	private Boolean poolPreparedStatements;

	@Override
	public void setProperties(Properties prop) {
		logger.debug("Using customize DataSourceFactory");

		Properties properties = new Properties();
		try {
			properties.load(DbcpDataSourceFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		url = (properties.getProperty("dataSource.url"));
		username = (properties.getProperty("dataSource.username"));
		password = (properties.getProperty("dataSource.password"));
		poolPreparedStatements = (Boolean.valueOf(properties.getProperty("dataSource.poolPreparedStatements")));
		driver = (properties.getProperty("dataSource.driverClassName"));
	}

	@Override
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driver);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		return dataSource;
	}

}