package Pack;

import java.awt.EventQueue;

import javax.swing.UIManager;




//main

public class MiniDrawPad {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
					UIManager.setLookAndFeel(lookAndFeel);
					DrawPad drawpad = new DrawPad("Draw Pad");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
      
	}

}
