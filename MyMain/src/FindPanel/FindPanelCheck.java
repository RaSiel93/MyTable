package FindPanel;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Find;

public class FindPanelCheck extends JPanel implements FindPanelType {
    List<Checkbox> checkboxes;
    List<Integer> columns;

    public FindPanelCheck(Find find) {
	this.columns = find.getColumns();
	this.checkboxes = new ArrayList<Checkbox>();

	setPreferredSize(new Dimension(200, 40));
	for (int num = 0; num < columns.size(); num++) {
	    checkboxes.add(new Checkbox(String.valueOf(num + 1)));
	}

	add(new JLabel(find.getLabel()));

	for (int num = 0; num < checkboxes.size(); num++) {
	    add(checkboxes.get(num));
	}
    }

    public void clear() {
	for (int num = 0; num < checkboxes.size(); num++) {
	    checkboxes.get(num).setState(false);
	}
    }

    public List<String> getResult() {
	List<String> result = new ArrayList<String>();

	for (int num = 0; num < checkboxes.size(); num++) {
	    if (checkboxes.get(num).getState()) {
		result.add("column" + String.valueOf(columns.get(num)) + " > 0");
	    }
	}
	return result;
    }
}
