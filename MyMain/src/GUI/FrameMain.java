package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.Controller;
import Controller.MyException;
import FindPanel.PanelFind;
import Model.Find;
import Model.HullTable;
import Table.Table;

public class FrameMain extends JFrame {
	private class ButtonSwitchTools extends JButton {
		ButtonSwitchTools() {
			setPreferredSize(new Dimension(0, 10));
			addActionListener(getListener("swTOOLS"));
		}
	}

	private static int currentId = -1;

	private Controller controller;
	private FrameMain mainFrame;

	private PanelMenu panelMenu;
	private PanelTools panelTools;
	private JPanel panelTable;
	private PanelTabs panelTabbed;
	private HistoryList historyList;

	private ButtonSwitchTools btnSwitchToolsPanel;

	private Map<String, ActionListener> listeners;

	private class HistoryList {
		private List<Integer> listId;

		HistoryList() {
			listId = new ArrayList<Integer>();
		}

		public void addId(int id) {
			listId.add(id);
		}

		private int getIndex(int id) {
			int index = -1;
			for (Integer currentId : listId) {
				index++;
				if (currentId == id) {
					return index;
				}
			}
			return -1;
		}

		public void removeId(int id) {
			int index = getIndex(id);
			if (index != -1) {
				listId.remove(index);
			}
		}

		public int getId() {
			if (listId.size() == 0) {
				return -1;
			}
			System.out.print("getId = " + listId.get(listId.size() - 1) + "\n");
			return listId.get(listId.size() - 1);
		}

		public void switchId(int id) {
			removeId(id);
			addId(id);
			printHistory();
		}

		private void printHistory() {
			for (Integer id : listId) {
				System.out.print(id + " ");
			}
			System.out.print("\n");
		}
	}

