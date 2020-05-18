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

import farm.nz.FarmUIApplication;
import farm.nz.model.Game;
import farm.nz.type.FarmType;

public class SetupPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel farmerNameValidationLabel = new JLabel("");
	private JLabel farmerAgeValidationLabel = new JLabel("");
	private JLabel farmNameValidationLabel = new JLabel("");
	private FarmUIApplication gameFrame;
	private Game game;
	private JButton startButton = new JButton("START >");

	public SetupPanel(FarmUIApplication jframe, Game game) {
		this.gameFrame = jframe;
		this.game = game;
		initialise(game);
	}

	private void initialise(Game game) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 118, 118, 118, 118, 118, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 22, 0, 0, 20, 22, 0, 0, 31, 20, 20, 20, 20 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0 };
		setLayout(gridBagLayout);

		JLabel lblHeading = new JLabel("Please enter your starting options:");
		lblHeading.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblHeading = new GridBagConstraints();
		gbc_lblHeading.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblHeading.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeading.gridwidth = 4;
		gbc_lblHeading.gridx = 1;
		gbc_lblHeading.gridy = 1;
		this.add(lblHeading, gbc_lblHeading);

		// farmer name components
		JLabel lblFarmerName = new JLabel("Farmer name:");
		GridBagConstraints gbc_lblFarmerName = new GridBagConstraints();
		gbc_lblFarmerName.anchor = GridBagConstraints.EAST;
		gbc_lblFarmerName.fill = GridBagConstraints.VERTICAL;
		gbc_lblFarmerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmerName.gridx = 1;
		gbc_lblFarmerName.gridy = 3;
		this.add(lblFarmerName, gbc_lblFarmerName);

		JTextField farmerNameField = new JTextField();
		GridBagConstraints gbc_fldFarmerName = new GridBagConstraints();
		gbc_fldFarmerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_fldFarmerName.anchor = GridBagConstraints.NORTH;
		gbc_fldFarmerName.insets = new Insets(0, 0, 5, 5);
		// gbc_farmerNameField.gridwidth = 2;
		gbc_fldFarmerName.gridx = 2;
		gbc_fldFarmerName.gridy = 3;
		this.add(farmerNameField, gbc_fldFarmerName);
		farmerNameField.setColumns(10);
		farmerNameField.setInputVerifier(new FarmerNameVerifier());

		JLabel lblFarmerNameCount = new JLabel("");
		GridBagConstraints gbc_lblFarmerNameCount = new GridBagConstraints();
		gbc_lblFarmerNameCount.anchor = GridBagConstraints.WEST;
		gbc_lblFarmerNameCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmerNameCount.gridx = 3;
		gbc_lblFarmerNameCount.gridy = 3;
		this.add(lblFarmerNameCount, gbc_lblFarmerNameCount);

		farmerNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblFarmerNameCount.setText(farmerNameField.getText().length() + "");
				farmerNameValidationLabel.setEnabled(!farmerNameField.isValid());
			}
		});

		farmerNameValidationLabel.setForeground(Color.RED);
		farmerNameValidationLabel.setText("3-15 letters, no special characters");
		GridBagConstraints gbc_farmNameValidationLabel = new GridBagConstraints();
		gbc_farmNameValidationLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_farmNameValidationLabel.insets = new Insets(0, 0, 5, 5);
		// gbc_farmNameValidationLabel.gridwidth = 2;
		gbc_farmNameValidationLabel.gridx = 2;
		gbc_farmNameValidationLabel.gridy = 4;
		this.add(farmerNameValidationLabel, gbc_farmNameValidationLabel);

		// farmer age components
		JLabel lblFarmerAge = new JLabel("Farmer age:");
		GridBagConstraints gbc_lblFarmerAge = new GridBagConstraints();
		gbc_lblFarmerAge.anchor = GridBagConstraints.EAST;
		gbc_lblFarmerAge.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmerAge.gridx = 1;
		gbc_lblFarmerAge.gridy = 5;
		this.add(lblFarmerAge, gbc_lblFarmerAge);

		JTextField farmerAgeField = new JTextField();
		GridBagConstraints gbc_farmerAgeField = new GridBagConstraints();
		gbc_farmerAgeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_farmerAgeField.anchor = GridBagConstraints.NORTH;
		gbc_farmerAgeField.insets = new Insets(0, 0, 5, 5);
		gbc_farmerAgeField.gridx = 2;
		gbc_farmerAgeField.gridy = 5;
		this.add(farmerAgeField, gbc_farmerAgeField);
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
		GridBagConstraints gbc_lblFarmerAgeValidation = new GridBagConstraints();
		gbc_lblFarmerAgeValidation.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFarmerAgeValidation.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmerAgeValidation.gridx = 2;
		gbc_lblFarmerAgeValidation.gridy = 6;
		this.add(farmerAgeValidationLabel, gbc_lblFarmerAgeValidation);

		// farm name components
		JLabel lblFarmName = new JLabel("Farm name:");
		GridBagConstraints gbc_lblFarmName = new GridBagConstraints();
		gbc_lblFarmName.anchor = GridBagConstraints.EAST;
		gbc_lblFarmName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmName.gridx = 1;
		gbc_lblFarmName.gridy = 7;
		this.add(lblFarmName, gbc_lblFarmName);

		JTextField farmNameField = new JTextField();
		GridBagConstraints gbc_farmNameField = new GridBagConstraints();
		gbc_farmNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_farmNameField.insets = new Insets(0, 0, 5, 5);
		gbc_farmNameField.gridx = 2;
		gbc_farmNameField.gridy = 7;
		this.add(farmNameField, gbc_farmNameField);
		farmNameField.setColumns(10);
		farmNameField.setInputVerifier(new FarmNameVerifier());

		farmNameValidationLabel = new JLabel("1-25 letters and spaces only");
		farmNameValidationLabel.setForeground(Color.RED);
		GridBagConstraints gbc_lblFarmNameValidation = new GridBagConstraints();
		gbc_lblFarmNameValidation.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFarmNameValidation.insets = new Insets(0, 0, 5, 5);
		// gbc_lblNewLabel_8.gridwidth = 2;
		gbc_lblFarmNameValidation.gridx = 2;
		gbc_lblFarmNameValidation.gridy = 8;
		this.add(farmNameValidationLabel, gbc_lblFarmNameValidation);

		farmNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				farmNameValidationLabel.setEnabled(!farmNameField.isValid());
			}
		});

		// farm type components
		JLabel lblFarmType = new JLabel("Farm type:");
		GridBagConstraints gbc_lblFarmType = new GridBagConstraints();
		gbc_lblFarmType.anchor = GridBagConstraints.EAST;
		gbc_lblFarmType.insets = new Insets(0, 0, 5, 5);
		gbc_lblFarmType.gridx = 1;
		gbc_lblFarmType.gridy = 9;
		this.add(lblFarmType, gbc_lblFarmType);

		JLabel lblDifficulty = new JLabel();
		GridBagConstraints gbc_lblDifficulty = new GridBagConstraints();
		gbc_lblDifficulty.anchor = GridBagConstraints.WEST;
		gbc_lblDifficulty.insets = new Insets(0, 0, 5, 5);
		gbc_lblDifficulty.gridx = 3;
		gbc_lblDifficulty.gridy = 9;
		this.add(lblDifficulty, gbc_lblDifficulty);

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

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 9;
		this.add(comboBox, gbc_comboBox);

		// days to play components
		JLabel lblDaysToPlay = new JLabel("Days to play:");
		GridBagConstraints gbc_lblDaysToPlay = new GridBagConstraints();
		gbc_lblDaysToPlay.anchor = GridBagConstraints.EAST;
		gbc_lblDaysToPlay.insets = new Insets(0, 0, 5, 5);
		// gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblDaysToPlay.gridx = 1;
		gbc_lblDaysToPlay.gridy = 10;
		this.add(lblDaysToPlay, gbc_lblDaysToPlay);

		JLabel lblDaySlider = new JLabel("");
		GridBagConstraints gbc_lblDaySlider = new GridBagConstraints();
		gbc_lblDaySlider.anchor = GridBagConstraints.WEST;
		gbc_lblDaySlider.insets = new Insets(0, 0, 5, 0);
		gbc_lblDaySlider.gridx = 3;
		gbc_lblDaySlider.gridy = 10;
		this.add(lblDaySlider, gbc_lblDaySlider);

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
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		// gbc_slider.gridwidth = 4;
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 10;
		this.add(slider, gbc_slider);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.showStartCard(FarmUIApplication.START_GAME);

			}
		});
		startButton.setEnabled(false);
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.anchor = GridBagConstraints.WEST;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 4;
		gbc_startButton.gridy = 11;
		this.add(startButton, gbc_startButton);

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
