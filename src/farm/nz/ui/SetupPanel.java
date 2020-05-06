package farm.nz.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import farm.nz.FarmUIApplication;
import farm.nz.model.Game;
import farm.nz.type.FarmType;

public class SetupPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField farmerNameField;
	private JTextField farmerAgeField;
	private JTextField farmNameField;

	private JLabel farmNameValidationLabel = new JLabel("");

	/**
	 * Create the panel.
	 */
	public SetupPanel(FarmUIApplication jframe, Game game) {
		initialise(game);
	}

	private void initialise(Game game) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 118, 22, 59, 55, 31, 29, 30, 22, 0 };
		gridBagLayout.rowHeights = new int[] { 22, 20, 22, 31, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel_9 = new JLabel("Please enter game parameters");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridwidth = 4;
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 0;
		this.add(lblNewLabel_9, gbc_lblNewLabel_9);

		JLabel lblNewLabel_6 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 5;
		gbc_lblNewLabel_6.gridy = 0;
		this.add(lblNewLabel_6, gbc_lblNewLabel_6);

		farmNameValidationLabel.setForeground(Color.RED);
		farmNameValidationLabel.setText("3-15 chars, no special chars");
		GridBagConstraints gbc_farmNameValidationLabel = new GridBagConstraints();
		gbc_farmNameValidationLabel.anchor = GridBagConstraints.EAST;
		gbc_farmNameValidationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_farmNameValidationLabel.gridwidth = 2;
		gbc_farmNameValidationLabel.gridx = 0;
		gbc_farmNameValidationLabel.gridy = 1;
		this.add(farmNameValidationLabel, gbc_farmNameValidationLabel);

		// farmer age components
		JLabel lblNewLabel_1 = new JLabel("Farmer age:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 1;
		this.add(lblNewLabel_1, gbc_lblNewLabel_1);

		farmerAgeField = new JTextField();
		GridBagConstraints gbc_farmerAgeField = new GridBagConstraints();
		gbc_farmerAgeField.anchor = GridBagConstraints.NORTHEAST;
		gbc_farmerAgeField.insets = new Insets(0, 0, 5, 5);
		gbc_farmerAgeField.gridwidth = 2;
		gbc_farmerAgeField.gridx = 3;
		gbc_farmerAgeField.gridy = 1;
		this.add(farmerAgeField, gbc_farmerAgeField);
		farmerAgeField.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("1-100");
		lblNewLabel_7.setForeground(Color.RED);
		lblNewLabel_7.setEnabled(false);
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 5;
		gbc_lblNewLabel_7.gridy = 1;
		this.add(lblNewLabel_7, gbc_lblNewLabel_7);

		// farm name components
		JLabel lblNewLabel_2 = new JLabel("Farm name:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 6;
		gbc_lblNewLabel_2.gridy = 1;
		this.add(lblNewLabel_2, gbc_lblNewLabel_2);

		farmNameField = new JTextField();
		GridBagConstraints gbc_farmNameField = new GridBagConstraints();
		gbc_farmNameField.anchor = GridBagConstraints.EAST;
		gbc_farmNameField.insets = new Insets(0, 0, 5, 5);
		gbc_farmNameField.gridx = 0;
		gbc_farmNameField.gridy = 2;
		this.add(farmNameField, gbc_farmNameField);
		farmNameField.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Cannot be empty");
		lblNewLabel_8.setForeground(Color.RED);
		lblNewLabel_8.setEnabled(false);
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridwidth = 2;
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 2;
		this.add(lblNewLabel_8, gbc_lblNewLabel_8);

		// farm type components
		JLabel lblNewLabel_3 = new JLabel("Farm type:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 2;
		this.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JComboBox<FarmType> comboBox = new JComboBox<FarmType>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 2;
		this.add(comboBox, gbc_comboBox);
		comboBox.addItem(FarmType.FLAT);
		comboBox.addItem(FarmType.RIVER);
		comboBox.addItem(FarmType.FOREST);
		comboBox.addItem(FarmType.HILL);

		// days to play components
		JLabel lblNewLabel_4 = new JLabel("Days to play:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.gridx = 5;
		gbc_lblNewLabel_4.gridy = 2;
		this.add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 7;
		gbc_lblNewLabel_5.gridy = 2;
		this.add(lblNewLabel_5, gbc_lblNewLabel_5);

		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblNewLabel_5.setText(slider.getValue() + " days");
			}
		});
		slider.setToolTipText("Days to play");
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(5);
		slider.setMaximum(10);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.anchor = GridBagConstraints.NORTHEAST;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridwidth = 4;
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 3;
		this.add(slider, gbc_slider);

		JButton btnNewButton = new JButton("NEXT >");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnNewButton.setEnabled(true);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 3;
		this.add(btnNewButton, gbc_btnNewButton);

		farmerNameField = new JTextField();
		farmerNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String input = farmerNameField.getText();
				lblNewLabel_6.setText(input.length() + "");

			}
		});

		// farmer name components
		JLabel lblNewLabel = new JLabel("Farmer name:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		this.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_farmerNameField = new GridBagConstraints();
		gbc_farmerNameField.anchor = GridBagConstraints.NORTHWEST;
		gbc_farmerNameField.insets = new Insets(0, 0, 0, 5);
		gbc_farmerNameField.gridwidth = 2;
		gbc_farmerNameField.gridx = 2;
		gbc_farmerNameField.gridy = 4;
		this.add(farmerNameField, gbc_farmerNameField);
		farmerNameField.setColumns(10);
		farmerNameField.setInputVerifier(new FarmerNameVerifier());

	}

	private class FarmerNameVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			if (tf.getText().matches("[A-Za-z0-9]{3,15}")) {
				farmNameValidationLabel.setEnabled(false);
				return true;
			} else {
				farmNameValidationLabel.setEnabled(true);
				return false;
			}

		}
	}

}
