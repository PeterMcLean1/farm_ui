package farm.nz.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Farm;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Paddock;
import farm.nz.model.Store;
import farm.nz.model.StoreItem;

public class StorePanel extends JPanel {
	private static final Store store = new Store();

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Game game;

	public StorePanel(Game game) {
		initialise(game);
	}

	private void initialise(Game game) {
		this.game = game;

		tabbedPane.setBounds(50, 20, 700, 400);

		List<Crop> crops = store.getCropList();
		StoreCropTableModel cropTableModel = new StoreCropTableModel(crops);
		JTable cropTable = new JTable(cropTableModel);
		this.addButton(cropTable, 5);
		JScrollPane cropScroll = new JScrollPane(cropTable);

		tabbedPane.add("Crops", cropScroll);

		List<Animal> animals = store.getAnimalList();
		StoreAnimalTableModel animalTableModel = new StoreAnimalTableModel(animals);
		JTable animalTable = new JTable(animalTableModel);
		this.addButton(animalTable, 4);
		JScrollPane animalScroll = new JScrollPane(animalTable);

		tabbedPane.add("Animals", animalScroll);

		List<Item> items = store.getItemList();
		StoreItemTableModel itemTableModel = new StoreItemTableModel(items);
		JTable itemTable = new JTable(itemTableModel);
		this.addButton(itemTable, 5);
		JScrollPane itemScroll = new JScrollPane(itemTable);

		tabbedPane.add("Farm Supplies", itemScroll);

		this.setLayout(null);
		this.add(tabbedPane);

	}

	public void addButton(JTable table, int col) {
		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellEditor(new ButtonEditor(new JCheckBox()));
		column.setCellRenderer(new ButtonRenderer());

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

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;
		private int index;
		private TableModel model;

		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			System.out.println(row);
			model = table.getModel();
			index = row;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				StoreItem storeItem = null;

				if (model instanceof StoreAnimalTableModel) {
					StoreAnimalTableModel amodel = (StoreAnimalTableModel) model;
					storeItem = amodel.getAnimal(index);
				} else if (model instanceof StoreCropTableModel) {
					StoreCropTableModel cmodel = (StoreCropTableModel) model;
					storeItem = cmodel.getCrop(index);
				} else if (model instanceof StoreItemTableModel) {
					StoreItemTableModel imodel = (StoreItemTableModel) model;
					storeItem = imodel.getItem(index);
				}
				this.purchase(storeItem);
			}
			isPushed = false;
			return new String(label);
		}

		public void purchase(StoreItem storeItem) {
			Farm farm = game.getFarm();

			if (storeItem instanceof Animal) {
				Animal animal = (Animal) storeItem;
				if (animal.getPurchasePrice() <= game.getAccount()) {
					if (confirmPurchase(animal.getType().getDisplay(), animal.getPurchasePrice())) {
						animal.setHappy(animal.getHappy() + farm.getType().getAnimalBonus());
						farm.addAnimal(animal);
						game.setAccount(game.getAccount() - animal.getPurchasePrice());
					}
				} else {
					this.showInsufficientFunds();
				}

			} else if (storeItem instanceof Item) {
				Item item = (Item) storeItem;
				if (item.getPurchasePrice() <= game.getAccount()) {
					if (confirmPurchase(item.getType().getDisplay(), item.getPurchasePrice())) {
						farm.addItem(item);
						game.setAccount(game.getAccount() - item.getPurchasePrice());
					}
				} else {
					this.showInsufficientFunds();
				}
			} else if (storeItem instanceof Crop) {
				Crop crop = (Crop) storeItem;
				boolean hasSpace = false;
				// check if there is an empty paddock
				for (Paddock p : farm.getPaddocks()) {
					if (!p.hasCrop()) {
						hasSpace = true;
						break;
					}
				}
				// if empty paddock
				if (hasSpace) {
					if (crop.getPurchasePrice() <= game.getAccount()) {
						if (confirmPurchase(crop.getType().getDisplay(), crop.getPurchasePrice())) {
							for (Paddock p : farm.getPaddocks()) {
								// put crop in first empty paddock
								if (!p.hasCrop()) {
									p.setCrop(crop);
									break;
								}
							}
							game.setAccount(game.getAccount() - crop.getPurchasePrice());
						}
					} else {
						this.showInsufficientFunds();
					}

				} else {
					JOptionPane.showMessageDialog(button, "Not enough paddocks!", "Insufficient space for crop",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		}

		public void showInsufficientFunds() {
			JOptionPane.showMessageDialog(button, "Not enough money!", "Insufficient funds", JOptionPane.ERROR_MESSAGE);
		}

		public boolean confirmPurchase(String type, int price) {
			String message = "Buy " + type + " for $" + price + "?";
			int input = JOptionPane.showConfirmDialog(button, message, "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);// 0=ok, 2=cancel
			return (input == 0);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

}
