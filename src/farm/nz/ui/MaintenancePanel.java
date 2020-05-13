package farm.nz.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import farm.nz.model.Game;

public class MaintenancePanel extends JPanel {
	private Game game;

	public MaintenancePanel(Game game) {
		initialise(game);
	}

	private void initialise(Game game) {
		this.game = game;

		this.setBorder(new EmptyBorder(5, 5, 5, 0));

		GridBagLayout gbl_maintenance = new GridBagLayout();
		gbl_maintenance.columnWidths = new int[] { 42, 42 };
		gbl_maintenance.rowHeights = new int[] { 14, 22, 60 };
		gbl_maintenance.columnWeights = new double[] { 1.0, 1.0 };
		gbl_maintenance.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_maintenance);

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
					"JTextPane is a subclass of JEditorPane class. JTextPane is used for styled document with embedded images and components. It is text component that can be marked up with attributes that are represented graphically. JTextPane uses a DefaultStyledDocument as its default model.",
					attributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		GridBagConstraints gbc_barnPane = new GridBagConstraints();
		gbc_barnPane.insets = new Insets(0, 0, 5, 5);
		gbc_barnPane.fill = GridBagConstraints.BOTH;
		gbc_barnPane.gridx = 0;
		gbc_barnPane.gridy = 0;
		this.add(barnPane, gbc_barnPane);

		JButton barnButton = new JButton("Barn improvements");
		barnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Barn has been improved!");
			}
		});
		GridBagConstraints gbc_barnButton = new GridBagConstraints();
		gbc_barnButton.insets = new Insets(0, 0, 0, 5);
		gbc_barnButton.fill = GridBagConstraints.NONE;
		gbc_barnButton.gridx = 0;
		gbc_barnButton.gridy = 1;
		this.add(barnButton, gbc_barnButton);

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
					"JTextPane is a subclass of JEditorPane class. JTextPane is used for styled document with embedded images and components. It is text component that can be marked up with attributes that are represented graphically. JTextPane uses a DefaultStyledDocument as its default model.",
					attributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		GridBagConstraints gbc_paddockPane = new GridBagConstraints();
		gbc_paddockPane.insets = new Insets(0, 0, 5, 5);
		gbc_paddockPane.fill = GridBagConstraints.BOTH;
		gbc_paddockPane.gridx = 1;
		gbc_paddockPane.gridy = 0;
		this.add(paddockPane, gbc_paddockPane);

		JButton paddockButton = new JButton("Land improvements");
		paddockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New fencing has increased paddock number!");
			}
		});
		GridBagConstraints gbc_paddockButton = new GridBagConstraints();
		gbc_paddockButton.insets = new Insets(0, 0, 0, 5);
		gbc_paddockButton.gridx = 1;
		gbc_paddockButton.gridy = 1;
		this.add(paddockButton, gbc_paddockButton);

	}

}