	public FrameMain(final Controller controller) {
		this.controller = controller;
		this.mainFrame = this;
		InitLesteners();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 630);
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout());

		JPanel panelStatus = new JPanel();
		panelStatus.setBackground(Color.orange);

		// arrayTable = new ArrayList<PanelTable>();
		this.historyList = new HistoryList();

		this.panelTools = new PanelTools(controller, this);
		this.panelMenu = new PanelMenu(controller, this);
		this.panelTools.setEnabled(false);
		this.panelMenu.setEnabled(false);
		this.btnSwitchToolsPanel = new ButtonSwitchTools();
		JPanel panelMainTools = new JPanel(new BorderLayout());
		panelMainTools.add(this.panelTools, BorderLayout.NORTH);
		panelMainTools.add(btnSwitchToolsPanel, BorderLayout.SOUTH);

		this.panelTable = new JPanel(new BorderLayout());
		this.panelTable.setBackground(Color.DARK_GRAY);
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.add(panelMainTools, BorderLayout.NORTH);
		panelMain.add(this.panelTable, BorderLayout.CENTER);

		add(panelMenu, BorderLayout.NORTH);
		add(panelMain, BorderLayout.CENTER);
		add(panelStatus, BorderLayout.SOUTH);

		Font font = new Font("Verdana", Font.PLAIN, 10);
		panelTabbed = new PanelTabs();
		panelTabbed.setFont(font);
		// panelTabbed

		panelTabbed.addChangeListener(new SwitchTable());

		panelTable.add(panelTabbed, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class SwitchTable implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JPanel panel = (JPanel) ((JTabbedPane) e.getSource())
					.getSelectedComponent();
			PanelTable table = getPanelTable(panel);
			if (table != null) {
				FrameMain.currentId = table.getId();
				System.out.print("Switch = " + FrameMain.currentId + "\n");

				historyList.switchId(FrameMain.currentId);
			}
		}
	}

	private class PanelTabs extends JTabbedPane {
		@Override
		protected void processMouseEvent(MouseEvent event) {
			if (SwingUtilities.isRightMouseButton(event)) {
				event.consume();
				return;
			}
			super.processMouseEvent(event);
		}
	}

	private PanelTable getPanelTable(JPanel panel) {
		if (panel instanceof PanelTable) {
			return (PanelTable) panel;
		}
		return null;
	}

	public static int getId() {
		return FrameMain.currentId;
	}

	private class ListenerSwitchTools implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnSwitchToolsPanel();
		}
	}

	private class ListenerSwitchFind implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnSwitchFindPanel();
		}
	}

	private class ListenerCloseFind implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnSwitchFindPanel();
			getTable(FrameMain.currentId).resetFind();
			try {
				updateTable();
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ListenerQueryFind implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				updateTable();
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void InitLesteners() {
		listeners = new HashMap<String, ActionListener>();
		listeners.put("swTOOLS", new ListenerSwitchTools());
		listeners.put("swFIND", new ListenerSwitchFind());
		listeners.put("clFIND", new ListenerCloseFind());
		listeners.put("SEARCH", new ListenerQueryFind());
	}

	public ActionListener getListener(String method) {
		return listeners.get(method);
	}

	private void btnSwitchToolsPanel() {
		changeLabel();
		this.panelTools.setVisible(!this.panelTools.isVisible());
	}

	private void btnSwitchFindPanel() {
		getTable(FrameMain.currentId).switchFindPanel();
	}

	private void changeLabel() {
		if (this.panelTools.isVisible()) {
			this.btnSwitchToolsPanel.setText("+");
		} else {
			this.btnSwitchToolsPanel.setText("-");
		}
	}

	public void creatTable(int id, HullTable hull, List<Find> finds)
			throws MyException {
		FrameMain.currentId = id;
		historyList.addId(FrameMain.currentId);
		PanelTable table = new PanelTable(id, new Table(hull, mainFrame,
				controller), new PanelFind(finds, mainFrame));
		panelTabbed.addTab(String.valueOf(id), table);
		panelTabbed.setSelectedComponent(table);
		System.out.print("Creat = " + FrameMain.currentId + "\n");
	}

	public void removeTable(int id) {
		historyList.removeId(FrameMain.currentId);
		this.panelTabbed.remove(getTable(id));
		System.out.print("Delete = " + FrameMain.currentId + "\n");
		if (this.panelTabbed.getTabCount() > 0) {
			// PanelTable table = getPanelTable((JPanel) this.panelTable
			// .getComponent(0));
			// this.panelTabbed.setSelectedComponent(table);
			FrameMain.currentId = historyList.getId();
			System.out.print("Switch = " + FrameMain.currentId + "\n");
		} else {
			FrameMain.currentId = -1;
		}
	}

	public List<String> getFindQuery() {
		List<String> findQuery = getTable(FrameMain.currentId).getFindQuery();
		findQuery.add(" LIMIT "
				+ (getTable(FrameMain.currentId).getCurrentPage() * getTable(
						FrameMain.currentId).getMaxRows()) + ","
				+ getTable(FrameMain.currentId).getMaxRows() + " ");
		return findQuery;
	}

	private PanelTable getTable(int id) {
		System.out.print("Get = " + FrameMain.currentId + "\n");
		return (PanelTable) panelTabbed.getSelectedComponent();
	}

	public void updateFrame() throws MyException {
		if (panelTabbed.getTabCount() > 0) {
			updateTable();
			panelMenu.setEnabled(true);
			panelTools.setEnabled(true);
		} else {
			panelMenu.setEnabled(false);
			panelTools.setEnabled(false);
		}
		repaint();
		revalidate();
	}

	public void dialogAdd() {
		DialogAdd dlgAdd = new DialogAdd(this, controller);
		dlgAdd.setVisible(true);
	}

	public void dialogAbout() throws IOException {
		DialogAbout dlgAbout = new DialogAbout(this);
		dlgAbout.setVisible(true);
	}

	public void dialogRemove(int countRows) throws MyException {
		try {
			DialogRemove dlgRemove = new DialogRemove(this, countRows);
			dlgRemove.setVisible(true);
		} catch (IOException e) {
			throw new MyException(e);
		}
	}

	public void updateTable() throws MyException {
		getTable(FrameMain.currentId).updateTable(
				controller.getData(FrameMain.currentId, getFindQuery()));
	}

	public List<String> getSelectedRows() {
		return getTable(FrameMain.currentId).getSelectedRows();
	}

	public int getCurrentPage() {
		return getTable(FrameMain.currentId).getCurrentPage();
	}

	public int getMaxRows() {
		return getTable(FrameMain.currentId).getMaxRows();
	}

	public List<String> getHeaderNames() {
		return getTable(FrameMain.currentId).getHeaderNames();
	}

	public void dialogConnect() {
		DialogConnect dlgConnect = new DialogConnect(this, controller);
		dlgConnect.setVisible(true);
	}

	public void dialogLoad(String[] listFile) {
		DialogLoad dlgLoad = new DialogLoad(this, controller, listFile);
		dlgLoad.setVisible(true);
	}
}
