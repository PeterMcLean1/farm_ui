package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Crop;

public class CropTableModel extends AbstractTableModel {
	private String[] columnNames = { "Crop", "Price", "Sell Price", "Days to harvest", "Buy" };

	private List<Crop> crops;

	public CropTableModel() {
		crops = new ArrayList<Crop>();
	}

	public CropTableModel(List<Crop> crops) {
		this.crops = crops;
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
		return crops.size();
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
		Crop crop = getCrop(row);

		switch (column) {
		case 0:
			return crop.getType().getDisplay();
		case 1:
			return crop.getPurchasePrice();
		case 2:
			return crop.getSalePrice();
		case 3:
			return crop.getMaturity();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		Crop crop = getCrop(row);

		switch (column) {
		case 0:
			crop.setType(crop.getType());
			break;
		case 1:
			crop.setPurchasePrice(crop.getPurchasePrice());
			;
			break;
		case 2:
			crop.setSalePrice(crop.getSalePrice());
			break;
		}

		fireTableCellUpdated(row, column);
	}

	public Crop getCrop(int row) {
		return crops.get(row);
	}
}