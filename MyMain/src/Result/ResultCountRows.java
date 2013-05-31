package Result;

import java.io.Serializable;

public class ResultCountRows implements Serializable {
	private int count;
	public ResultCountRows(int count){
		this.count = count;
	}
	public int getCount(){
		return count;
	}
}
