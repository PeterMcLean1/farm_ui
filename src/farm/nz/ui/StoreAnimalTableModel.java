package farm.nz.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Animal;
import farm.nz.model.Game;

public class StoreAnimalTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "Animal type", "Price", "Base income/day  (?)", "On Farm (?)", "" };
	private static final String BUY = "Buy";
	private List<Animal> animals;
	private Game game;

	public StoreAnimalTableModel(List<Animal> animals, Game game) {
		this.animals = animals;
		this.game = game;
	}

	public Animal getAnimal(int row) {
		return animals.get(row);
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 4:
			return String.class;
		default:
			return Integer.class;
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

	@Override
	public int getRowCount() {
		return animals.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Animal animal = getAnimal(row);

		switch (column) {
		case 0:
			return animal.getType().getDisplay();
		case 1:
			return "$" + animal.getPurchasePrice();
		case 2:
			return "$" + animal.getBaseIncome();
		case 3:
			int count = 0;
			for (Animal a : game.getFarm().getAnimals()) {
				if (a.getType() == animal.getType()) {
					count++;
				}
			}
			return count;
		case 4:
			return BUY;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case 4:
			return true;
		default:
			return false;
		}
	}

}