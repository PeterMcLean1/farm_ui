package farm.nz.ui;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import farm.nz.model.Animal;
import farm.nz.model.Farm;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Paddock;

public class FarmPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField farmerNameField;
	private JTextField farmerAgeField;
	private JTextField farmNameField;

	private JLabel farmNameValidationLabel = new JLabel("");

	/**
	 * Create the panel.
	 */
	public FarmPanel(Game game) {
		initialise(game);
	}

	private void initialise(Game game) {
		Farm farm = game.getFarm();
		JPanel paddockPanel = new JPanel();
		JPanel animalPanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(50, 50, 805, 500);

		// TODO add content to paddockPanel
		// a list of paddocks with column including crop and column for actions
		// (harvest) if ready, otherwise days to ready.

		String col[] = { "Paddock ID", "Crop", "Actions" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);

		List<Paddock> paddocks = farm.getPaddocks();

		for (Paddock p : paddocks) {

			if (p.hasCrop()) {
				Object[] objs = { p.getPaddockID(), p.getCrop().getType().getDisplay(), 0 };
				tableModel.addRow(objs);
			} else {
				Object[] objs = { p.getPaddockID(), "No crop - purchase from store", 0 };
				tableModel.addRow(objs);
			}

		}

		JTable jt = new JTable(tableModel);
		jt.setBounds(0, 0, 800, 495);
		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(0, 0, 800, 495);
		paddockPanel.add(sp);

		tabbedPane.add("Paddocks", paddockPanel);

		// TODO add content to animalPanel
		// a list of animals with actions (play with, use item). Item pop up modal
		// window to select item (or cancel)

		String col2[] = { "Animal", "Happiness", "Health", "Actions" };
		DefaultTableModel tableModel2 = new DefaultTableModel(col2, 0);

		List<Animal> animals = farm.getAnimals();

		for (Animal a : animals) {

			Object[] objs = { a.getType().getDisplay(), a.getHappy(), a.getHealth(), 0 };
			tableModel.addRow(objs);

		}

		JTable jt2 = new JTable(tableModel2);
		jt2.setBounds(0, 0, 800, 495);
		JScrollPane sp2 = new JScrollPane(jt2);
		sp2.setBounds(0, 0, 800, 495);
		animalPanel.add(sp2);

		tabbedPane.add("Animals", animalPanel);

		// TODO add content to itemPanel
		// a list of items but no actions
		String col3[] = { "Item", "Animal use", "Crop use", "Skill" };
		DefaultTableModel tableModel3 = new DefaultTableModel(col3, 0);

		List<Item> items = farm.getItems();

		for (Item item : items) {
			String isAnimal = item.isAnimal() ? "yes" : "no";
			String isCrop = item.isCrop() ? "yes" : "no";
			String isSkill = item.isSkill() ? "yes" : "no";

			Object[] objs3 = { item.getType().getDisplay(), isAnimal, isCrop, isSkill };
			tableModel3.addRow(objs3);

		}

		JTable jt3 = new JTable(tableModel3);
		jt3.setBounds(0, 0, 800, 495);
		JScrollPane sp3 = new JScrollPane(jt3);
		sp3.setBounds(0, 0, 800, 495);
		itemPanel.add(sp3);
		tabbedPane.add("Farm Supplies", itemPanel);

		this.setLayout(null);
		this.add(tabbedPane);

	}
}
