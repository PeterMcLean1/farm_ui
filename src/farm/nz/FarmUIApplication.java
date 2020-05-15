package farm.nz;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import farm.nz.model.Animal;
import farm.nz.model.Farm;
import farm.nz.model.Farmer;
import farm.nz.model.Game;
import farm.nz.model.Item;
import farm.nz.model.Paddock;
import farm.nz.type.FarmType;
import farm.nz.ui.FarmPanel;
import farm.nz.ui.InstructionPanel;
import farm.nz.ui.SetupPanel;
import farm.nz.ui.StorePanel;

public class FarmUIApplication extends JFrame {

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
	final static String HELP = "Instructions";
	final static String MOVE_DAY_PANEL = "Go to next day";

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

	private JLabel statusLabel = new JLabel();
	private JPanel cards;

	private Game game;

	/**
	 * Create the application.
	 */
	public FarmUIApplication() {
		initialise();
	}

	public void showCard(String card) {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, card);
	}

	public void addComponentToPane(Container pane, Game game) {
		JPanel topPane = new JPanel(); // use default FlowLayout
		topPane.setLayout(new GridLayout());
		game.addPropertyChangeListener(Game.ACCOUNT, new StoreModelListener(this));
		game.addPropertyChangeListener(Game.ACTION, new StoreModelListener(this));
		game.addPropertyChangeListener(Game.DAY, new StoreModelListener(this));
		this.setLabelText();

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem nextDay = new JMenuItem("Go to next day");
		nextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dayEnd();
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});
		fileMenu.add(nextDay);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		JMenu farmMenu = new JMenu("Farm");
		farmMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				showCard(FARM_PANEL);
			}
		});
		menuBar.add(farmMenu);

		JMenu storeMenu = new JMenu("General Store");
		storeMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				showCard(STORE_PANEL);
			}
		});
		menuBar.add(storeMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				showCard(HELP);
			}
		});
		menuBar.add(helpMenu);
		menuBar.add(Box.createHorizontalGlue());
		statusLabel = new JLabel();
		this.setLabelText();
		menuBar.add(statusLabel);
		topPane.add(menuBar);

		// create the game view cards
		JPanel card1 = new SetupPanel(this, game);
		JPanel card2 = new InstructionPanel();
		JPanel card3 = new FarmPanel(game);
		JPanel card4 = new StorePanel(game);

		// Create the panel with card layout and add the "cards" to it.
		cards = new JPanel(new CardLayout());
		cards.add(card1, SETUP_PANEL);
		cards.add(card2, HELP);
		cards.add(card3, FARM_PANEL);
		cards.add(card4, STORE_PANEL);

		// now add the menu panel and cards to the contentPane
		pane.add(topPane, BorderLayout.PAGE_START);
		pane.add(cards, BorderLayout.CENTER);

	}

	private void dayEnd() {
		Farm farm = game.getFarm();
		int bonus = 0;

		List<Animal> animals = farm.getAnimals();
		for (Animal animal : animals) {
			bonus = bonus + animal.getDailyIncome();

		}

		game.setAccount(game.getAccount() + bonus);

		if (game.getCurrentDay() == game.getDaysToPlay()) {
			int score = game.getAccount();
			for (Paddock paddock : farm.getPaddocks()) {
				if (null != paddock.getCrop()) {
					score = score + paddock.getCrop().getResidualValue();
				}
			}
			for (Animal animal : farm.getAnimals()) {
				score = score + animal.getResidualValue();
			}
			for (Item item : farm.getItems()) {
				score = score + item.getResidualValue();
			}
			JOptionPane.showMessageDialog(this, "Your score is: " + score, "GAME OVER", JOptionPane.ERROR_MESSAGE);
		} else {
			this.dayStart();
		}

	}

	/**
	 * Start of day logic
	 * 
	 * @param game Used to track game instance progress
	 */
	private void dayStart() {
		game.incrementCurrentDay();
		game.setActionCount(0);
		Farm farm = game.getFarm();
		boolean skill = false;
		for (Item item : farm.getItems()) {
			if (item.isSkill()) {
				skill = true;

			}
		}

		// reduce animal health and happy by 1
		List<Animal> animals = farm.getAnimals();
		for (Animal a : animals) {
			if (!skill && a.getHappy() > 0) {
				a.setHappy(a.getHappy() - 1);
			}
			if (!skill && a.getHealth() > 0) {
				a.setHealth(a.getHealth() - 1);
			}
		}

		int chance = farm.getType().getEventChance();

		Random rnd = new Random();
		int i = rnd.nextInt(100);

		if (i < chance) {
			int eventType = -1;
			eventType = rnd.nextInt(3);
			StringBuffer sb = new StringBuffer();

			switch (eventType) {
			case 0:
				// drought
				// get farm crops and delete half (rounded down)
				List<Paddock> paddocks = farm.getPaddocks();
				List<Paddock> cropPaddock = new ArrayList<Paddock>();
				int cropsLost = 0;
				for (Paddock p : paddocks) {
					if (p.hasCrop()) {
						cropPaddock.add(p);
					}
				}
				int cropLength = cropPaddock.size() / 2;
				while (cropLength > 0) {
					int deleteCrop = rnd.nextInt(cropPaddock.size());
					Paddock affectedPaddock = cropPaddock.remove(deleteCrop);
					affectedPaddock.setCrop(null);
					cropsLost++;
					cropLength--;

				}

				sb.append("A drought has occured!\n");
				sb.append("The wells have dried up and the crops are thirsty.\n");
				sb.append("You have lost ");
				sb.append(cropsLost);
				sb.append(" crop");
				if (cropsLost > 1) {
					sb.append("s");
				}

				// eventScreen(sb, game);
				JOptionPane.showMessageDialog(this, sb, "!!! SEVERE WEATHER WARNING !!!", JOptionPane.WARNING_MESSAGE);
				break;
			case 1:
				// fence break
				// get farm animals
				// remove one or more of all farm animals
				// remaining animals lose 'substantial' happiness

				int animalLength = animals.size();
				int animalsLost = 0;
				if (animalLength == 0) {
					// there are no animals on farm
					// do nothing
				} else if (animalLength == 1) {
					// only 1 animal on farm so remove it
					animals.remove(0);
					animalsLost++;
				} else {
					// more than 1 animal on farm so remove one
					// at random, and 20% chance of removing other
					// animals
					int deleteAnimal = rnd.nextInt(animalLength);
					animals.remove(deleteAnimal);
					List<Animal> animalList = new ArrayList<Animal>(animals);
					for (Animal a : animalList) {
						a.setHappy(a.getHappy() - 2);
						int animalChance = rnd.nextInt(100);
						if (animalChance < 20) {
							animals.remove(a);
							animalsLost++;
						}
					}
				}

				sb.append("Some of your animals (");
				sb.append(animalsLost);
				sb.append(") have escaped through a broken fence and are lost forever!\n");
				sb.append("The remaining animals are not so happy.");
				// eventScreen(sb, game);
				JOptionPane.showMessageDialog(this, sb, "!!! BROKEN FENCE !!!", JOptionPane.WARNING_MESSAGE);
				break;
			case 2:
				// win county fair
				// add 'reasonable' sum of money to farm account
				// money earned scaled number of farm crops and animals
				int winnings = 30;
				animalLength = farm.getAnimals().size();
				winnings = winnings + 5 * animalLength;

				int numberCrops = 0;
				for (Paddock p : farm.getPaddocks()) {
					if (p.hasCrop()) {
						numberCrops++;
					}
				}
				winnings = winnings + 5 * numberCrops;
				game.setAccount(game.getAccount() + winnings);

				sb.append("Your farm has won the top award at the annual county fair.\n");
				sb.append("The prize winnings ($");
				sb.append(winnings);
				sb.append(") have been added to your farm account.");
				// eventScreen(sb, game);
				JOptionPane.showMessageDialog(this, sb, "!!! COUNTY FAIR AWARDS !!!", JOptionPane.INFORMATION_MESSAGE);
				break;

			}
		}

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

	public void setLabelText() {
		statusLabel.setText("Day " + game.getCurrentDay() + "/" + game.getDaysToPlay() + "  |  Action "
				+ game.getActionCount() + "/" + game.getMaxDailyActions() + "  |  $" + game.getAccount() + "  ");
	}
}
