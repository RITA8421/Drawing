package Pack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Help extends JFrame {
	private DrawPad  drawpad = null;
	Help(DrawPad dp)
	{
		drawpad = dp;
	}
	
	public void MainHeip()
	  {
	  	JOptionPane.showMessageDialog(this,"This is Draw Pad!","Draw Pad",JOptionPane.WARNING_MESSAGE);
	  } 
	 public void AboutBook()
	  {
	  	JOptionPane.showMessageDialog(drawpad,"Draw Pad"+"\n\n"+"Drawer 1.0"+"\n\n"
	  	   ,"Draw Pad",JOptionPane.WARNING_MESSAGE);
	  }
}
