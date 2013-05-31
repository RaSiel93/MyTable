package Result;

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileSystemView;

public class ResultFile implements Serializable {
	private String[] listFile;
	public ResultFile(String[] listFile){
		this.listFile = listFile;
	}
	
	public String[] getListFile(){
		return listFile;
	}
}
