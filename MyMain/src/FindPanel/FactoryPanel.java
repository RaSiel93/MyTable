package FindPanel;

import java.sql.SQLException;

import javax.swing.JPanel;

import Model.Find;

public abstract class FactoryPanel {
    public static JPanel createPanel(Find find) {
	if (find.getType().equals("Simular")) {
	    return new FindPanelSimular(find);
	}
	if (find.getType().equals("Check")) {
	    return new FindPanelCheck(find);
	}
	if (find.getType().equals("Diapason")) {
	    return new FindPanelDiapason(find);
	}
	return null;
    }
}
