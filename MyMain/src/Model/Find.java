package Model;

import java.io.Serializable;
import java.util.List;

public class Find implements Serializable {
    private String label;
    private String type;
    private List<Integer> columns;

    public Find(String label, String type, List<Integer> columns) {
	this.label = label;
	this.type = type;
	this.columns = columns;
    }

    public String getLabel() {
	return label;
    }

    public String getType() {
	return type;
    }

    public List<Integer> getColumns() {
	return columns;
    }
}
