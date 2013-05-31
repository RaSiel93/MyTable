package Controller;

import java.io.IOException;
import java.sql.SQLException;

public class MyException extends Exception{
	public MyException(Exception e) {
		e.printStackTrace();
	}	
	public MyException(IOException e) {
		e.printStackTrace();
	}
	public MyException(ClassNotFoundException e) {
		e.printStackTrace();
	}
}
