package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogRemove extends JDialog {
	private JButton btnClose;

	private class ListnerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	public DialogRemove(JFrame parent, int countRows) throws IOException {
		super(parent, "Удаление записей", true);

		setPreferredSize(new Dimension(200, 100));

		String message = "Нечего удалять!";
		if (countRows > 0) {
			if (countRows % 100 > 4 && countRows % 100 < 21
					|| countRows % 10 > 4) {
				message = "Удалено " + countRows + " записей!";
			} else if (countRows % 10 == 1) {
				message = "Удалена " + countRows + " запись!";
			} else {
				message = "Удалено " + countRows + " записи!";
			}
		}

		JPanel panel = new JPanel();
		panel.add(new JLabel(message));

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
