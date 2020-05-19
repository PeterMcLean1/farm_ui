package farm.nz.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

import farm.nz.FarmUiApplication;
import farm.nz.model.Game;
import farm.nz.type.FarmType;

public class SetupPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel farmerNameValidationLabel = new JLabel("");
	private JLabel farmerAgeValidationLabel = new JLabel("");
	private JLabel farmNameValidationLabel = new JLabel("");
	private FarmUiApplication gameFrame;
	private Game game;
	private JButton startButton = new JButton("START >");

	/**
	 * 
	 * @param jframe
	 * @param game
	 */
	public SetupPanel(FarmUiApplication jframe, Game game) {
		this.gameFrame = jframe;
		this.game = game;
		initialise(game);
	}

	private void initialise(Game game) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 118, 118, 118, 118, 118, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 22, 0, 0, 20, 22, 0, 0, 31, 20, 20, 20, 20 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		GridBagConstraints gbcLblHeading = new GridBagConstraints();
		gbcLblHeading.anchor = GridBagConstraints.NORTHWEST;
		gbcLblHeading.insets = new Insets(0, 0, 5, 5);
		gbcLblHeading.gridwidth = 4;
		gbcLblHeading.gridx = 1;
		gbcLblHeading.gridy = 1;
		JLabel lblHeading = new JLabel("Please enter your starting options:");
		lblHeading.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.add(lblHeading, gbcLblHeading);

		// farmer name components
		GridBagConstraints gbcLblFarmerName = new GridBagConstraints();
		gbcLblFarmerName.anchor = GridBagConstraints.EAST;
		gbcLblFarmerName.fill = GridBagConstraints.VERTICAL;
		gbcLblFarmerName.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmerName.gridx = 1;
		gbcLblFarmerName.gridy = 3;
		JLabel lblFarmerName = new JLabel("Farmer name:");
		this.add(lblFarmerName, gbcLblFarmerName);

		GridBagConstraints gbcFldFarmerName = new GridBagConstraints();
		gbcFldFarmerName.fill = GridBagConstraints.HORIZONTAL;
		gbcFldFarmerName.anchor = GridBagConstraints.NORTH;
		gbcFldFarmerName.insets = new Insets(0, 0, 5, 5);
		// gbc_farmerNameField.gridwidth = 2;
		gbcFldFarmerName.gridx = 2;
		gbcFldFarmerName.gridy = 3;
		JTextField farmerNameField = new JTextField();
		farmerNameField.setColumns(10);
		farmerNameField.setInputVerifier(new FarmerNameVerifier());
		this.add(farmerNameField, gbcFldFarmerName);

		GridBagConstraints gbcLblFarmerNameCount = new GridBagConstraints();
		gbcLblFarmerNameCount.anchor = GridBagConstraints.WEST;
		gbcLblFarmerNameCount.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmerNameCount.gridx = 3;
		gbcLblFarmerNameCount.gridy = 3;
		JLabel lblFarmerNameCount = new JLabel("");
		this.add(lblFarmerNameCount, gbcLblFarmerNameCount);

		farmerNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblFarmerNameCount.setText(farmerNameField.getText().length() + "");
				farmerNameValidationLabel.setEnabled(!farmerNameField.isValid());
			}
		});

		farmerNameValidationLabel.setForeground(Color.RED);
		farmerNameValidationLabel.setText("3-15 letters, no special characters");
		GridBagConstraints gbcFarmNameValidationLabel = new GridBagConstraints();
		gbcFarmNameValidationLabel.anchor = GridBagConstraints.NORTHWEST;
		gbcFarmNameValidationLabel.insets = new Insets(0, 0, 5, 5);
		// gbc_farmNameValidationLabel.gridwidth = 2;
		gbcFarmNameValidationLabel.gridx = 2;
		gbcFarmNameValidationLabel.gridy = 4;
		this.add(farmerNameValidationLabel, gbcFarmNameValidationLabel);

		// farmer age components
		GridBagConstraints gbcLblFarmerAge = new GridBagConstraints();
		gbcLblFarmerAge.anchor = GridBagConstraints.EAST;
		gbcLblFarmerAge.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmerAge.gridx = 1;
		gbcLblFarmerAge.gridy = 5;
		JLabel lblFarmerAge = new JLabel("Farmer age:");
		this.add(lblFarmerAge, gbcLblFarmerAge);

		GridBagConstraints gbcFarmerAgeField = new GridBagConstraints();
		gbcFarmerAgeField.fill = GridBagConstraints.HORIZONTAL;
		gbcFarmerAgeField.anchor = GridBagConstraints.NORTH;
		gbcFarmerAgeField.insets = new Insets(0, 0, 5, 5);
		gbcFarmerAgeField.gridx = 2;
		gbcFarmerAgeField.gridy = 5;
		JTextField farmerAgeField = new JTextField();
		this.add(farmerAgeField, gbcFarmerAgeField);
		farmerAgeField.setColumns(10);
		farmerAgeField.setInputVerifier(new FarmerAgeVerifier());

		farmerAgeField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				farmerAgeValidationLabel.setEnabled(!farmerAgeField.isValid());
			}
		});

		farmerAgeValidationLabel = new JLabel("Number between 1-100");
		farmerAgeValidationLabel.setForeground(Color.RED);
		GridBagConstraints gbcLblFarmerAgeValidation = new GridBagConstraints();
		gbcLblFarmerAgeValidation.anchor = GridBagConstraints.NORTHWEST;
		gbcLblFarmerAgeValidation.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmerAgeValidation.gridx = 2;
		gbcLblFarmerAgeValidation.gridy = 6;
		this.add(farmerAgeValidationLabel, gbcLblFarmerAgeValidation);

		// farm name components
		GridBagConstraints gbcLblFarmName = new GridBagConstraints();
		gbcLblFarmName.anchor = GridBagConstraints.EAST;
		gbcLblFarmName.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmName.gridx = 1;
		gbcLblFarmName.gridy = 7;
		JLabel lblFarmName = new JLabel("Farm name:");
		this.add(lblFarmName, gbcLblFarmName);

		GridBagConstraints gbcFarmNameField = new GridBagConstraints();
		gbcFarmNameField.fill = GridBagConstraints.HORIZONTAL;
		gbcFarmNameField.insets = new Insets(0, 0, 5, 5);
		gbcFarmNameField.gridx = 2;
		gbcFarmNameField.gridy = 7;
		JTextField farmNameField = new JTextField();
		this.add(farmNameField, gbcFarmNameField);
		farmNameField.setColumns(10);
		farmNameField.setInputVerifier(new FarmNameVerifier());

		farmNameValidationLabel = new JLabel("1-25 letters and spaces only");
		farmNameValidationLabel.setForeground(Color.RED);
		GridBagConstraints gbcLblFarmNameValidation = new GridBagConstraints();
		gbcLblFarmNameValidation.anchor = GridBagConstraints.NORTHWEST;
		gbcLblFarmNameValidation.insets = new Insets(0, 0, 5, 5);
		// gbc_lblNewLabel_8.gridwidth = 2;
		gbcLblFarmNameValidation.gridx = 2;
		gbcLblFarmNameValidation.gridy = 8;
		this.add(farmNameValidationLabel, gbcLblFarmNameValidation);

		farmNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				farmNameValidationLabel.setEnabled(!farmNameField.isValid());
			}
		});

		// farm type components
		GridBagConstraints gbcLblFarmType = new GridBagConstraints();
		gbcLblFarmType.anchor = GridBagConstraints.EAST;
		gbcLblFarmType.insets = new Insets(0, 0, 5, 5);
		gbcLblFarmType.gridx = 1;
		gbcLblFarmType.gridy = 9;
		JLabel lblFarmType = new JLabel("Farm type:");
		this.add(lblFarmType, gbcLblFarmType);

		GridBagConstraints gbcLblDifficulty = new GridBagConstraints();
		gbcLblDifficulty.anchor = GridBagConstraints.WEST;
		gbcLblDifficulty.insets = new Insets(0, 0, 5, 5);
		gbcLblDifficulty.gridx = 3;
		gbcLblDifficulty.gridy = 9;
		JLabel lblDifficulty = new JLabel();
		this.add(lblDifficulty, gbcLblDifficulty);

		JComboBox<FarmType> comboBox = new JComboBox<FarmType>();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.SELECTED == e.getStateChange()) {
					FarmType type = (FarmType) e.getItem();
					game.getFarm().setType(type);
					game.setAccount(type.getStartMoney());
					lblDifficulty.setText(game.getFarm().getType().getDifficulty());
				}
			}
		});
		comboBox.addItem(FarmType.FLAT);
		comboBox.addItem(FarmType.HILL);
		comboBox.addItem(FarmType.RIVER);
		comboBox.addItem(FarmType.FOREST);

		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBox.anchor = GridBagConstraints.NORTH;
		gbcComboBox.insets = new Insets(0, 0, 5, 5);
		gbcComboBox.gridx = 2;
		gbcComboBox.gridy = 9;
		this.add(comboBox, gbcComboBox);

		// days to play components

		GridBagConstraints gbcLblDaysToPlay = new GridBagConstraints();
		gbcLblDaysToPlay.anchor = GridBagConstraints.EAST;
		gbcLblDaysToPlay.insets = new Insets(0, 0, 5, 5);
		// gbc_lblNewLabel_4.gridwidth = 2;
		gbcLblDaysToPlay.gridx = 1;
		gbcLblDaysToPlay.gridy = 10;
		JLabel lblDaysToPlay = new JLabel("Days to play:");
		this.add(lblDaysToPlay, gbcLblDaysToPlay);

		GridBagConstraints gbcLblDaySlider = new GridBagConstraints();
		gbcLblDaySlider.anchor = GridBagConstraints.WEST;
		gbcLblDaySlider.insets = new Insets(0, 0, 5, 0);
		gbcLblDaySlider.gridx = 3;
		gbcLblDaySlider.gridy = 10;
		JLabel lblDaySlider = new JLabel("");
		this.add(lblDaySlider, gbcLblDaySlider);

		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				game.setDaysToPlay(slider.getValue());
				lblDaySlider.setText(slider.getValue() + " days");
			}
		});

		slider.setToolTipText("Days to play");
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(5);
		slider.setMaximum(10);
		slider.setValue(5);
		GridBagConstraints gbcSlider = new GridBagConstraints();
		gbcSlider.fill = GridBagConstraints.HORIZONTAL;
		gbcSlider.insets = new Insets(0, 0, 5, 5);
		// gbc_slider.gridwidth = 4;
		gbcSlider.gridx = 2;
		gbcSlider.gridy = 10;
		this.add(slider, gbcSlider);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.showStartCard(FarmUiApplication.START_GAME);
			}
		});
		startButton.setEnabled(false);
		GridBagConstraints gbcStartButton = new GridBagConstraints();
		gbcStartButton.anchor = GridBagConstraints.WEST;
		gbcStartButton.insets = new Insets(0, 0, 5, 5);
		gbcStartButton.gridx = 4;
		gbcStartButton.gridy = 11;
		this.add(startButton, gbcStartButton);
	}

	private void startButtonChecker() {
		// check farmer name is valid
		// check farmer age is valid
		// check farm name is valid
		startButton.setEnabled(!game.getFarm().getFarmer().getName().isEmpty()
				&& game.getFarm().getFarmer().getAge() > 0 && !game.getFarm().getName().isEmpty());
	}

	private class FarmerNameVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			// letters only allowed
			if (tf.getText().matches("[A-Za-z]{3,15}")) {
				farmerNameValidationLabel.setEnabled(false);
				game.getFarm().getFarmer().setName(tf.getText());
				startButtonChecker();
				return true;
			} else {
				farmerNameValidationLabel.setEnabled(true);
				return false;
			}

		}
	}

	private class FarmerAgeVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			int value = 0;
			try {
				value = Integer.parseInt(tf.getText());
			} catch (NumberFormatException n) {
				// not a valid number format
				farmerAgeValidationLabel.setEnabled(true);
				return false;
			}
			if (0 <= value && 100 >= value) {
				farmerAgeValidationLabel.setEnabled(false);
				game.getFarm().getFarmer().setAge(value);
				startButtonChecker();
				return true;

			} else {
				farmerAgeValidationLabel.setEnabled(true);
				return false;
			}

		}
	}

	private class FarmNameVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField tf = (JTextField) input;
			// letters and spaces allowed
			if (tf.getText().matches("[A-Za-z ]{1,25}")) {
				farmNameValidationLabel.setEnabled(false);
				game.getFarm().setName(tf.getText());
				startButtonChecker();
				return true;
			} else {
				farmNameValidationLabel.setEnabled(true);
				return false;
			}

		}
	}

}
