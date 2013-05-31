package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import DataBase.DAOFactory;
import DataBase.MyData;
import GUI.FrameMain;
import GUI.PanelTable;
import Query.QueryFind;
import Query.QueryLoad;
import Result.ResultFile;
import Result.ResultLoad;

public class ControllerServer implements Controller {
	private static FrameMain mainFrame;
	private HashMap<String, ActionListener> listeners = initListeners();

	private static DAOFactory cloudscapeFactory = DAOFactory
			.getDAOFactory(DAOFactory.MYSQL);
	private List<MyData> dataBaseList;

	public ControllerServer() {
		this.dataBaseList = new ArrayList<MyData>();
	}

	public void setFrame(FrameMain mainFrame) {
		ControllerServer.mainFrame = mainFrame;
	}
	
	public int connect(String connect){
		return 0;
	}

	public List<List<String>> getData(int id, List<String> queryFind)
			throws MyException {
		return getDataStruct(id).find(queryFind);
	}

	public int getCountRows(int id) throws MyException {
		return getDataStruct(id).getCountRows();
	}

	public void loadFile() throws MyException {
		JFileChooser fileopen = new JFileChooser();
		fileopen.setCurrentDirectory(new File(".\\save"));
		if (fileopen.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			createMainTabel(creatDataTable(fileopen.getSelectedFile().getPath())
					.getId());
		}
	}

	private MyData creatDataTable(String path) throws MyException {
		MyData struct = ControllerServer.cloudscapeFactory
				.getCustomerDAO(path, generateId());
		this.dataBaseList.add(struct);
		return struct;
	}

	private int generateId() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(100000);
	}

	private void createMainTabel(int id) throws MyException {
		MyData struct = getDataStruct(id);
		mainFrame.creatTable(struct.getId(), struct.getHull(),
				struct.getFindArray());
	}

	private void saveFile(int id, boolean flag) throws MyException {
		MyData struct = getDataStruct(id);
		if (struct != null) {
			if (flag) {
				JFileChooser fileopen = new JFileChooser();
				fileopen.setCurrentDirectory(new File(".\\save"));
				if (fileopen.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					struct.writeIntoXML(
							fileopen.getSelectedFile().getAbsolutePath());
				}
			} else {
				struct.writeIntoXML(new String(""));
			}
		}
	}

	private MyData getDataStruct(int id) {
		for (MyData struct : this.dataBaseList) {
			if (struct.isId(id)) {
				return struct;
			}
		}
		return null;
	}
	

	private int getIndexStruct(int id) {
		int index = 0;
		for (MyData struct : dataBaseList) {
			if (struct.getId() == id) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void closeAll() throws MyException {
		while (FrameMain.getId() != -1) {
			close(FrameMain.getId());
		}
	}

	public void close(int id) throws MyException {
		int index = getIndexStruct(id);
		if (index != -1) {
			getDataStruct(id).clear();
			dataBaseList.remove(index);
			mainFrame.removeTable(id);
		}
	}

	public void addInTable(int id, List<List<String>> value) throws MyException {
		MyData struct = getDataStruct(id);
		if (struct != null) {
			struct.insert(value);
		}
	}

	private void updateFrame() throws MyException {
		mainFrame.updateFrame();
	}

	protected void deleteTable(int id, List<String> listSelected) throws MyException{
		getDataStruct(id).delete(listSelected);
	}
	
	private class AddData implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainFrame.dialogAdd();
		}
	}

	private class DeleteData implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<String> listSelected = mainFrame.getSelectedRows();
			if (listSelected.size() > 0) {
				try {
					deleteTable(FrameMain.getId(), listSelected);
					mainFrame.dialogRemove(listSelected.size());
					updateFrame();
				} catch (MyException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class LoadData implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				loadFile();
				updateFrame();
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class SaveFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				saveFile(mainFrame.getId(), false);
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class SaveFileAs implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				saveFile(mainFrame.getId(), true);
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class CloseFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				close(mainFrame.getId());
				mainFrame.removeTable(mainFrame.getId());
				//mainFrame.updateFrame();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private class About implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				mainFrame.dialogAbout();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ExitFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				closeAll();
				System.exit(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private HashMap<String, ActionListener> initListeners() {
		HashMap<String, ActionListener> listeners = new HashMap<String, ActionListener>();
		listeners.put("ADD", new AddData());
		listeners.put("DELETE", new DeleteData());
		listeners.put("LOAD", new LoadData());
		listeners.put("SAVE", new SaveFile());
		listeners.put("SAVE_AS", new SaveFileAs());
		listeners.put("ABOUT", new About());
		listeners.put("CLOSE", new CloseFile());
		listeners.put("EXIT", new ExitFile());
		return listeners;
	}

	public ActionListener getListner(String string) {
		return listeners.get(string);
	}

	protected ResultLoad getQueryLoad(QueryLoad query) throws MyException {
		MyData struct = creatDataTable(query.getPath());
		return new ResultLoad(struct.getId(), struct.getHull(),
				struct.getFindArray());
	}

	protected List<List<String>> getQueryFind(QueryFind query)
			throws MyException {
		return getDataStruct(query.getId()).find(
				(List<String>) query.getQuery());
	}

	public String[] getFileDirectory() {
		//JFileChooser chooser = new JFileChooser();
		String[] list = new File(".\\save").list();
		//List<String> list2 = new ArrayList<String>(list);
	    //FileSystemView view = ;
		return list;//chooser.getFileSystemView();
	}

	@Override
	public void loadFile(String path) throws MyException {
		// TODO Auto-generated method stub
		
	}
}
