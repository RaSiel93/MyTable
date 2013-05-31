package DataBase;

import java.util.List;

import Controller.MyException;
import Model.Find;
import Model.HullTable;

public interface MyData {
	void readXML() throws MyException;

	public List<List<String>> find(List<String> finds) throws MyException;

	public void writeIntoXML(String path) throws MyException;

	public void insert(List<List<String>> value) throws MyException;

	public void delete(List<String> idArray) throws MyException;

	public void clear() throws MyException;

	public HullTable getHull();

	public List<Find> getFindArray();

	public int getCountRows() throws MyException;

	public boolean isId(int id);

	public int getId();
}
