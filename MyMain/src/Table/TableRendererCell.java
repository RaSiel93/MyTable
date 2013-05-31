package Table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TableRendererCell implements TableCellRenderer {
    public TableRendererCell() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
	JLabel header = new JLabel();
	header.setForeground(table.getTableHeader().getForeground());
	header.setBackground(table.getTableHeader().getBackground());
	header.setFont(table.getTableHeader().getFont());

	header.setHorizontalAlignment(JLabel.CENTER);
	header.setText(value.toString());
	header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));

	return header;
    }
}
