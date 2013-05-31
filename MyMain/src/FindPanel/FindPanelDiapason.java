package FindPanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Find;

public class FindPanelDiapason extends JPanel implements FindPanelType {
    List<JTextField> textFields;
    int column;

    public FindPanelDiapason(Find find) {
	this.column = find.getColumns().get(0);
	this.textFields = new ArrayList<JTextField>();

	setPreferredSize(new Dimension(200, 40));
	// for (int num = 0; num < columns.size(); num++) {
	textFields.add(new JTextField(3));
	textFields.add(new JTextField(3));
	// }

	add(new JLabel(find.getLabel()));
	for (int num = 0; num < textFields.size(); num++) {
	    add(textFields.get(num));
	}
    }

    public void clear() {
	for (int num = 0; num < textFields.size(); num++) {
	    textFields.get(num).setText("");
	}
    }

    public List<String> getResult() {
	List<String> result = new ArrayList<String>();

	for (int num = 0; num < textFields.size(); num++) {
	    String tempResult1 = textFields.get(0).getText();
	    String tempResult2 = textFields.get(1).getText();

	    if (!tempResult1.equals("")) {
		result.add("column" + String.valueOf(column) + " > "
			+ tempResult1);
	    }
	    if (!tempResult2.equals("")) {
		result.add("column" + String.valueOf(column) + " < "
			+ tempResult2);
	    }
	}
	return result;
    }
}
