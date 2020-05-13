package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Crop;
import farm.nz.model.Paddock;

public class FarmPaddockTableModel extends AbstractTableModel {
	private static String[] COLUMN_NAMES = { "Paddock ID", "Crop", "Days to mature", "Sell Price", "Water (free)", "",
			"" }; // water,
	// use
	// item,
	// harvest

	private List<Paddock> paddocks;

	public FarmPaddockTableModel() {
		paddocks = new ArrayList<Paddock>();
	}

	public FarmPaddockTableModel(List<Paddock> paddocks) {
		this.paddocks = paddocks;
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 2:
			return Integer.class;
		case 3:
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

	public Paddock getPaddock(int row) {
		return paddocks.get(row);
	}

	@Override
	public int getRowCount() {
		return paddocks.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Paddock paddock = getPaddock(row);
		Crop crop = paddock.getCrop();
		switch (column) {
		case 0:
			return paddock.getPaddockID();
		case 1:
			return (null != crop) ? crop.getType().getDisplay() : "No crop";
		case 2:
			if (null == crop) {
				return "-";
			} else if (crop.getMaturity() > 0) {

				return crop.getMaturity();
			} else {
				return 0;
			}

		case 3:
			return (null != crop) ? crop.getSalePrice() : "-";
		case 4:
			return "Water";
		case 5:
			return "Use item";
		case 6:
			return "Harvest";
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
		case 6:
			return true;
		default:
			return false;
		}
	}
}