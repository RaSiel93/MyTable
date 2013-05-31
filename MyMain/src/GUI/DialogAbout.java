package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class DialogAbout extends JDialog {
	private JButton btnClose;
	private BufferedImage image;

	private class ListnerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	public DialogAbout(JFrame parent) throws IOException {
		super(parent, "Об авторе", true);
		setPreferredSize(new Dimension(200, 300));
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(new LineBorder(Color.GRAY));
		image = ImageIO.read(new File("image/myPhoto.bmp"));
		JLabel picLabel = new JLabel(new ImageIcon(image));
		GridBagConstraints bagConstr = new GridBagConstraints();
		bagConstr.fill = GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel("       Поплавский Александр"), bagConstr);
		bagConstr.gridx = 0;
		bagConstr.gridy = 1;
		panel.add(picLabel, bagConstr);

		bagConstr.gridx = 0;
		bagConstr.gridy = 2;
		panel.add(new JLabel("Это Я!) Студент 2-го курса на"), bagConstr);
		bagConstr.gridx = 0;
		bagConstr.gridy = 3;
		panel.add(new JLabel("специальности ИИ. 121703 гр."), bagConstr);
		bagConstr.gridx = 0;
		bagConstr.gridy = 4;
		panel.add(new JLabel("А это моя 3-я лаба по ППВиС."), bagConstr);
		bagConstr.gridx = 0;
		bagConstr.gridy = 5;
		panel.add(new JLabel("Теперь 02.05.2013 22:34:45"), bagConstr);

		btnClose = new JButton("Закрыть");
		btnClose.addActionListener(new ListnerClose());

		JPanel bp = new JPanel();
		bp.add(btnClose);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}
}