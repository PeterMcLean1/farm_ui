package farm.nz.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Game;
import farm.nz.model.Item;

public class StoreItemTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "Item name", "Price", "Bonus  (?)", "Used on", "On Farm (?)", "" };
	private static final String BUY = "Buy";
	private static final String ANIMAL = "Animal ";
	private static final String CROP = "Crop ";
	private static final String SKILL = "Skill ";
	private List<Item> items;
	private Game game;

	public StoreItemTableModel(List<Item> items, Game game) {
		this.items = items;
		this.game = game;
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 3:
			return String.class;
		case 5:
			return String.class;
		default:
			return int.class;
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
			return "$" + item.getPurchasePrice();
		case 2:
			return item.getBonus();
		case 3:
			String application = "";
			if (item.isAnimal()) {
				application = application + ANIMAL;
			}
			if (item.isCrop()) {
				application = application + CROP;
			}
			if (item.isSkill()) {
				application = application + SKILL;
			}
			return application;
		case 4:
			int count = 0;
			for (Item i : game.getFarm().getItems()) {
				if (i.getType() == item.getType()) {
					count++;
				}
			}
			return count;
		case 5:
			return BUY;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case 5:
			return true;
		default:
			return false;
		}
	}
}