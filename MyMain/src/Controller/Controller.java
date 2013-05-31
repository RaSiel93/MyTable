package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import GUI.FrameMain;
import Model.Find;
import Model.HullTable;
import Model.UnionCell;

public interface Controller {
	//public Controller() {
	//}

	public void setFrame(FrameMain mainFrame);

	public int connect(String connect);
	
	public void close(int id) throws MyException;

	public void closeAll() throws MyException;
	
	public ActionListener getListner(String string);

	public List<List<String>> getData(int id, List<String> queryFind) throws MyException;

	public int getCountRows(int id) throws MyException;

	public void addInTable(int id, List<List<String>> value) throws MyException;

	public void loadFile(String path) throws MyException;

	//public abstract void resetQueryFind();

	//public abstract void setQueryFind(List<String> findQuery);
}
