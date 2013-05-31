package Query;

import java.io.Serializable;

public class QueryCountRows implements Serializable {
	private int id;

	public QueryCountRows(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
