package farm.nz.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import javax.swing.table.JTableHeader;
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

public class StorePanel extends JPanel {

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private int index;
		private boolean isPushed;
		private String label;
		private TableModel model;

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

		public boolean confirmPurchase(String type, int price) {
			String message = "Buy " + type + " for $" + price + "?";
			int input = JOptionPane.showConfirmDialog(button, message, "", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);// 0=ok, 2=cancel
			return (input == 0);
		}

		public void doAnimalPurchase(Animal animal) {
			if (animal.getPurchasePrice() <= game.getAccount()) {
				if (confirmPurchase(animal.getType().getDisplay(), animal.getPurchasePrice())) {
					try {
						Farm farm = game.getFarm();
						animal = (Animal) animal.clone();
						animal.setHappy(animal.getHappy() + farm.getType().getAnimalBonus());
						farm.addAnimal(animal);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					game.setAccount(game.getAccount() - animal.getPurchasePrice());
				}
			} else {
				this.showInsufficientFunds();
			}
			animalTable.repaint();
		}

		public void doCropPurchase(Crop crop) {
			Farm farm = game.getFarm();
			boolean hasSpace = false;
			// first check if there is an empty paddock
			for (Paddock p : farm.getPaddocks()) {
				if (!p.hasCrop()) {
					hasSpace = true;
					break;
				}
			}
			// if there is an empty paddock
			if (hasSpace) {
				// check sufficient funds to purchase
				if (crop.getPurchasePrice() <= game.getAccount()) {
					if (confirmPurchase(crop.getType().getDisplay(), crop.getPurchasePrice())) {
						for (Paddock p : farm.getPaddocks()) {
							// put crop in first empty paddock found
							if (!p.hasCrop()) {
								try {
									crop = (Crop) crop.clone();
									crop.setDayPlanted(game.getCurrentDay());
								} catch (CloneNotSupportedException e) {
									e.printStackTrace();
								}
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
			cropTable.repaint();

		}

		public void doItemPurchase(Item item) {
			if (item.getPurchasePrice() <= game.getAccount()) {
				if (confirmPurchase(item.getType().getDisplay(), item.getPurchasePrice())) {
					try {
						Farm farm = game.getFarm();
						item = (Item) item.clone();
						farm.addItem(item);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}

					game.setAccount(game.getAccount() - item.getPurchasePrice());
				}
			} else {
				this.showInsufficientFunds();
			}
			itemTable.repaint();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}

		public Object getCellEditorValue() {
			if (isPushed) {

				if (model instanceof StoreAnimalTableModel) {
					StoreAnimalTableModel amodel = (StoreAnimalTableModel) model;
					Animal animal = amodel.getAnimal(index);
					this.doAnimalPurchase(animal);
				} else if (model instanceof StoreCropTableModel) {
					StoreCropTableModel cmodel = (StoreCropTableModel) model;
					Crop crop = cmodel.getCrop(index);
					this.doCropPurchase(crop);
				} else if (model instanceof StoreItemTableModel) {
					StoreItemTableModel imodel = (StoreItemTableModel) model;
					Item item = imodel.getItem(index);
					this.doItemPurchase(item);
				}
			}
			isPushed = false;
			return new String(label);
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
			model = table.getModel();
			index = row;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public void showInsufficientFunds() {
			JOptionPane.showMessageDialog(button, "Not enough money!", "Insufficient funds", JOptionPane.ERROR_MESSAGE);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
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

	private Game game;
	private JTable cropTable;
	private JTable animalTable;
	private JTable itemTable;
	protected String[] animalColumnToolTips = { null, null,
			"Base amount of money provided by the animal at the end of the day",
			"How many of this animal you have on your farm" };
	protected String[] itemColumnToolTips = { null, null,
			"Crop bonus reduces days to harvest, Animal bonus adds to health", "What you can use the item on", null };
	protected String[] cropColumnToolTips = { null, null, "How much you can sell the crop for when harvested", null,
			null };

	public StorePanel(Game game) {
		initialise(game);
	}

	public void addButton(JTable table, int col) {
		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellEditor(new ButtonEditor(new JCheckBox()));
		column.setCellRenderer(new ButtonRenderer());

	}

	private void initialise(Game game) {
		this.game = game;
		Store store = new Store();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		tabbedPane.setBounds(50, 20, 700, 400);

		List<Crop> crops = store.getCropList();
		StoreCropTableModel cropTableModel = new StoreCropTableModel(crops, game);
		cropTable = new JTable(cropTableModel) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					public String getToolTipText(MouseEvent e) {
						String tip = null;
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return cropColumnToolTips[realIndex];
					}
				};
			}
		};
		this.addButton(cropTable, 5);
		JScrollPane cropScroll = new JScrollPane(cropTable);

		tabbedPane.add("Crops", cropScroll);

		List<Animal> animals = store.getAnimalList();
		StoreAnimalTableModel animalTableModel = new StoreAnimalTableModel(animals, game);
		animalTable = new JTable(animalTableModel) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					public String getToolTipText(MouseEvent e) {
						String tip = null;
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return animalColumnToolTips[realIndex];
					}
				};
			}
		};

		this.addButton(animalTable, 4);
		JScrollPane animalScroll = new JScrollPane(animalTable);

		tabbedPane.add("Animals", animalScroll);

		List<Item> items = store.getItemList();
		StoreItemTableModel itemTableModel = new StoreItemTableModel(items, game);
		itemTable = new JTable(itemTableModel) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					public String getToolTipText(MouseEvent e) {
						String tip = null;
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return itemColumnToolTips[realIndex];
					}
				};
			}
		};
		this.addButton(itemTable, 5);
		JScrollPane itemScroll = new JScrollPane(itemTable);

		tabbedPane.add("Farm Supplies", itemScroll);

		this.setLayout(null);
		this.add(tabbedPane);

	}

}
