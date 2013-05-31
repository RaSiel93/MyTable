package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Controller.Controller;

public class DialogAdd extends JDialog {
	private Controller controller;
	private FrameMain mainFrame;
	private JButton btnAdd;
	private JButton btnClose;
	private boolean choice;
	private List<String> headerNames;
	private List<JTextField> textField;

	private class ListnerAddData implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<String> newData = new ArrayList<String>();
			List<List<String>> arrayValue = new ArrayList<List<String>>();
			for (int number = 0; number < textField.size(); number++) {
				newData.add(textField.get(number).getText());
			}
			arrayValue.add(newData);
			try {
				controller.addInTable(FrameMain.getId(), arrayValue);
				mainFrame.updateTable();
				dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	private class ListnerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	public DialogAdd(JFrame parent, final Controller controller) {
		super(parent, "Добавить запись", true);
		this.controller = controller;
		this.mainFrame = (FrameMain) parent;
		textField = new ArrayList<JTextField>();

		headerNames = mainFrame.getHeaderNames();

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints bagConstr = new GridBagConstraints();

		bagConstr.fill = GridBagConstraints.HORIZONTAL;

		int numberColumn = 0;
		for (Iterator iter = headerNames.iterator(); iter.hasNext();) {
			bagConstr.gridx = 0;
			bagConstr.gridy = numberColumn;
			panel.add(new JLabel((String) iter.next() + ":"), bagConstr);

			bagConstr.gridx = 1;
			textField.add(new JTextField(20));
			panel.add(textField.get(numberColumn), bagConstr);

			numberColumn++;
		}
		panel.setBorder(new LineBorder(Color.GRAY));

		btnAdd = new JButton("Добавить");
		btnAdd.addActionListener(new ListnerAddData());
		btnClose = new JButton("Закрыть");
		btnClose.addActionListener(new ListnerClose());

		JPanel bp = new JPanel();
		bp.add(btnAdd);
		bp.add(btnClose);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}
	public boolean isChoice() {
		return choice;
	}
}