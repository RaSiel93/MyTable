package Query;

import java.io.Serializable;
import java.util.List;

public class QueryAdd implements Serializable {
	private int id;
	private List<List<String>> arrayData;
	
	public QueryAdd(int id, List<List<String>> arrayData){
		this.id = id;
		this.arrayData = arrayData;
	}

	public int getId() {
		return id;
	}

	public List<List<String>> getArrayData() {
		return arrayData;
	}
}
