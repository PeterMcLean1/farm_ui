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

import farm.nz.model.Animal;
import farm.nz.model.Crop;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Store;

public class StorePanel extends JPanel {
	private static final Store store = new Store();

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public StorePanel(Game game) {
		initialise(game);
	}

	private void initialise(Game game) {
		JPanel cropPanel = new JPanel();
		JPanel animalPanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(50, 50, 800, 500);

		// TODO list of crops to cropPanel
		List<Crop> crops = store.getCropList();
		CropTableModel cropTable = new CropTableModel(crops);

		JTable jt = new JTable(cropTable);
		jt.setBounds(0, 0, 800, 495);
		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(0, 0, 800, 495);
		cropPanel.add(sp);
		tabbedPane.add("Crops", cropPanel);

		// TODO list of animals to animalPanel

		List<Animal> animals = store.getAnimalList();
		AnimalTableModel animalTable = new AnimalTableModel(animals);

		JTable jt2 = new JTable(animalTable);
		jt2.getColumn("Buy").setCellRenderer(new ButtonRenderer());
		jt2.getColumn("Buy").setCellEditor(new ButtonEditor(new JCheckBox()));
		jt2.setBounds(0, 0, 800, 495);
		JScrollPane sp2 = new JScrollPane(jt2);
		sp2.setBounds(0, 0, 800, 495);
		animalPanel.add(sp2);
		tabbedPane.add("Animals", animalPanel);

		// TODO list of farm supplies to itemPanel
		List<Item> items = store.getItemList();
		ItemTableModel itemTable = new ItemTableModel(items);

		JTable jt3 = new JTable(itemTable);
		jt3.setBounds(0, 0, 800, 495);
		JScrollPane sp3 = new JScrollPane(jt3);
		sp3.setBounds(0, 0, 800, 495);
		itemPanel.add(sp3);
		tabbedPane.add("Farm Supplies", itemPanel);

		this.setLayout(null);
		this.add(tabbedPane);

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

	/**
	 * @version 1.0 11/09/98
	 */

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;

		private String label;
		private int index;

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
			index = row;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				//
				Animal animal = store.getAnimalList().get(index);
				//
				JOptionPane.showMessageDialog(button, label + animal.getType().getDisplay() + ": Ouch!");
				// System.out.println(label + ": Ouch!");
			}
			isPushed = false;
			return new String(label);
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
