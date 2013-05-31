package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;
import Table.Table;

public class TableNavigation extends JPanel {
	private ButtonNavigation butPrev;
	private ButtonNavigation butNext;
	private Slider rowsPerPage;
	private JLabel labCurrentPage;
	private JLabel labCountRows;

	public JButton getButPrev() {
		return butPrev;
	}

	public JButton getButNext() {
		return butNext;
	}

	public JLabel getCurrentPage() {
		return labCurrentPage;
	}

	public JLabel getCountRows() {
		return labCountRows;
	}

	public TableNavigation(Table table, Controller controller) {
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(60, 46));

		labCurrentPage = new JLabel();
		labCurrentPage.setForeground(Color.WHITE);

		labCountRows = new JLabel();
		labCountRows.setForeground(Color.WHITE);

		butPrev = new ButtonNavigation("<");
		butPrev.addActionListener(table.getPrevPage());

		butNext = new ButtonNavigation(">");
		butNext.addActionListener(table.getNextPage());

		rowsPerPage = new Slider(table);

		add(labCurrentPage);
		add(butPrev);
		add(rowsPerPage, BorderLayout.CENTER);
		add(butNext);
		add(labCountRows);
	}

}
