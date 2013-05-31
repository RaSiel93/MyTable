package Result;

import java.io.Serializable;
import java.util.List;

import Model.Find;
import Model.HullTable;

public class ResultLoad implements Serializable {
	private HullTable hullTable;
	private List<Find> listFind;
	private int id;
	
	public ResultLoad(int id, HullTable hullTable, List<Find> list){
		this.id = id;
		this.hullTable = hullTable;
		this.listFind = list;
	}
	public int getId() {
		return id;
	}
	public HullTable getHull() {
		return hullTable;
	}
	public List<Find> getFind() {
		return listFind;
	}
}
