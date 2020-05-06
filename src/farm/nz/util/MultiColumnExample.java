package farm.nz.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MultiColumnExample extends JFrame {
	// See Serialization for more info, this is to remove a warning.
	private static final long serialVersionUID = 1L;

	private static final int ROWS = 17;
	private static final int COLUMNS = 7;
	private static final int ELEMENTLENGTH = 7; // The length of the element Strings.
	private static final String TITLE = "MultiColumnJListExample";

	private ArrayList data;
	private JList list;
	private MultiColumnCellRenderer cellrenderer;

	private MultiColumnExample() {
		// Set up the JFrame
		super(TITLE);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(800 / 2 - COLUMNS * ELEMENTLENGTH * 5, 0);

		// Create the JList
		data = new ArrayList();
		this.addExampleData(data);
		list = new JList(data.toArray());
		cellrenderer = new MultiColumnCellRenderer(COLUMNS);
		list.setCellRenderer(cellrenderer);

		// Play around with the colours and rendering settings.
		list.setSelectionForeground(Color.WHITE);
		list.setSelectionBackground(Color.BLACK);
		int column = 1;
		cellrenderer.setFGColour(column, Color.WHITE);
		cellrenderer.setBGColour(column, Color.BLACK);
		cellrenderer.setSFGColour(column, Color.BLACK);
		cellrenderer.setSBGColour(column, Color.WHITE);

		// Add the JList to the JFrame and display it.
		this.getContentPane().add(list);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Adds some example data to a given ArrayList.
	 * 
	 * @param arraylist The ArrayList to add data to.
	 */
	private void addExampleData(ArrayList arraylist) {
		Random rand = new Random();
		final int MAXVALUE = (int) Math.pow(10, ELEMENTLENGTH);
		for (int i = 0; i < ROWS; i++) {
			String[] datacolumn = new String[COLUMNS];
			for (int j = 0; j < COLUMNS; j++) {
				// Let's have a String if it's even.
				if (j % 2 == 0) {
					// If you don't understand this bit, search the web for ASCIITABLE
					char[] tobestring = new char[ELEMENTLENGTH];
					for (int u = 0; u < ELEMENTLENGTH; u++) {
						tobestring[u] = (char) ('a' + rand.nextInt((int) ('z' - 'a')));
					}
					tobestring[0] -= 'a' - 'A'; // Uppercase the first letter
					datacolumn[j] = new String(tobestring);
				}
				// And some funky integers if odd.
				else {
					datacolumn[j] = String.valueOf(rand.nextInt(MAXVALUE));
				}
			}
			arraylist.add(datacolumn);
		}
	}

	/**
	 * A simple example of a ListCellRenderer capable of rendering several columns.
	 * The ListCellRenderer is also capable of manipulation of the colour of
	 * specific columns. You can of course add more functionallity with ease just by
	 * adding say different colours depending whether or not the index is odd or
	 * even using %. Feel free to use it and play around with it.
	 *
	 * @author Pontus Stenetorp
	 * @version 2007-07-02
	 */
	private class MultiColumnCellRenderer extends JPanel implements ListCellRenderer {
		// See Serialization for more info, this is to remove a warning.
		private static final long serialVersionUID = 1L;

		private JLabel[] labels;
		private Color[] fgs; // Stores the foreground colours for each column.
		private Color[] bgs; // Stores the background colours for each column.
		private Color[] sfgs; // Stores the foreground colours for each column when selected.
		private Color[] sbgs; // Stores the background colours for each column when selected.

		/**
		 * The JList has to hold objects of type and length String[columns]. If no Color
		 * has been set to a column it will use the default colours of the JList.
		 *
		 * @param columns The amount of columns that the JList stores.
		 */
		public MultiColumnCellRenderer(int columns) {
			setLayout(new GridLayout(1, columns));
			labels = new JLabel[columns];
			fgs = new Color[columns];
			bgs = new Color[columns];
			sfgs = new Color[columns];
			sbgs = new Color[columns];

			for (int i = 0; i < columns; i++) {
				labels[i] = new JLabel();
				labels[i].setOpaque(true);
				add(labels[i]);
			}
		}

		/**
		 * @param column The column you wish to set the foreground of.
		 * @param colour The Color to render the column foreground with.
		 */
		public void setFGColour(int column, Color colour) {
			fgs[column] = colour;
		}

		/**
		 * @param column The column you wish to set the background of.
		 * @param colour The Color to render the column background with.
		 */
		public void setBGColour(int column, Color colour) {
			bgs[column] = colour;
		}

		/**
		 * @param column The column you wish to set the selection foreground of.
		 * @param colour The Color to render the column foreground with.
		 */
		public void setSFGColour(int column, Color colour) {
			sfgs[column] = colour;
		}

		/**
		 * @param column The column you wish to set the selection background of.
		 * @param colour The Color to render the column background with.
		 */
		public void setSBGColour(int column, Color colour) {
			sbgs[column] = colour;
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// You might want to change this line in case you don't use String[]'s.
			String[] celldata = (String[]) value;

			for (int i = 0; i < celldata.length; i++) {
				labels[i].setText(celldata[i]);

				// Colour logic, highly configurable.
				if (isSelected) {
					if (sbgs[i] != null) {
						this.labels[i].setBackground(sbgs[i]);
					} else {
						this.labels[i].setBackground(list.getSelectionBackground());
					}

					if (sfgs[i] != null) {
						this.labels[i].setForeground(sfgs[i]);
					} else {
						this.labels[i].setForeground(list.getSelectionForeground());
					}
				} else {
					if (bgs[i] != null) {
						this.labels[i].setBackground(bgs[i]);
					} else {
						this.labels[i].setBackground(list.getBackground());
					}

					if (fgs[i] != null) {
						this.labels[i].setForeground(fgs[i]);
					} else {
						this.labels[i].setForeground(list.getForeground());
					}
				}
			}

			super.setEnabled(list.isEnabled());
			super.setFont(list.getFont());
			return this;
		}
	}

	public static void main(String[] args) {
		MultiColumnExample mce = new MultiColumnExample();
	}
}