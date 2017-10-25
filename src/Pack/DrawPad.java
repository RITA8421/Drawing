package Pack;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.*;

// main menu
public class DrawPad extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2551980583852173918L;
	private JToolBar buttonpanel;//definir Bouton panneau 
	private JMenuBar bar ;//definir la barre de menus
	private JMenu file,color,stroke,help;//d¨¦finir le menu
	private JMenuItem newfile,openfile,savefile,exit;//definir le option dans menu de file
	private JMenuItem helpin,helpmain,colorchoice,strokeitem;//definir le option dans menu de help
	private Icon nf,sf,of;
	private JLabel startbar;
	private DrawArea drawarea;
	private Help  helpobject;
	
	String[] polygonName; 
	//definir les noms des icones de la barre d'outils
	private String names[] = {
			"line",
			"rect","oval","circle",
			"zoom","rotate","symmetric"
			,"stroke","clear","polygon"};
	private Icon icons[];//D¨¦finition d'un tableau d'image
	
	private String tiptext[] = {//set tips quand vous deplacez la souris sur la bouton
			"dessine une ligne","dessine un rectangle","dessine une ellipse","dessine un cercle","homothetie","rotation","symetrie","l'epaisseur de ligne","set vide","polygones"};
	JButton button[];
	private JComboBox stytles ;
	public DrawPad(String string) {
		// TODO constructeur de main menu
		super(string);
	    //initialisation de menu
	    file = new JMenu("fichier");
	    stroke = new JMenu("installer");
	    help = new JMenu("aider");
	    bar = new JMenuBar();//Initialisation la barre de menu
	    
	   
	    bar.add(file);
	    bar.add(stroke);
	    bar.add(help);
	    
	    
	    setJMenuBar(bar);
	    
	    //ajouter les raccourcis
	    file.setMnemonic('F');//ALT+F
	    stroke.setMnemonic('S');//ALT+S
	    help.setMnemonic('H');//ALT+H
	   
	    //File
	    try {
			Reader reader = new InputStreamReader(getClass().getResourceAsStream("/icon"));//la lecture du fichier de reference ¨¤ ClassPath
		} catch (Exception e) {
			// TODO Erreur de lecture du fichier
			JOptionPane.showMessageDialog(this,"Erreur de lecture du fichier£¡","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	    exit = new JMenuItem("quitter");	    
	    file.add(exit);	    
	    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
	    exit.addActionListener(this);
	    

	    //Help
	    helpmain = new JMenuItem("Aide");
	    helpin = new JMenuItem("About DrawPad");
	   
	    help.add(helpmain);
	    help.addSeparator();
	    help.add(helpin);
	    
	    helpin.addActionListener(this);
	    helpmain.addActionListener(this);
	    
	    //Stroke
	    strokeitem = new JMenuItem("set brousse");
	    strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
	    stroke.add(strokeitem);
	    strokeitem.addActionListener(this);
	    
	    //initialisation de barre d'outils
	    buttonpanel = new JToolBar( JToolBar.HORIZONTAL);
	    icons = new ImageIcon[names.length];
	    button = new JButton[names.length];
	    for(int i = 0 ;i<names.length;i++)
	    {
	        icons[i] = new ImageIcon(getClass().getResource("/icon/"+names[i]+".jpg"));//Obtenez l'image de icon
	    	button[i] = new JButton("",icons[i]);
	    	button[i].setToolTipText(tiptext[i]);//set tips quand vous deplacez la souris sur la bouton
	    	buttonpanel.add(button[i]);
	    	button[i].setBackground(Color.red);
	    	button[i].addActionListener(this);
	    }
	   CheckBoxHandler CHandler = new CheckBoxHandler();
	   //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       //fontName = ge.getAvailableFontFamilyNames();
	   polygonName=new String[5];
	   polygonName[0]="triangle";
	   polygonName[1]="quadrilat¨¦ral";
	   polygonName[2]="pentagone";
	   polygonName[3]="hexagone";
	   polygonName[4]="heptagone";
	   stytles = new JComboBox(polygonName);
	   stytles.addItemListener(CHandler);
	   stytles.setMaximumSize(new Dimension(150,50));
	   stytles.setMinimumSize(new  Dimension(100,40));
	   stytles.setFont(new Font(Font.DIALOG,Font.BOLD,15));	  
	   buttonpanel.add(stytles);
	   
	    startbar = new JLabel("DrawPad");

	    //initialisation de DrawArea
	    drawarea = new DrawArea(this);
	    helpobject = new Help(this);
	    Container con = getContentPane();
	    con.add(buttonpanel, BorderLayout.NORTH);
	    con.add(drawarea,BorderLayout.CENTER);
	    con.add(startbar,BorderLayout.SOUTH);
	    Toolkit tool = getToolkit();
	    Dimension dim = tool.getScreenSize();
	    setBounds(40,40,dim.width-70,dim.height-100);
	    setVisible(true);
	    validate();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void setStratBar(String s) {
		startbar.setText(s);
	}
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i<=3;i++)
		{
			if(e.getSource() ==button[i])
			{
				if(drawarea.currentChoice!=-1&&!drawarea.isOver)
					drawarea.index--;
				drawarea.setCurrentChoice(i);
				drawarea.isOver=true;
				drawarea.repaint();
		    }			
		}
		if(e.getSource() ==button[8]){//clear graphic settings
			if(drawarea.currentChoice!=-1&&!drawarea.isOver)
				drawarea.index--;							
			drawarea.isOver=true;
			drawarea.setCurrentChoice(-1);			
		}
		if(e.getSource() ==button[9])//new add polygen
		{
			if(drawarea.currentChoice!=-1&&!drawarea.isOver)
				drawarea.index--;
			drawarea.setCurrentChoice(4);
			drawarea.sideNum=stytles.getSelectedIndex()+3;
			drawarea.isOver=true;
			drawarea.repaint();
	    }
		if(e.getSource() ==button[4]){//homothetie
			drawarea.setZoom();
		}
		if(e.getSource() ==button[5]){//rotate
			drawarea.setRotate();
		}
		if(e.getSource() ==button[6]){//symmetric
			drawarea.setSymmetric();
		}		
	    if(e.getSource() == exit)
		{System.exit(0);}
		else if(e.getSource() == button[7]||e.getSource()==strokeitem)
		{
			drawarea.setStroke();
		}		
		else if(e.getSource() == helpin)
		{helpobject.AboutBook();}
		else if(e.getSource() == helpmain)
		{helpobject.MainHeip();}
				
	}
	
	//Style de police
	public  class CheckBoxHandler implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie) {
			if(ie.getSource() == stytles)
			{   
				drawarea.stytle = polygonName[stytles.getSelectedIndex()];
			}
		}		
	}
}


