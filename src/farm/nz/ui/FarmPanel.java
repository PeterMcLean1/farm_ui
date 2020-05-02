package farm.nz.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class FarmPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField farmerNameField;
	private JTextField farmerAgeField;
	private JTextField farmNameField;

	private JLabel farmNameValidationLabel = new JLabel("");

	/**
	 * Create the panel.
	 */
	public FarmPanel() {
		initialise();
	}

	private void initialise() {
		JPanel cropPanel = new JPanel();
		JPanel animalPanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(50, 50, 800, 500);
		tabbedPane.add("Crops", cropPanel);
		tabbedPane.add("Animals", animalPanel);
		tabbedPane.add("Farm Supplies", itemPanel);

		this.setLayout(null);
		this.add(tabbedPane);

	}

}
