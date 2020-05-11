package farm.nz;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import farm.nz.model.Farm;
import farm.nz.model.Farmer;
import farm.nz.model.Game;
import farm.nz.model.Paddock;
import farm.nz.type.FarmType;
import farm.nz.ui.FarmPanel;
import farm.nz.ui.InstructionPanel;
import farm.nz.ui.SetupPanel;
import farm.nz.ui.StorePanel;

public class FarmUIApplication extends JFrame implements ItemListener {

	class StoreModelListener implements PropertyChangeListener {
		private FarmUIApplication app;

		public StoreModelListener(FarmUIApplication app) {
			super();
			this.app = app;
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			app.setLabelText();
		}
	}

	final static String FARM_PANEL = "Farm";
	private static final long serialVersionUID = 1L;
	// Menu options
	final static String SETUP_PANEL = "Set up";
	final static String STORE_PANEL = "General Store";
	final static String TEXT_PANEL = "Instructions";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FarmUIApplication application = new FarmUIApplication();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JLabel accountLabel = new JLabel();
	private JLabel actionLabel = new JLabel();
	private JPanel cards;
	private JLabel dayLabel = new JLabel();
	private Game game;

	/**
	 * Create the application.
	 */
	public FarmUIApplication() {

		initialise();
	}

	public void addComponentToPane(Container pane, Game game) {
		// Put the Menu JComboBox in a JPanel to look nice.
		JPanel comboBoxPane = new JPanel(); // use default FlowLayout
		JLabel farmerName = new JLabel(game.getFarm().getFarmer().getName());
		game.addPropertyChangeListener(Game.ACCOUNT, new StoreModelListener(this));
		game.addPropertyChangeListener(Game.ACTION, new StoreModelListener(this));
		game.addPropertyChangeListener(Game.DAY, new StoreModelListener(this));
		this.setLabelText();

		String comboBoxItems[] = { SETUP_PANEL, TEXT_PANEL, FARM_PANEL, STORE_PANEL };
		JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(farmerName);
		comboBoxPane.add(accountLabel);
		comboBoxPane.add(dayLabel);
		comboBoxPane.add(actionLabel);
		comboBoxPane.add(cb);

		// create the game view cards
		JPanel card1 = new SetupPanel(this, game);
		JPanel card2 = new InstructionPanel();
		JPanel card3 = new FarmPanel(game);
		JPanel card4 = new StorePanel(game);

		// Create the panel with card layout and add the "cards" to it.
		cards = new JPanel(new CardLayout());
		cards.add(card1, SETUP_PANEL);
		cards.add(card2, TEXT_PANEL);
		cards.add(card3, FARM_PANEL);
		cards.add(card4, STORE_PANEL);

		// now add the menu panel and cards to the contentPane
		pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);

	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {
		Farmer farmer = new Farmer();
		Farm farm = new Farm(farmer);
		game = new Game(farm);
		this.setTitle("Swing Valley Farm");
		game.setAccount(50);
		farm.setType(FarmType.FLAT);
		farm.setName("Peter Valley Farm");
		game.setDaysToPlay(5);
		game.setMaxDailyActions(2);
		farm.addPaddock(new Paddock());
		farm.addPaddock(new Paddock());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add all the containers
		this.addComponentToPane(this.getContentPane(), game);
		this.setPreferredSize(new Dimension(800, 600));
		// Display the window.
		this.pack();
		this.setVisible(true);

	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, (String) evt.getItem());
	}

	public void setLabelText() {
		accountLabel.setText("$" + game.getAccount());
		dayLabel.setText("Day " + game.getCurrentDay() + "/" + game.getDaysToPlay());
		actionLabel.setText("Action " + game.getActionCount() + "/" + game.getMaxDailyActions());
	}
}
