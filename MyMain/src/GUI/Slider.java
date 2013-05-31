package GUI;

import java.awt.Dimension;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.Controller;
import Controller.MyException;
import Table.Table;

public class Slider extends JPanel implements ChangeListener {
	static final int FPS_MIN = 1;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15;
	int frameNumber = 15;
	private Table table;

	public Slider(Table table) {
		this.table = table;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(200, 34));
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, FPS_MIN,
				FPS_MAX, FPS_INIT);
		framesPerSecond.addChangeListener(this);
		framesPerSecond.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(1), new JLabel("1"));
		labelTable.put(new Integer(5), new JLabel("5"));
		labelTable.put(new Integer(10), new JLabel("10"));
		labelTable.put(new Integer(15), new JLabel("15"));
		labelTable.put(new Integer(20), new JLabel("20"));
		labelTable.put(new Integer(25), new JLabel("25"));
		labelTable.put(new Integer(30), new JLabel("30"));
		framesPerSecond.setLabelTable(labelTable);

		add(framesPerSecond);
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			int temp = (int) source.getValue();
			try {
				table.setCountRowsPerPage(temp);
			} catch (MyException e1) {
				e1.printStackTrace();
			}
		}
	}
}
