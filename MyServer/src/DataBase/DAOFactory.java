package DataBase;

import Controller.MyException;

public abstract class DAOFactory {
	public static final int MYSQL = 1;

	public abstract MyData getCustomerDAO(String pathToFile, int id)
			throws MyException;

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case MYSQL:
			return new MySQLFactory();
		default:
			return null;
		}
	}
}
