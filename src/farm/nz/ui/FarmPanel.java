package farm.nz.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import farm.nz.model.StoreItem;

public class FarmPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField farmerNameField;
	private JTextField farmerAgeField;
	private JTextField farmNameField;

	private JLabel farmNameValidationLabel = new JLabel("");

	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Game game;
	private JTable animalTable;
	private JTable paddockTable;
	private JTable itemTable;

	/**
	 * Create the panel.
	 */
	public FarmPanel(Game game) {
		initialise(game);
		this.game = game;
	}

	private void initialise(Game game) {
		Farm farm = game.getFarm();
		farm.addPropertyChangeListener(Farm.PADDOCK, new FarmModelListener(this));
		farm.addPropertyChangeListener(Farm.ANIMAL, new FarmModelListener(this));
		farm.addPropertyChangeListener(Farm.ITEM, new FarmModelListener(this));
		tabbedPane.setBounds(50, 20, 700, 400);

		// TODO add content to paddockPanel
		// a list of paddocks with column including crop and column for actions
		// (harvest) if ready, otherwise days to ready.

		List<Paddock> paddocks = farm.getPaddocks();
		FarmPaddockTableModel paddockTableModel = new FarmPaddockTableModel(paddocks);
		paddockTable = new JTable(paddockTableModel);
		this.addButton(paddockTable, 4);
		this.addButton(paddockTable, 5);
		this.addButton(paddockTable, 6);
		JScrollPane paddockScroll = new JScrollPane(paddockTable);

		tabbedPane.add("Paddocks", paddockScroll);

		// TODO add content to animalPanel
		// a list of animals with actions (play with, use item). Item pop up modal
		// window to select item (or cancel)

		List<Animal> animals = farm.getAnimals();
		FarmAnimalTableModel animalTableModel = new FarmAnimalTableModel(animals);
		animalTable = new JTable(animalTableModel);
		this.addButton(animalTable, 4);
		this.addButton(animalTable, 5);
		JScrollPane animalScroll = new JScrollPane(animalTable);
		tabbedPane.add("Animals", animalScroll);

		// TODO add content to itemPanel
		// a list of items but no actions
		List<Item> items = farm.getItems();
		FarmItemTableModel itemTableModel = new FarmItemTableModel(items);
		itemTable = new JTable(itemTableModel);
		JScrollPane itemScroll = new JScrollPane(itemTable);
		tabbedPane.add("Items", itemScroll);

		this.setLayout(null);
		this.add(tabbedPane);

	}

	public void addButton(JTable table, int col) {
		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellEditor(new ButtonEditor(new JCheckBox()));
		column.setCellRenderer(new ButtonRenderer());

	}

	public void refreshPanel() {
		itemTable.revalidate();
		paddockTable.revalidate();

		animalTable.revalidate();

	}

	class FarmModelListener implements PropertyChangeListener {
		private FarmPanel app;

		public FarmModelListener(FarmPanel app) {
			super();
			this.app = app;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			app.refreshPanel();
		}
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
