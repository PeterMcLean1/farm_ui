package farm.nz;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import farm.nz.ui.FarmPanel;
import farm.nz.ui.InstructionPanel;
import farm.nz.ui.SetupPanel;
import farm.nz.ui.StorePanel;

public class FarmApplication extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel cards;
	// Menu options
	final static String SETUP_PANEL = "Set up";
	final static String TEXT_PANEL = "Instructions";
	final static String FARM_PANEL = "Farm";
	final static String STORE_PANEL = "General Store";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FarmApplication application = new FarmApplication();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FarmApplication() {
		initialise();
	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, (String) evt.getItem());
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {
		this.setTitle("Swing Valley Farm");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add all the containers
		this.addComponentToPane(this.getContentPane());

		// Display the window.
		this.pack();
		this.setVisible(true);

	}

	public void addComponentToPane(Container pane) {
		// Put the Menu JComboBox in a JPanel to look nice.
		JPanel comboBoxPane = new JPanel(); // use default FlowLayout
		String comboBoxItems[] = { SETUP_PANEL, TEXT_PANEL, FARM_PANEL, STORE_PANEL };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);

		// create the game view cards
		JPanel card1 = new SetupPanel(this);
		JPanel card2 = new InstructionPanel();
		JPanel card3 = new FarmPanel();
		JPanel card4 = new StorePanel();

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

}
