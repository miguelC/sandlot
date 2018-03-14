package net.sandlotnow.interop.integration.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class InteropDataManager {

	private static final Logger LOG = LoggerFactory
			.getLogger(InteropDataManager.class);
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void create() throws Exception {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		String sql = "create table company (\n"
				+ "  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
				+ "  name varchar(30),\n" + "  symbol varchar(10)\n" + ")";

		LOG.info("Creating table company ...");

		try {
			jdbc.execute("drop table company");
		} catch (Throwable e) {
			// ignore
		}

		jdbc.execute(sql);

		LOG.info("... created table company");
	}

	public void destroy() throws Exception {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		try {
			jdbc.execute("drop table company");
		} catch (Throwable e) {
			// ignore
		}
	}
}
