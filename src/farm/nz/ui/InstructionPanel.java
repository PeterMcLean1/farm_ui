package farm.nz.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

		JLabel lblNewLabel = new JLabel("Instruction for game play");
		add(lblNewLabel);
	}

}
