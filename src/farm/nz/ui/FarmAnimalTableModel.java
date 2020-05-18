package farm.nz.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Animal;

public class FarmAnimalTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "Animal type", "Base income/day", "Happiness", "Health",
			"(+2 happiness)", "(+bonus heath)" };
	private List<Animal> animals;

	public FarmAnimalTableModel(List<Animal> animals) {
		this.animals = animals;
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
		case 5:
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
			return animal.getBaseIncome();
		case 2:
			return animal.getHappy();
		case 3:
			return animal.getHealth();
		case 4:
			return "Play";
		case 5:
			return "Use item";
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case 4:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}

}