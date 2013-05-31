package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

public class DialogConnect extends JDialog {
	private JTextField textConnect;
	private JButton buttonConnect;
	private JButton buttonClose;

	private FrameMain mainFrame;
	private Controller controller;

	DialogConnect(JFrame parent, final Controller controller) {
		super(parent, "Присоединиться к серверу", true);
		this.controller = controller;
		this.mainFrame = (FrameMain) parent;

		textConnect = new JTextField("192.168.1.4");
		buttonConnect = new JButton("Присоединиться");
		buttonClose = new JButton("Закрыть");

		buttonConnect.addActionListener(new ListnerConnect());
		buttonClose.addActionListener(new ListnerClose());

		JPanel bp = new JPanel();
		bp.add(buttonConnect);
		bp.add(buttonClose);

		getContentPane().add(textConnect, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	private class ListnerConnect implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (controller.connect(textConnect.getText()) == -1) {
				//getContentPane().add(new JLabel("Ошибка соединения"));
			} else {
				dispose();
			}
		}
	}

	private class ListnerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
