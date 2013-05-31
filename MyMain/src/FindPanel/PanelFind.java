package FindPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;
import GUI.FrameMain;
import Model.Find;
import Controller.MyException;
public class PanelFind extends JPanel {

	private List<JPanel> panelSearchArray;

	public PanelFind(List<Find> finds, FrameMain mainFrame) throws MyException {
		
		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
		Box box = Box.createVerticalBox();
		panelSearchArray = new ArrayList<JPanel>();
		for (int num = 0; num < finds.size(); num++) {
			panelSearchArray.add(FactoryPanel.createPanel(finds.get(num)));
		}
		for (int i = 0; i < panelSearchArray.size(); i++) {
			box.add(panelSearchArray.get(i));
		}
		add(box);
		add(new PanelFindButton(mainFrame, panelSearchArray),
				BorderLayout.SOUTH);
	}

	public List<String> getFindQuery() {
		List<String> findQuery = new ArrayList<String>();
		for (int num = 0; num < panelSearchArray.size(); num++) {
			List<String> tempFindQuery = ((FindPanelType) panelSearchArray
					.get(num)).getResult();
			for (int num2 = 0; num2 < tempFindQuery.size(); num2++) {
				findQuery.add(tempFindQuery.get(num2));
			}
		}
		return findQuery;
	}

	public void resetFind() {
		for (int i = 0; i < panelSearchArray.size(); i++) {
			((FindPanelType) panelSearchArray.get(i)).clear();
		}
	}
}
