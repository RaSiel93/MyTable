package Table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	private List<String> columnNames;
	private List<List<String>> data;
	private int maxRows = 15;
	private int currentPage = 0;
	private int countRows = 0;

	public void nextPage() {
		currentPage++;
	}

	public void prevPage() {
		currentPage--;
	}

	public boolean existsPage(int number) {
		if (countRows <= (currentPage + number) * maxRows
				|| currentPage + number < 0) {
			return false;
		}
		return true;
	}

	public MyTableModel(List<String> columnNames) {
		this.currentPage = 0;
		this.data = new ArrayList<List<String>>();
		this.columnNames = columnNames;
		this.countRows = columnNames.size();
	}

	public List<List<String>> getData() {
		return data;
	}

	public List<String> getData(int index) {
		return data.get(index);
	}

	public void update(List<List<String>> data) {
		this.data = data;
	}

	public void removeRow(List<Integer> selectedRows) {
		for (int number = selectedRows.size() - 1; number >= 0; number--) {
			this.data.remove((int) selectedRows.get(number));
			fireTableRowsDeleted(selectedRows.get(number),
					selectedRows.get(number));
		}
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		if (data != null) {
			if (maxRows > countRows)
				return countRows;
			if (maxRows * currentPage + maxRows > countRows)
				return countRows - maxRows * currentPage;
			return maxRows;
		}
		return -1;
	}

	public String getColumnName(int col) {
		return columnNames.get(col);
	}

	public Object getValueAt(int row, int col) {
		//if (data.size() != row) {
			return data.get(row).get(col);
		//}
		//return null;
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

	public void setValueAt(String value, int row, int col) {
		data.get(row).set(col, value);
		fireTableCellUpdated(row, col);
	}

	public void removeAll() {
		for (int num = countRows - 1; num > 0; num--) {
			data.remove(num);
		}
	}

	public String getId(int row) {
		return getData(row).get(getColumnCount());

	}

	public void resetPage() {
		currentPage = 0;
	}

	private int maxCurrentPage() {
		int number = 0;
		for (; number < countRows; number++) {
			if (countRows < maxRows * number)
				break;
		}
		return number;
	}

	public void setRowsPerPage(int count) {
		maxRows = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public int getCountPage() {
		return (countRows + maxRows - 1) / maxRows;
	}

	public int getCountRows() {
		return this.countRows;
	}

	public void recurrentPage() {
		if (currentPage > getCountPage() - 1) {
			if (getCountPage() == 0) {
				currentPage = 0;
			} else {
				currentPage = getCountPage() - 1;
			}
		}
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}

	public List<String> getColumnNames() {
		return this.columnNames;
	}
}