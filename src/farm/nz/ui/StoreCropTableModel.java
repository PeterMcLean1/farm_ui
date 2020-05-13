package farm.nz.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import farm.nz.model.Crop;
import farm.nz.model.Game;
import farm.nz.model.Paddock;

public class StoreCropTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "Crop", "Price", "Sell Price", "Days to mature", "On Farm", "" };
	private static final String BUY = "Buy";
	private List<Crop> crops;
	private Game game;

	public StoreCropTableModel() {
		crops = new ArrayList<Crop>();
	}

	public StoreCropTableModel(List<Crop> crops, Game game) {
		this.crops = crops;
		this.game = game;
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
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

	public Crop getCrop(int row) {
		return crops.get(row);
	}

	@Override
	public int getRowCount() {
		return crops.size();
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
		case 4:

			int count = 0;
			for (Paddock p : game.getFarm().getPaddocks()) {
				if (p.hasCrop() && p.getCrop().getType() == crop.getType()) {
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