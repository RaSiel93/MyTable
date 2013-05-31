package Query;

import java.io.Serializable;

public class QueryLoad implements Serializable {
	private String path;
	public QueryLoad(String path){
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
