package util;

import java.sql.*;

public class DbConnection {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "Prachi@123");

		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
