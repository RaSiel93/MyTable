package Query;

import java.io.Serializable;
import java.util.List;

public class QueryFind implements Serializable {
//	private int currentPage;
//	private int maxRows;
	private int id;
	private List<String> query;
	
	public QueryFind(int id, List<String> query){//, int currentPage, int maxRows) {
		this.id = id;
		this.query = query;
		//this.currentPage = currentPage;
		//this.maxRows = maxRows;
	}

	public int getId() {
		return id;
	}

	public List<String> getQuery() {
		return query;
	}
	
//	public int getCurrentPage() {
//		return currentPage;
//	}
//
//	public int getMaxRows() {
//		return maxRows;
//	}
}
