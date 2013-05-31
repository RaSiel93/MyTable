package FindPanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Find;

public class FindPanelSimular extends JPanel implements FindPanelType {
    List<JTextField> textFields;
    List<Integer> columns;

    public FindPanelSimular(Find find) {
	this.columns = find.getColumns();
	this.textFields = new ArrayList<JTextField>();

	setPreferredSize(new Dimension(200, 40));
	for (int num = 0; num < columns.size(); num++) {
	    textFields.add(new JTextField(16));
	}

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
	    String tempResult = textFields.get(num).getText();
	    if (!tempResult.equals("")) {
		result.add("column" + String.valueOf(columns.get(num))
			+ " LIKE '" + tempResult + "%'");
	    }
	}
	return result;
    }
}
