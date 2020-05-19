package farm.nz.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InstructionPanel extends JPanel {

	public InstructionPanel() {
		initialise();
	}

	private void initialise() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 121, 164, 121 };
		gridBagLayout.rowHeights = new int[] { 14, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		final JLabel lblNewLabel = new JLabel("Instructions");
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbcLblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbcLblNewLabel.gridx = 1;
		gbcLblNewLabel.gridy = 1;
		add(lblNewLabel, gbcLblNewLabel);

		final JTextPane textPane = new JTextPane();
		GridBagConstraints gbcTextPane = new GridBagConstraints();
		gbcTextPane.gridheight = 2;
		gbcTextPane.fill = GridBagConstraints.BOTH;
		gbcTextPane.gridx = 1;
		gbcTextPane.gridy = 2;
		add(textPane, gbcTextPane);
		textPane.setText("The aim of the game is to make as much money as possible in the allocated time."
				+ "\n\nYou are a farmer starting out on a new farm with a set amount of money. You need to fill it "
				+ "with crops and animals purchased from the General Store to create income.\n\n- Animals give you a daily income "
				+ "based on their health and happiness.\n- Crops take time to grow and are then sold at a profit once mature (harvested). "
				+ "\n\n- You can increase the number paddocks, and therefore crops you can plant, by improving the land. "
				+ "\n- You can increase animal happiness by improving your barn. "
				+ "\n\nAt the General Store you purchase animals, crops, and farm supplies."
				+ "\nThe number of actions you can perform in the General Store are unlimited."
				+ "\n\nThe farm supplies can be used on animals to improve their health, "
				+ "or on crops to speed their growth."
				+ "\nThe number of actions you can perform on the Farm are limited to two (2) each day. "
				+ "\nIn order to get more daily actions you must move to the next day (File > Go to next day)."
				+ "\n\nUse the menu above to move between your Farm and the General Store." + "\n\nGood luck!");
		textPane.setEditable(false);
	}

}
