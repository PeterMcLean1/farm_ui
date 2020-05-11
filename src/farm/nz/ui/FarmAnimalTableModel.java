package farm.nz.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import farm.nz.model.Animal;

public class FarmAnimalTableModel extends AbstractTableModel {
	private static String[] COLUMN_NAMES = { "Animal Type", "Base income", "Happiness", "Health", "", "" }; // play, use
																											// item

	private List<Animal> animals;

	public FarmAnimalTableModel() {
		animals = new ArrayList<Animal>();
	}

	public FarmAnimalTableModel(List<Animal> animals) {
		this.animals = animals;
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
		case 3:
			return animal.getBaseIncome();
		case 4:
			return "Play (+2 Happy)";
		case 5:
			return "Use item";
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

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

}