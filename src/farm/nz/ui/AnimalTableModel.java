package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Animal;

public class AnimalTableModel extends AbstractTableModel {
	private String[] columnNames = { "Animal", "Price", "Base income", "Buy" };

	private List<Animal> animals;

	public AnimalTableModel() {
		animals = new ArrayList<Animal>();
	}

	public AnimalTableModel(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return animals.size();
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
			return false;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Animal animal = getAnimal(row);

		switch (column) {
		case 0:
			return animal.getType().getDisplay();
		case 1:
			return animal.getPurchasePrice();
		case 2:
			return animal.getBaseIncome();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		Animal animal = getAnimal(row);

		switch (column) {
		case 0:
			animal.setType(animal.getType());
			break;
		case 1:
			animal.setPurchasePrice(animal.getPurchasePrice());
			;
			break;
		case 2:
			animal.setBaseIncome(animal.getBaseIncome());
			break;
		}

		fireTableCellUpdated(row, column);
	}

	public Animal getAnimal(int row) {
		return animals.get(row);
	}
}