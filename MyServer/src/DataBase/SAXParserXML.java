package DataBase;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Model.Find;
import Model.HullTable;
import Model.UnionCell;

public class SAXParserXML extends DefaultHandler {

	List<List<String>> data;
	List<Find> findArray;
	HullTable hullTable;

	List<String> node;
	private String label;
	private String type;
	List<Integer> columns;
	List<UnionCell> unions;
	List<String> headerNames;

	String thisElement = "";

	public void startDocument() throws SAXException {
		data = new ArrayList<List<String>>();
		findArray = new ArrayList<Find>();
		unions = new ArrayList<UnionCell>();
		headerNames = new ArrayList<String>();
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		thisElement = qName;
		if (thisElement.equals("node")) {
			node = new ArrayList<String>();
		}
		if (thisElement.equals("find")) {
			label = atts.getValue("label");
			type = atts.getValue("type");
			columns = new ArrayList<Integer>();
		}
		if (thisElement.equals("union")) {
			unions.add(new UnionCell(Integer.parseInt(atts.getValue("first")),
					Integer.parseInt(atts.getValue("last")), atts
							.getValue("union_name")));

		}
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		thisElement = qName;
		if (thisElement.equals("node")) {
			data.add(node);
		}
		if (thisElement.equals("find")) {
			findArray.add(new Find(label, type, columns));
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (thisElement.equals("info")) {
			node.add(new String(ch, start, length));
		}
		if (thisElement.equals("column")) {
			columns.add(Integer.parseInt(new String(ch, start, length)));
		}
		if (thisElement.equals("name")) {
			headerNames.add(new String(ch, start, length));
		}
	}

	public void endDocument() {
		hullTable = new HullTable(headerNames, unions);
	}

	public List<List<String>> getData() {
		return data;
	}

	public List<Find> getFind() {
		return findArray;
	}

	public HullTable getHull() {
		return hullTable;
	}
}