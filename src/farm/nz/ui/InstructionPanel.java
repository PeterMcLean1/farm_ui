package farm.nz.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InstructionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
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

		JLabel lblNewLabel = new JLabel("Instructions");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridheight = 2;
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 2;
		add(textPane, gbc_textPane);
		textPane.setText("Your farming adventure has started!\n\nYou are a farmer "
				+ "starting out on your brand new farm with a set amount of money, ready to fill it "
				+ "with crops and animals which can be purchased from the General Store.\n\nThere are"
				+ " a number of options/actions which you can select in the General Store or on the farm."
				+ "\n\nUse the menu above to move between the farm and the General Store\n\n"
				+ "Any option selected other than those in the General Store constitute an “action”.\n\nThe farmer may only perform"
				+ " a maximum of two actions per day. There is an option in the menu to move to the next day "
				+ "which will replenish your actions.\n\nThe aim of the game is to make as much money as possible in the allowed time.\n\nGood luck!");
		textPane.setEditable(false);
	}

}
