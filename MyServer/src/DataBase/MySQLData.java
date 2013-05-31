package DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mysql.jdbc.DatabaseMetaData;

import Controller.MyException;
import Model.Find;
import Model.HullTable;

public class MySQLData implements MyData {
	private Connection connection;
	private Statement statement;

	private String pathToFile;
	private int id;
	private int countColumn;

	private HullTable hullTable;
	private List<Find> findArray;

	public MySQLData(String pathToFile, int id) throws MyException {
		this.pathToFile = pathToFile;
		this.id = id;
		this.countColumn = 0;

		try {
			this.connection = MySQLFactory.createConnection();
			this.statement = connection.createStatement();
			readXML();
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public boolean isId(int id) {
		return this.id == id;
	}

	public int getId() {
		return id;
	}

	public HullTable getHull() {
		return hullTable;
	}

	public List<Find> getFindArray() {
		return findArray;
	}

	public int getCountRows() throws MyException {
		String query = "SELECT COUNT(*) FROM myTable" + id + ";";
		try {
			ResultSet result = statement.executeQuery(query);
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	//
	// private List<List<String>> getData() throws MyException {
	// String select = "SELECT * FROM myTable" + id + ";";
	// try {
	// ResultSet result = statement.executeQuery(select);
	// return resultSetToList(result);
	// } catch (SQLException e) {
	// throw new MyException(e);
	// }
	// }

	public void readXML() throws MyException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			SAXParserXML saxParser = new SAXParserXML();

			File file = new File(this.pathToFile);
			parser.parse(file, saxParser);
			this.hullTable = saxParser.getHull();
			this.findArray = saxParser.getFind();
			this.countColumn = this.hullTable.getHeaderNames().size();
			create();
			insert(saxParser.getData());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void writeIntoXML(String path) throws MyException {
		List<List<String>> value = find(new ArrayList<String>());

		if (!path.equals("")) {
			this.pathToFile = path;
		}

		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = f.newDocumentBuilder();

			Document doc = (Document) builder.newDocument();

			Element rootDoc = doc.createElement("table");
			doc.appendChild(rootDoc);

			Element header = doc.createElement("header");
			rootDoc.appendChild(header);

			Element names = doc.createElement("names");
			header.appendChild(names);
			for (int num = 0; num < hullTable.getHeaderNames().size(); num++) {
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(hullTable.getHeaderNames()
						.get(num)));
				names.appendChild(name);
			}
			Element unions = doc.createElement("unions");
			header.appendChild(unions);
			for (int num = 0; num < hullTable.getUnionCells().size(); num++) {
				Element union = doc.createElement("union");

				union.setAttribute("first", String.valueOf((hullTable
						.getUnionCells().get(num).getBeginUnion())));
				union.setAttribute("last", String.valueOf((hullTable
						.getUnionCells().get(num).getEndUnion())));
				union.setAttribute("union_name", String.valueOf((hullTable
						.getUnionCells().get(num).getColumnName())));
				unions.appendChild(union);
			}

			Element finds = doc.createElement("finds");
			rootDoc.appendChild(finds);
			for (int num = 0; num < findArray.size(); num++) {
				Element find = doc.createElement("find");
				find.setAttribute("label", findArray.get(num).getLabel());
				find.setAttribute("type", findArray.get(num).getType());

				List<Integer> columnArray = findArray.get(num).getColumns();
				for (int num2 = 0; num2 < columnArray.size(); num2++) {
					Element column = doc.createElement("column");
					column.appendChild(doc.createTextNode(String
							.valueOf(columnArray.get(num2))));
					find.appendChild(column);
				}
				finds.appendChild(find);
			}

			Element dataEl = doc.createElement("data");
			rootDoc.appendChild(dataEl);
			for (int num = 0; num < value.size(); num++) {
				Element node = doc.createElement("node");
				dataEl.appendChild(node);

				for (int num2 = 0; num2 < value.get(num).size(); num2++) {
					Element info = doc.createElement("info");
					node.appendChild(info);
					info.appendChild(doc.createTextNode(value.get(num)
							.get(num2)));
				}
			}
			Transformer t = TransformerFactory.newInstance().newTransformer();

			t.transform(new DOMSource(doc), new StreamResult(
					new FileOutputStream(pathToFile)));
		} catch (FileNotFoundException | TransformerException
				| ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void create() throws MyException {
		String request = "CREATE TABLE `feedback`.`myTable" + id + "` (";
		for (int num = 0; num < this.countColumn; num++) {
			request += "`column" + String.valueOf(num) + "` TEXT NULL,";
		}
		request += "`id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(`id`));";
		try {
			if (isTableExists()) {
				clear();
			}
			statement.executeUpdate(request);
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	private boolean isTableExists() throws MyException {
		try {
			DatabaseMetaData dbm = (DatabaseMetaData) connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, "myTable" + id, null);
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			throw new MyException(e);
		}
		return false;
	}

	public void clear() throws MyException {
		String request = "DROP TABLE `feedback`.`myTable" + id + "`";
		try {
			statement.executeUpdate(request);
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public void insert(List<List<String>> value) throws MyException {
		List<String> insertList = new ArrayList<String>();
		for (int numRow = 0; numRow < value.size(); numRow++) {
			String str1 = "INSERT INTO `feedback`.`myTable" + id + "` (";
			String str2 = "VALUES (";
			for (int num = 0; num < this.countColumn; num++) {
				str1 += "`column" + String.valueOf(num) + "`,";
				str2 += "'" + value.get(numRow).get(num) + "',";
			}
			str1 = str1.substring(0, str1.length() - 1);
			str2 = str2.substring(0, str2.length() - 1);

			insertList.add(str1 + ") " + str2 + ");");
		}
		try {
			for (int num = 0; num < insertList.size(); num++) {
				statement.executeUpdate(insertList.get(num));
			}
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public void delete(List<String> idArray) throws MyException {
		String delete = "DELETE FROM `feedback`.`myTable" + id
				+ "` WHERE `id` IN (";
		for (int num = 0; num < idArray.size(); num++) {
			delete += "'" + idArray.get(num) + "',";
		}
		delete = delete.substring(0, delete.length() - 1);
		delete += ");";
		try {
			statement.executeUpdate(delete);
		} catch (SQLException e) {
			throw new MyException(e);
		}
	}

	public List<List<String>> find(List<String> filter) throws MyException {
		//filter.add(" LIMIT 1,5 ");
		String myFind = "SELECT * FROM myTable" + id;
		String limit = "";

		if (filter.size() != 0) {
			if ((filter.get(filter.size() - 1).substring(0, 7))
					.equals(" LIMIT ")) {
				limit += filter.get(filter.size() - 1);
				filter.remove(filter.size() - 1);
			}
			if (filter.size() > 0) {
				myFind += " WHERE ";
				String find = "";
				for (int num = 0; num < filter.size(); num++) {
					find += filter.get(num);
					find += " AND ";
				}
				find = find.substring(0, find.length() - 5);
				myFind += find;
			}
			myFind += limit;
		}
		try {
			ResultSet result = statement.executeQuery(myFind);
			return resultSetToList(result);
		} catch (SQLException e) {
			throw new MyException(e);
		}

	}

	private List<List<String>> resultSetToList(ResultSet resultQuery)
			throws MyException {
		List<List<String>> result = new ArrayList<List<String>>();
		try {
			while (resultQuery.next()) {
				List<String> tempList = new ArrayList<String>();
				for (int num = 0; num < countColumn; num++) {
					tempList.add(resultQuery.getString("column"
							+ String.valueOf(num)));
				}
				tempList.add(resultQuery.getString("id"));
				result.add(tempList);
			}
		} catch (SQLException e) {
			throw new MyException(e);
		}
		return result;
	}
}
