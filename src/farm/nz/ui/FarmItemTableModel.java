package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Item;

public class FarmItemTableModel extends AbstractTableModel {
	private static String[] COLUMN_NAMES = { "Item", "Bonus", "Application" };

	private List<Item> items;

	public FarmItemTableModel() {
		items = new ArrayList<Item>();
	}

	public FarmItemTableModel(List<Item> items) {
		this.items = items;
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 1:
			return Integer.class;
		default:
			return String.class;
		}
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	public Item getItem(int row) {
		return items.get(row);
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Item item = getItem(row);
		switch (column) {
		case 0:
			return item.getType().getDisplay();
		case 1:
			return item.getBonus();
		case 2:
			String application = "";
			if (item.isAnimal()) {
				application = application + "ANIMAL ";
			}
			if (item.isCrop()) {
				application = application + "CROP ";
			}
			if (item.isSkill()) {
				application = application + "SKILL ";
			}
			return application;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {

		default:
			return false;
		}
	}
}