package GUI;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import FindPanel.PanelFind;
import Table.Table;

public class PanelTable extends JPanel {
	private int id;
	private Table table;
	private PanelFind panelFind;

	public PanelTable(int id, Table table, PanelFind panelFind) {
		this.id = id;
		this.table = table;
		this.panelFind = panelFind;
		this.panelFind.setVisible(false);
		setLayout(new BorderLayout());
		
		add(this.table, BorderLayout.CENTER);
		add(this.panelFind, BorderLayout.EAST);
	}

	public void switchFindPanel(){
		this.panelFind.setVisible(!this.panelFind.isVisible());
	}
	
	public int getId() {
		return this.id;
	}
	
	public List<String> getHeaderNames(){
		return table.getHeaderNames();
	}

	public void updateTable(List<List<String>> data) {
		table.updateTable(data);
	}

	public List<String> getFindQuery() {
		return this.panelFind.getFindQuery();
	}

	public void resetFind() {
		this.panelFind.resetFind();
	}

	public List<String> getSelectedRows() {
		return this.table.getSelectedRows();
	}

	public int getCurrentPage() {
		return this.table.getCurrentPage();
	}

	public int getMaxRows() {
		return this.table.getMaxRows();
	}
}
