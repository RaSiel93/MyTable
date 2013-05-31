package Model;

import java.io.Serializable;

public class UnionCell implements Serializable {
    private int columnFirst;
    private int columnLast;
    private String columnName;

    public UnionCell(int columnBegin, int columnEnd, String columnName) {
	this.columnFirst = columnBegin;
	this.columnLast = columnEnd;
	this.columnName = columnName;
    }

    public int getBeginUnion() {
	return columnFirst;
    }

    public int getEndUnion() {
	return columnLast;
    }

    public String getColumnName() {
	return columnName;
    }
}
