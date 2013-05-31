package Table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Controller.Controller;
import Controller.MyException;
import GUI.FrameMain;
import GUI.TableNavigation;
import Model.HullTable;
import Model.UnionCell;

public class Table extends JPanel {
	private FrameMain mainFrame;
	private Controller controller;

	private JTable table;
	private MyTableModel myTableModel;
	private TableNavigation navigation;

	public Table(HullTable hull, FrameMain mainFrame,
			Controller controller) {
		this.mainFrame = mainFrame;
		this.controller = controller;
		this.setMyTableModel(new MyTableModel(hull.getHeaderNames()));
		this.table = new JTableExtension(getMyTableModel());
		List<UnionCell> unionCells = hull.getUnionCells();
		for (int number = 0; number < unionCells.size(); number++) {
			addUnionCell((UnionCell) unionCells.get(number));
		}

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
		navigation = new TableNavigation(this, controller);
		add(navigation, BorderLayout.SOUTH);

		this.table.setRowHeight(20);
		this.table.setGridColor(Color.BLUE);
		this.table.setDragEnabled(false);
	}

	public List<String> getHeaderNames() {
		return this.getMyTableModel().getColumnNames();
	}

	private final class JTableExtension extends JTable {
		private JTableExtension(TableModel dm) {
			super(dm);
		}

		protected JTableHeader createDefaultTableHeader() {
			return new GroupableTableHeader(columnModel);
		}
	}

	private class ActionPrevPage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getMyTableModel().prevPage();
			navigationExists();
			navigationNumberPage();
			try {
				mainFrame.updateFrame();
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ActionNextPage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getMyTableModel().nextPage();
			navigationExists();
			navigationNumberPage();
			try {
				mainFrame.updateFrame();
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}

	private ActionListener prevPage = new ActionPrevPage();
	private ActionListener nextPage = new ActionNextPage();

	public ActionListener getPrevPage() {
		return prevPage;
	}

	public ActionListener getNextPage() {
		return nextPage;
	}

	public void setCountRowsPerPage(int count) throws MyException {
		getMyTableModel().setRowsPerPage(count);
		getMyTableModel().resetPage();
		navigationExists();
		navigationNumberPage();
		mainFrame.updateFrame();
	}

	private void navigationNumberPage() {
		navigation.getCurrentPage().setText(
				"Cтраница " + (getMyTableModel().getCurrentPage() + 1) + " из "
						+ getMyTableModel().getCountPage());
		navigation.getCountRows().setText(
				"Всего записей: " + getMyTableModel().getCountRows());
	}

	public void updateTable(List<List<String>> data) {
		((MyTableModel) this.table.getModel()).update(data);
		((MyTableModel) this.table.getModel()).fireTableDataChanged();
		getMyTableModel().recurrentPage();
		try {
			getMyTableModel().setCountRows(
					controller.getCountRows(FrameMain.getId()));
		} catch (MyException e) {
			e.printStackTrace();
		}
		navigationNumberPage();
		navigationExists();
	}

	private void addUnionCell(UnionCell unionCell) {
		TableColumnModel cm = table.getColumnModel();

		ColumnGroup column = new ColumnGroup(unionCell.getColumnName());
		for (int numberCell = unionCell.getBeginUnion(); numberCell < unionCell
				.getEndUnion(); numberCell++) {
			column.add(cm.getColumn(numberCell));
		}
		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();
		header.addColumnGroup(column);
	}

	public List<String> getSelectedRows() {
		List<String> selectedRows = new ArrayList<String>();
		for (int number = 0; number < table.getRowCount(); number++) {
			if (table.getSelectionModel().isSelectedIndex(number)) {
				selectedRows.add(getMyTableModel().getId(number));
			}
		}
		return selectedRows;
	}

	private void navigationExists() {
		navigation.getButPrev().setEnabled(getMyTableModel().existsPage(-1));
		navigation.getButNext().setEnabled(getMyTableModel().existsPage(1));
	}

	public int getCurrentPage() {
		return getMyTableModel().getCurrentPage();
	}

	public int getMaxRows() {
		return getMyTableModel().getMaxRows();
	}

	public MyTableModel getMyTableModel() {
		return myTableModel;
	}

	public void setMyTableModel(MyTableModel myTableModel) {
		this.myTableModel = myTableModel;
	}
}