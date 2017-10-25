package Pack;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SymmetricDialog extends JDialog {
	private final ButtonGroup buttonGroup = new ButtonGroup();

	JRadioButton rdbtnNewRadioButton,rdbtnNewRadioButton_1;
	float angle=0;
	/**
	 * Create the dialog.
	 */
	public SymmetricDialog() {
		setTitle("symetrie");
		setBounds(100, 100, 533, 264);
		getContentPane().setLayout(null);
		
		rdbtnNewRadioButton = new JRadioButton("horizontal");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(161, 58, 121, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("longitudinal");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(161, 106, 121, 23);
		getContentPane().add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton.setSelected(true);
		JButton btnNewButton = new JButton("confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnNewRadioButton.isSelected())
					angle=0;
				else angle=90;
				SymmetricDialog.this.dispose();
			}
		});
		btnNewButton.setBounds(125, 169, 93, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("annuler");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				angle=-1;
				SymmetricDialog.this.dispose();
			}
		});
		btnNewButton_1.setBounds(323, 169, 93, 23);
		getContentPane().add(btnNewButton_1);
		this.setLocationRelativeTo(null);
	}
}
