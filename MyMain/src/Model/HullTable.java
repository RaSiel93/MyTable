package Model;

import java.io.Serializable;
import java.util.List;

public class HullTable implements Serializable {
    private List<String> headerNames;
    private List<UnionCell> unionCells;

    public HullTable(List<String> headerNames, List<UnionCell> unionCells) {
	this.headerNames = headerNames;
	this.unionCells = unionCells;
    }

    public List<String> getHeaderNames() {
	return headerNames;
    }

    public List<UnionCell> getUnionCells() {
	return unionCells;
    }
}
