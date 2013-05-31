package Query;

import java.io.Serializable;

public class QueryClose implements Serializable {
	private int id;
	
	public QueryClose(int id) {
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
