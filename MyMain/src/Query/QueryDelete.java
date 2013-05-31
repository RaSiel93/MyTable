package Query;

import java.io.Serializable;
import java.util.List;

public class QueryDelete implements Serializable {
	private List<String> listSelected;
	private int id;
	public QueryDelete(int id, List<String> listSelected) {
		this.id = id;
		this.listSelected = listSelected;
	}
	
	public int getId() {
		return id;
	}
	
	public List<String> getListSelected() {
		return listSelected;
	}
}
