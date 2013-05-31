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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Controller.Controller;

public class DialogLoad extends JDialog {
	private String[] listFile;
	private Controller controller;
	private FrameMain mainFrame;
	
	private JButton btnClose;
	private boolean choice;
	private List<String> headerNames;
	private List<JTextField> textField;
	
	public DialogLoad(FrameMain frameMain, Controller controller,
			String[] listFile) {
		this.listFile = listFile;
		this.controller = controller;
		this.mainFrame = mainFrame;
		
		for(String file : listFile){
			getContentPane().add(new JButton(file));
		}
		getContentPane().add(new JButton("Закрыть"));


		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints bagConstr = new GridBagConstraints();

		bagConstr.fill = GridBagConstraints.HORIZONTAL;

		int numberColumn = 0;
		for(String file : listFile){
			bagConstr.gridx = 0;
			bagConstr.gridy = numberColumn;
			JButton button = new JButton((String) file);
			button.addActionListener(new ListnerLoad(file));
			panel.add(button, bagConstr);
			numberColumn++;
		}
		panel.setBorder(new LineBorder(Color.GRAY));

		//btnAdd = new JButton("Добавить");
		//btnAdd.addActionListener(new ListnerAddData());
		btnClose = new JButton("Закрыть");
		btnClose.addActionListener(new ListnerClose());

		JPanel bp = new JPanel();
		//bp.add(btnAdd);
		bp.add(btnClose);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(mainFrame);
	}

	private class ListnerLoad implements ActionListener {
		private String path;
		ListnerLoad(String path){
			this.path = path;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				controller.loadFile(".//save//" + path);//(FrameMain.getId(), arrayValue);
				//mainFrame.updateTable();
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
}
