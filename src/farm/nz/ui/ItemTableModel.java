package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Item;

public class ItemTableModel extends AbstractTableModel {
	private static String[] COLUMN_NAMES = { "Item", "Price", "Bonus", "Buy" };

	private List<Item> items;

	public ItemTableModel() {
		items = new ArrayList<Item>();
	}

	public ItemTableModel(List<Item> items) {
		this.items = items;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		default:
			return int.class;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		default:
			return true;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Item item = getItem(row);

		switch (column) {
		case 0:
			return item.getType().getDisplay();
		case 1:
			return item.getPurchasePrice();
		case 2:
			return item.getBonus();
		case 3:
			return "Buy";
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		Item item = getItem(row);

		switch (column) {
		case 0:
			item.setType(item.getType());
			break;
		case 1:
			item.setPurchasePrice(item.getPurchasePrice());
			;
			break;
		case 2:
			item.setBonus(item.getBonus());
			break;
		}

		fireTableCellUpdated(row, column);
	}

	public Item getItem(int row) {
		return items.get(row);
	}
}