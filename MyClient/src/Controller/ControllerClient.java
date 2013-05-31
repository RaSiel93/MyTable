package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

//import DataBase.MyData;
import GUI.FrameMain;
import Model.Find;
import Model.HullTable;
import Query.QueryAdd;
import Query.QueryClose;
import Query.QueryCountRows;
import Query.QueryDelete;
import Query.QueryFind;
import Query.QueryLoad;
import Query.QueryFile;
import Result.ResultCountRows;
import Result.ResultFile;
import Result.ResultLoad;
import Table.MyTableModel;

public class ControllerClient implements Controller {
	private static FrameMain mainFrame;
	private HashMap<String, ActionListener> listeners = initListeners();

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket clientSocket;

	public ControllerClient() {
	}

	@Override
	public void setFrame(FrameMain mainFrame) {
		this.mainFrame = mainFrame;
	}

	public int connect(String connect) {
		try {
			clientSocket = new Socket(connect, 4444);
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public List<List<String>> getData(int id, List<String> queryFind)
			throws MyException {
		try {
			out.writeObject(new QueryFind(id, queryFind));
			List<List<String>> data = (List<List<String>>) in.readObject();
			return data;
		} catch (IOException | ClassNotFoundException e) {
			throw new MyException(e);
		}
	}

	private void updateFrame() throws MyException {
		mainFrame.updateFrame();
	}

	@Override
	public int getCountRows(int id) throws MyException {
		try {
			out.writeObject(new QueryCountRows(id));
			return ((ResultCountRows) in.readObject()).getCount();
		} catch (IOException e) {
			throw new MyException(e);
		} catch (ClassNotFoundException e) {
			throw new MyException(e);
		}
	}

	@Override
	public void close(int id) throws MyException {
		mainFrame.removeTable(id);
		try {
			out.writeObject(new QueryClose(id));
			in.readObject();
		} catch (IOException e) {
			throw new MyException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void closeAll() throws MyException {
		while (FrameMain.getId() != -1) {
			close(FrameMain.getId());
		}
		if (clientSocket != null) {
			try {
				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				throw new MyException(e);
			}
		}
	}

	@Override
	public void addInTable(int id, List<List<String>> value) throws MyException {
			try {
				out.writeObject(new QueryAdd(id, value));
				in.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

	private class Connect implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainFrame.dialogConnect();
		}
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
					out.writeObject(new QueryDelete(FrameMain.getId(), listSelected));
					in.readObject();
					mainFrame.dialogRemove(listSelected.size());
					updateFrame();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (MyException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class LoadData implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] listFile = null;
			try {
				out.writeObject(new QueryFile());
				ResultFile result = (ResultFile) in.readObject();
				listFile = result.getListFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			mainFrame.dialogLoad(listFile);
		}
	}

	private class CloseFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				close(mainFrame.getId());
				mainFrame.updateFrame();
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
		listeners.put("CONNECT", new Connect());
		listeners.put("ADD", new AddData());
		listeners.put("DELETE", new DeleteData());
		listeners.put("LOAD", new LoadData());
		listeners.put("ABOUT", new About());
		listeners.put("CLOSE", new CloseFile());
		listeners.put("EXIT", new ExitFile());
		return listeners;
	}

	public ActionListener getListner(String string) {
		return listeners.get(string);
	}

	@Override
	public void loadFile(String path) throws MyException {
		try {
			QueryLoad file = new QueryLoad(path);
					//new QueryLoad("C:\\Users\\RaSiel\\workspace\\Server-Client\\MyServer\\save\\variant3.xml");
			out.writeObject(file);
			ResultLoad result = (ResultLoad) in.readObject();
			if(result != null){
				mainFrame.creatTable(result.getId(), result.getHull(), result.getFind());
				updateFrame();
			}
		} catch (MyException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
}
