package com.qiu.instantMessage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * �������ݿ���������
 */
public class DBManager {

	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "&v$}TTX2Y$";
	public static final String URL = "jdbc:mysql://instantMessage";
	public static DataSource dataSource = null;

	// ׼����������Դ C3P0
	static {
		// try {
		// ComboPooledDataSource pool = new ComboPooledDataSource();
		// pool.setDriverClass(DRIVER_NAME);
		// pool.setUser(USERNAME);
		// pool.setPassword(PASSWORD);
		// pool.setJdbcUrl(URL);
		// pool.setMaxPoolSize(30);
		// pool.setMinPoolSize(5);
		// dataSource = pool;
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.out.println("�������ӳؼ���ʧ��!");
		// }
	}

	// ���ͨ���˷��� ���Connection���Ӷ���
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER_NAME);

			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}
	}

}
