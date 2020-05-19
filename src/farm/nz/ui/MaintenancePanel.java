package farm.nz.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import farm.nz.model.Animal;
import farm.nz.model.Farm;
import farm.nz.model.Game;
import farm.nz.model.Paddock;

public class MaintenancePanel extends JPanel {

	class ModelListener implements PropertyChangeListener {
		private MaintenancePanel app;

		public ModelListener(MaintenancePanel app) {
			super();
			this.app = app;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			app.checkAnimalButton();
			app.checkPaddockButton();
		}
	}

	private Game game;
	private JButton paddockButton = new JButton("Land improvements");
	private JButton barnButton = new JButton("Barn improvements");

	public MaintenancePanel(Game game) {
		initialise(game);
	}

	public void checkAnimalButton() {
		barnButton.setEnabled(game.getFarm().getAnimals().size() > 0);
	}

	public void checkPaddockButton() {
		paddockButton.setEnabled(
				game.getFarm().getType().getMaxPaddocks() > game.getFarm().getPaddocks().size());
	}

	private void initialise(Game game) {
		this.game = game;
		Farm farm = game.getFarm();
		farm.addPropertyChangeListener(Farm.ANIMAL, new ModelListener(this));
		farm.addPropertyChangeListener(Farm.PADDOCK, new ModelListener(this));
		for (Paddock p : farm.getPaddocks()) {
			p.addPropertyChangeListener(Farm.CROP, new ModelListener(this));
		}

		this.setBorder(new EmptyBorder(5, 5, 5, 0));

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 42, 42 };
		gbl.rowHeights = new int[] { 14, 22, 60 };
		gbl.columnWeights = new double[] { 1.0, 1.0 };
		gbl.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl);

		JTextPane barnPane = new JTextPane();
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setBold(attributeSet, true);

		// Set the attributes before adding text
		barnPane.setCharacterAttributes(attributeSet, true);
		barnPane.setText("Improving the barn\n\n");

		Document doc = barnPane.getStyledDocument();
		attributeSet = new SimpleAttributeSet();
		try {
			doc.insertString(doc.getLength(),
					"Adding barn improvements uses one daily action and results in +3 Happiness "
							+ "randomly added to an animal you own.\n\nThe action will be disabled "
							+ "if you have not yet purchased any animals from the General Store.",
					attributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		GridBagConstraints gbcBp = new GridBagConstraints();
		gbcBp.insets = new Insets(0, 0, 5, 5);
		gbcBp.fill = GridBagConstraints.BOTH;
		gbcBp.gridx = 0;
		gbcBp.gridy = 0;
		this.add(barnPane, gbcBp);
		barnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!game.hasActions()) {
					showInsufficientActions(barnButton);
				} else {
					List<Animal> animals = game.getFarm().getAnimals();
					Random rand = new Random();
					Animal animal = animals.get(rand.nextInt(animals.size()));
					animal.setHappy(animal.getHappy() + 3);
					game.incrementActionCount();
				}

			}
		});
		this.checkAnimalButton();
		GridBagConstraints gbcBb = new GridBagConstraints();
		gbcBb.insets = new Insets(0, 0, 0, 5);
		gbcBb.fill = GridBagConstraints.NONE;
		gbcBb.gridx = 0;
		gbcBb.gridy = 1;
		this.add(barnButton, gbcBb);

		JTextPane paddockPane = new JTextPane();
		attributeSet = new SimpleAttributeSet();
		StyleConstants.setBold(attributeSet, true);

		// Set the attributes before adding text
		paddockPane.setCharacterAttributes(attributeSet, true);
		paddockPane.setText("Improving the land\n\n");

		doc = paddockPane.getStyledDocument();
		attributeSet = new SimpleAttributeSet();
		try {
			doc.insertString(doc.getLength(),
					"Improve your farm by clearing scrub from land and erecting a fence.  "
							+ "This will increase your number of paddocks and allow you to plant an "
							+ "additional crop, purchased from the General Store.\n\nIf you have reached "
							+ "the maximum number of paddocks for your farm type this action will be disabled and "
							+ "further improvements are not possible.",
					attributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		GridBagConstraints gbcPp = new GridBagConstraints();
		gbcPp.insets = new Insets(0, 0, 5, 5);
		gbcPp.fill = GridBagConstraints.BOTH;
		gbcPp.gridx = 1;
		gbcPp.gridy = 0;
		this.add(paddockPane, gbcPp);

		paddockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!game.hasActions()) {
					showInsufficientActions(paddockButton);
				} else {
					Paddock paddock = new Paddock();
					game.getFarm().addPaddock(paddock);
					game.incrementActionCount();
				}

			}
		});
		this.checkPaddockButton();
		GridBagConstraints gbcPb = new GridBagConstraints();
		gbcPb.insets = new Insets(0, 0, 0, 5);
		gbcPb.gridx = 1;
		gbcPb.gridy = 1;
		this.add(paddockButton, gbcPb);

	}

	/**
	 * 
	 * @param button
	 */
	public void showInsufficientActions(JButton button) {
		JOptionPane.showMessageDialog(button,
				"There are no actions left today.\n\nMove to the next day (File > Go to next day)",
				"Max daily actions", JOptionPane.ERROR_MESSAGE);
	}

}
