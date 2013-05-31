package GUI;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;

public class ButtonNavigation extends JButton {
    public ButtonNavigation(String label) {
	super(label);
	setPreferredSize(new Dimension(34, 34));
	setMargin(new Insets(1, 1, 1, 1));
	setAlignmentX(JComponent.CENTER_ALIGNMENT);
    }
}