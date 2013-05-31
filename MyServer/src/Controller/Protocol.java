package Controller;
import java.sql.SQLException;

import Query.QueryAdd;
import Query.QueryCountRows;
import Query.QueryDelete;
import Query.QueryFile;
import Query.QueryFind;
import Query.QueryLoad;
import Query.QueryClose;
import Result.ResultCountRows;
import Result.ResultFile;
public class Protocol {
	private ControllerServer controller;
	public Protocol(ControllerServer controller) {
		this.controller = controller;
	}
	public Object execute(Object query) throws ClassNotFoundException, SQLException, Exception {
		if(query instanceof QueryLoad){
			return controller.getQueryLoad((QueryLoad)query);
		} else if(query instanceof QueryFind){
			return controller.getQueryFind((QueryFind)query);
		} else if(query instanceof QueryAdd){
			controller.addInTable(((QueryAdd)query).getId(), ((QueryAdd)query).getArrayData());
		} else if(query instanceof QueryDelete){
			controller.deleteTable(((QueryDelete)query).getId(), ((QueryDelete)query).getListSelected());
		} else if(query instanceof QueryClose){
			controller.close(((QueryClose)query).getId());
		} else if(query instanceof QueryCountRows){
			return new ResultCountRows(controller.getCountRows(((QueryCountRows)query).getId()));
		} else if(query instanceof QueryFile){
			return new ResultFile(controller.getFileDirectory());
		}				
		return null;
	}
}
