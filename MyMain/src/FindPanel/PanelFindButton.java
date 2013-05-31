package FindPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;
import GUI.FrameMain;

public class PanelFindButton extends JPanel {
	public PanelFindButton(FrameMain mainFrame,
			List<JPanel> panelSearchArray) {
		setPreferredSize(new Dimension(200, 46));
		setBackground(Color.darkGray);

		JButton btnFind = new JButton("Найти");
		btnFind.addActionListener(mainFrame.getListener("SEARCH"));
		JButton btnClose = new JButton("Закрыть");
		btnClose.addActionListener(mainFrame.getListener("clFIND"));

		add(btnFind);
		add(btnClose);
	}
}
