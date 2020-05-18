package farm.nz.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import farm.nz.model.StoreItem;

public class FarmPanel extends JPanel {

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private int indexCol;
		private int indexRow;
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

		public void doAnimalAction(Animal animal) {
			if (!game.hasActions()) {
				this.showInsufficientActions();
			} else {
				if (indexCol == 4) {
					animal.setHappy(animal.getHappy() + 2);
					game.incrementActionCount();
				}
				animalTable.repaint();
			}
		}

		public void doCropAction(Crop crop) {
			if (!game.hasActions()) {
				this.showInsufficientActions();
			} else {
				if (indexCol == 4) { // water
					if (null == crop) {
						this.showNoCropActions();
					} else if (crop.getMaturity() != 0) {
						crop.setMaturity(crop.getMaturity() - 1);
						game.incrementActionCount();
					}
				} else if (indexCol == 6) { // harvest
					if (null == crop) {
						this.showNoCropActions();
					} else if (crop.isMature(game)) {
						game.setAccount(game.getAccount() + crop.getSalePrice());
						Paddock paddock = game.getFarm().getPaddocks().get(indexRow);
						paddock.setCrop(null);
						game.incrementActionCount();
					}
				}
				paddockTable.repaint();
			}
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				if (model instanceof FarmAnimalTableModel) {
					Animal animal = game.getFarm().getAnimals().get(indexRow);
					this.doAnimalAction(animal);
				} else if (model instanceof FarmPaddockTableModel) {
					Crop crop = game.getFarm().getPaddocks().get(indexRow).getCrop();
					this.doCropAction(crop);
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
			indexRow = row;
			indexCol = column;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public void showInsufficientActions() {
			JOptionPane.showMessageDialog(button,
					"There are no more actions left today.\n\nMove to next day (File > Go to next day)",
					"No daily actions", JOptionPane.ERROR_MESSAGE);
		}

		public void showNoCropActions() {
			JOptionPane.showMessageDialog(button,
					"There is no crop planted in this paddock. \n\nVisit the General Store to buy/plant crops.",
					"No crop planted", JOptionPane.ERROR_MESSAGE);
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

	class ComboBoxEditor extends DefaultCellEditor {
		protected JComboBox<String> comboButton;
		private int rowSelected;
		private boolean isPushed;
		private String label;
		private String type;
		private TableModel model;

		public ComboBoxEditor(JComboBox<String> comboBox, String type) {
			super(comboBox);
			this.type = type;
			comboButton = new JComboBox<String>();
			comboButton.setOpaque(true);
			comboButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				if (!game.hasActions()) {
					this.showInsufficientActions();
				} else {
					if (model instanceof FarmAnimalTableModel) {
						Animal animal = game.getFarm().getAnimals().get(rowSelected);
						this.doAction(animal, ANIMAL);
					} else if (model instanceof FarmPaddockTableModel) {
						Crop crop = game.getFarm().getPaddocks().get(rowSelected).getCrop();
						if (null != crop) {
							this.doAction(crop, CROP);
						}

					}
				}
			}
			isPushed = false;
			return new String(label);
		}

		public void doAction(StoreItem storeItem, String type) {
			Item item = null;
			String selected = comboButton.getSelectedItem().toString();

			for (Item i : game.getFarm().getItems()) {
				if (selected.equalsIgnoreCase(i.getType().getDisplay())) {
					item = i;
					if (type.equalsIgnoreCase(CROP)) {
						Crop crop = (Crop) storeItem;
						crop.setMaturity(crop.getMaturity() - i.getBonus());
						paddockTable.repaint();
					} else if (type.equalsIgnoreCase(ANIMAL)) {
						Animal animal = (Animal) storeItem;
						animal.setHealth(animal.getHealth() + i.getBonus());
						animalTable.repaint();
					}
					break;
				}
			}
			game.getFarm().getItems().remove(item);
			game.incrementActionCount();
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				comboButton.setForeground(table.getSelectionForeground());
				comboButton.setBackground(table.getSelectionBackground());
			} else {
				comboButton.setForeground(table.getForeground());
				comboButton.setBackground(table.getBackground());
			}
			model = table.getModel();
			rowSelected = row;
			label = (value == null) ? "" : value.toString();
			DefaultComboBoxModel<String> dmodel = new DefaultComboBoxModel<String>();
			for (Item i : game.getFarm().getItems()) {
				if (type.equalsIgnoreCase(CROP) && i.isCrop()) {
					dmodel.addElement(i.getType().getDisplay());
				} else if (type.equalsIgnoreCase(ANIMAL) && i.isAnimal()) {
					dmodel.addElement(i.getType().getDisplay());
				}
			}
			if (dmodel.getSize() == 0) {
				dmodel.addElement("No items");
			}
			comboButton.setModel(dmodel);
			isPushed = true;
			return comboButton;
		}

		public void showInsufficientActions() {
			JOptionPane.showMessageDialog(comboButton, "There are no more actions left today.\nMove to next day (menu)",
					"No daily actions", JOptionPane.ERROR_MESSAGE);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}

	class FarmModelListener implements PropertyChangeListener {
		private JPanel app;

		public FarmModelListener(JPanel app) {
			super();
			this.app = app;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			switch (evt.getPropertyName()) {
			case Farm.ANIMAL:
				animalTable.revalidate();
			case Farm.PADDOCK:
				paddockTable.revalidate();
			case Farm.ITEM:
				itemTable.revalidate();
			case Game.DAY:
				paddockTable.repaint();
				animalTable.repaint();
			}
		}
	}

	private static final String CROP = "crop";
	private static final String ANIMAL = "animal";
	private static final long serialVersionUID = 1L;
	private JTable animalTable;
	private Game game;
	private JTable itemTable;
	private JTable paddockTable;
	protected String[] itemColumnToolTips = { null, "Crop bonus reduces days to harvest, Animal bonus adds to health",
			null };

	public FarmPanel(Game game) {
		this.game = game;
		initialise();
	}

	public void addButton(JTable table, int col) {
		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellEditor(new ButtonEditor(new JCheckBox()));
		column.setCellRenderer(new ButtonRenderer());
	}

	public void addComboBox(JTable table, int col, String type) {
		TableColumn column = table.getColumnModel().getColumn(col);
		column.setCellEditor(new ComboBoxEditor(new JComboBox<String>(), type));
		column.setCellRenderer(new ButtonRenderer());
	}

	private void initialise() {
		JPanel namePanel = new JPanel();
		namePanel.setBounds(500, 0, 30, 30);
		JLabel label = new JLabel("Farm");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(label);
		namePanel.setSize(50, 30);
		Farm farm = game.getFarm();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		farm.addPropertyChangeListener(Farm.PADDOCK, new FarmModelListener(this));
		farm.addPropertyChangeListener(Farm.ANIMAL, new FarmModelListener(this));
		farm.addPropertyChangeListener(Farm.ITEM, new FarmModelListener(this));
		game.addPropertyChangeListener(Game.DAY, new FarmModelListener(this));
		tabbedPane.setBounds(50, 20, 700, 400);

		FarmPaddockTableModel paddockTableModel = new FarmPaddockTableModel(game);
		paddockTable = new JTable(paddockTableModel);
		this.addButton(paddockTable, 4);
		this.addComboBox(paddockTable, 5, CROP);
		this.addButton(paddockTable, 6);
		JScrollPane paddockScroll = new JScrollPane(paddockTable);
		tabbedPane.add("Paddocks", paddockScroll);

		List<Animal> animals = farm.getAnimals();
		FarmAnimalTableModel animalTableModel = new FarmAnimalTableModel(animals);
		animalTable = new JTable(animalTableModel);
		this.addButton(animalTable, 4);
		this.addComboBox(animalTable, 5, ANIMAL);
		JScrollPane animalScroll = new JScrollPane(animalTable);
		tabbedPane.add("Animals", animalScroll);

		List<Item> items = farm.getItems();
		FarmItemTableModel itemTableModel = new FarmItemTableModel(items);
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
		JScrollPane itemScroll = new JScrollPane(itemTable);
		tabbedPane.add("Farm Supplies", itemScroll);

		tabbedPane.add("Maintenance", new MaintenancePanel(game));

		this.setLayout(null);
		this.add(namePanel);
		this.add(tabbedPane);

	}

}
