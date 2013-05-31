package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Controller.MyException;

public class MySQLFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost/feedback?"
			+ "user=sqluser&password=sqluserpw";

	public static Connection createConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(DRIVER);
		return DriverManager.getConnection(DBURL);
	}

	public MyData getCustomerDAO(String pathToFile, int id) throws MyException {
		return new MySQLData(pathToFile, id);
	}
}
