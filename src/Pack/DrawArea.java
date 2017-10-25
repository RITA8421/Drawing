package Pack;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.MouseMotionAdapter;

//Classe pour Dessiner  (tous les types de forme et ¨¦v¨¦nements souris)

public class DrawArea extends JPanel{
	DrawPad drawpad =null;
    Drawing[] itemList =new Drawing[5000]; //un 'Drawing' class pour dessiner    
    int currentChoice = -1;//set tous conditions des formes est null
    int index = 0;//combien de la forme a ete dessine maintenant
    private Color color = Color.black;
    int R,G,B;
    int f1,f2;
    String stytle ;
    float stroke = 1.0f;//set Empattement est 1.0
    int sideNum=3;//ar¨ºtes du polygone
    int count=0;//combien les sommets du polygone que on l'a dessine
    boolean isOver=true;//Est'ce que le polygone est fini?
    int oldmovx,oldmovy;//1 Les variables temporaires lors du d¨¦placement
    int oldx,oldy;//2 Les variables temporaires lors du d¨¦placement
    JPopupMenu popMenu = new JPopupMenu();//le menu pour cliquer en souris droit
    JMenuItem copy = null, paste = null; // le menu pour copier & coller
    int copyx,copyy;//les coordonners pour la souris quand on vaut copier
    int pastex,pastey;//les coordonners de la souris quand cliquer au droit pour coller
    int bt3x,bt3y;//les coordonners de la souris quand cliquer au droit
    boolean isCopy=false;//si on a deja cliquer le copier,installer true
    DrawingImage drawingImg;
	DrawArea(DrawPad dp) {
		drawpad = dp;
		// set la forme de la souris comme une croix
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// setCursor set le forme de la souris£¬getPredefinedCursor() return un cible
		setBackground(Color.white);
		addMouseListener(new MouseA());//  ajouter l'evenement de la souris
		addMouseMotionListener(new MouseB());
		popMenu.add(copy = new JMenuItem("copy"));  
	    popMenu.add(paste = new JMenuItem("paste")); 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // ouvrir un menu
                	bt3x=e.getX();//obtenir le coordonner
                	bt3y=e.getY();
                	popMenu.show(DrawArea.this, e.getX(), e.getY());//montrer le menu de cliquer en souris droit
                }
            }
        });
        copy.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  //cliquer au menu pour copier
            	copyx=bt3x;
            	copyy=bt3y;
            	isCopy=true;
            }  
           });  
        paste.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  //cliquer au menu pour coller
            	pastex=bt3x;
            	pastey=bt3y;
            	if(isCopy){
	            	int movx=pastex-copyx;
	          		int movy=pastey-copyy;
	          		int num=0;
	          		for(int i=0;i<index;i++){
        				Drawing dr=itemList[i];
        				if(dr.isChecked){
        					num++;
        					Drawing dr2=null;
        					if(dr instanceof Line){
        						dr2=new Line();
        					}
        					if(dr instanceof Rect){
        						dr2=new Rect();
        					}
        					if(dr instanceof Oval){
        						dr2=new Oval();
        					}
        					if(dr instanceof Circle){
        						dr2=new Circle();
        					}
        					if(dr instanceof Polygon){
        						dr2=new Polygon();
        					}
        					dr2.x1=dr.x1+movx;
        					dr2.y1=dr.y1+movy;
        					dr2.x2=dr.x2+movx;
        					dr2.y2=dr.y2+movy;
	      	  				if(dr instanceof Polygon){
	      	  					for(int j=0;j<dr.list.size();j++){
	      	  						Point pt=new Point();		      	  						
	      	  						pt.x=dr.list.get(j).x+movx;
	      	  						pt.y=dr.list.get(j).y+movy;
	      	  						dr2.list.add(pt);
	      	  					}
	      	  				}
		      	  			for(int k=0;k<index;k++){//juger si le forme du copier est doubler ?si oui ,annuler de ajouter le nouvel forme
		      	  				Drawing dr3=itemList[k];
		      	  				if(GraphicsUtil.compare(dr2, dr3)){		
		      	  					//System.out.println("3333333333");
		      	  					return;
		      	  				}
		      	  			}
	      	  				itemList[index+num-1]=dr2;
        				}
          		  }  
	          	  index=index+num;
          		  repaint();
            	}
            }  
           });  
		//createNewitem();		
        
        Drawing dr=new Line();
        dr.x1=121;
        dr.x2=421;
        dr.y1=261;
        dr.y2=106;
        //dr.angle=-30;
        itemList[0]=dr;
        dr=new Rect();
        dr.x1=643;
        dr.x2=852;
        dr.y1=142;
        dr.y2=255;
        itemList[1]=dr;
        dr=new Oval();
        dr.x1=576;
        dr.x2=756;
        dr.y1=465;
        dr.y2=570;
        itemList[2]=dr;
        dr=new Circle();
        dr.x1=1195;
        dr.x2=1419;
        dr.y1=164;
        dr.y2=326;
        itemList[3]=dr;
        dr=new Polygon();
        dr.x1=1280;
        dr.x2=253;
        dr.y1=1280;
        dr.y2=529;
        dr.sideNum=5;
        Point p=new Point(1280,453);
        dr.list.add(p);
        p=new Point(1380,473);
        dr.list.add(p);
        p=new Point(1470,637);
        dr.list.add(p);
        p=new Point(1359,737);
        dr.list.add(p);
        p=new Point(1265,685);
        dr.list.add(p);
        itemList[4]=dr;
        index=5;
        repaint();
        drawingImg=new DrawingImage();
	}
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;//defini la brosse;
		int  j = 0;
		while(j<index)
		{
			draw(g2d,itemList[j]);
			j++;
	    }		
	}
	void draw(Graphics2D g2d , Drawing i)
	{
		i.draw(g2d);//transmettre la brosse 
	}
	
	void createNewitem(){//selon les definition creer une nouvelle forme
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			switch(currentChoice){
			case 0: 
				itemList[index] = new Line();
				break;
			case 1: 
				itemList[index] = new Rect();
				break;
			case 2: 
				itemList[index] = new Oval();
				break;
			case 3: 
				itemList[index] = new Circle();
			break;
			case 4: 
				itemList[index] = new Polygon();
				itemList[index].sideNum=sideNum;
			break;
		}
	  itemList[index].R = R;
	  itemList[index].G = G;
	  itemList[index].B = B;
	  itemList[index].stroke = stroke;	 	  
	}
   
    public void setIndex(int x){//installer d'index
    	index = x;
    }
    public int getIndex(){
    	return index ;
    }
    public void setColor(Color color)
    {
    	this.color = color; 
    }
    public void setStroke(float f)//definir epaisseur de brosse
    {
    	stroke = f;
    }
	public void chooseColor()//Selectionner une couleur
	{
		color = JColorChooser.showDialog(drawpad, "Selectionner une couleur", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 0;
			G = 0;
			B = 0;
		}
		itemList[index].R = R;
		itemList[index].G = G;
		itemList[index].B = B;
	}
	public void setSymmetric()//definir axi symetrie du graphique
	{
		//String input ;
		SymmetricDialog dlg=new SymmetricDialog();
		dlg.setModal(true);
		dlg.show();
		if(dlg.angle==-1)
			return;
		for(int i=0;i<index;i++){
			if(itemList[i].isChecked){		
				itemList[i].angle=dlg.angle;
				if(itemList[i] instanceof Line){
					itemList[i].angle+=(float)((-1.0)*(Math.atan((float)(itemList[i].y2-itemList[i].y1) / (float)(itemList[i].x2-itemList[i].x1))* 180.0 / Math.PI));
				}
			}
		}
		repaint();
	}
	public void setZoom()//Definissez la taille de la homoth¨¦tie graphique selectionne
	{
		String input ;
		input = JOptionPane.showInputDialog("La taille de la dilatation( >0 )");
		float zoom=1.0f;
		try {
			zoom = Float.parseFloat(input);	
		} catch (Exception e) {
			zoom = 1.0f;			
		}
		for(int i=0;i<index;i++){
			if(itemList[i].isChecked){
				itemList[i].x1=(int)(itemList[i].x1*zoom);
				itemList[i].y1=(int)(itemList[i].y1*zoom);
				itemList[i].x2=(int)(itemList[i].x2*zoom);
				itemList[i].y2=(int)(itemList[i].y2*zoom);
				if(itemList[i] instanceof Polygon){
					int size=itemList[i].list.size();
					for(int j=0;j<size;j++){
						itemList[i].list.get(j).x=(int)(itemList[i].list.get(j).x*zoom);
						itemList[i].list.get(j).y=(int)(itemList[i].list.get(j).y*zoom);
					}
				}
				Drawing dr=itemList[i];
				boolean comp=false;
				int k=0;
				for(k=0;k<index;k++){//s'il est doublon,pas de homoth¨¦tie
					if(k==i)
						continue;
					Drawing dr2=itemList[k];
					if(GraphicsUtil.compare(dr,dr2)){
						comp=true;
						break;
					}
				}
				if(comp){//recuperation de la taille avant mise ¨¤ homoth¨¦tie
					for(int m=0;m<=i;m++){
						if(itemList[m].isChecked){
							itemList[m].x1=(int)(itemList[m].x1/zoom);
							itemList[m].y1=(int)(itemList[m].y1/zoom);
							itemList[m].x2=(int)(itemList[m].x2/zoom);
							itemList[m].y2=(int)(itemList[m].y2/zoom);
							if(itemList[m] instanceof Polygon){
								int size=itemList[m].list.size();
								for(int n=0;n<size;n++){
									itemList[m].list.get(n).x=(int)(itemList[m].list.get(n).x/zoom);
									itemList[m].list.get(n).y=(int)(itemList[m].list.get(n).y/zoom);
								}
							}
						}
					}
					return;
				}
			}
		}
		repaint();
	}
	public void setRotate()//Reglez l'angle de rotation
	{
		String input ;
		input = JOptionPane.showInputDialog("L'angle de rotation( >0 )");
		float angle=0;
		try {
			angle = Float.parseFloat(input);			
		} catch (Exception e) {
			angle = 0.0f;			
		}
		for(int i=0;i<index;i++){
			if(itemList[i].isChecked)
				itemList[i].angle=angle;
		}
		repaint();
	}
	public void setStroke()//Reglez l'epaisseur de stylo
	{
		String input ;
		input = JOptionPane.showInputDialog("L'epaisseur de stylo( >0 )");
		try {
			stroke = Float.parseFloat(input);
			
		} catch (Exception e) {
			stroke = 1.0f;
			
		}			
	}
	public void setCurrentChoice(int i )//texte
	{
		currentChoice = i;
	}
	
	public void setFont(int  i,int font)//type de caract¨¨re
	{
		if(i == 1)
		{
			f1 = font; 
		}
		else 
			f2 = font;
	}

// TODO souris Listener
class MouseA extends MouseAdapter
{
	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Entrer la souris
		//drawpad.setStratBar("La souris entre a£º["+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO souris sortie
		//drawpad.setStratBar("La souris sortie a£º["+me.getX()+" ,"+me.getY()+"]");
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO souris presse
		//drawpad.setStratBar("La souris presse a£º["+me.getX()+" ,"+me.getY()+"]");//pr¨¦senter sur la barre
		if(me.getButton()==MouseEvent.BUTTON3)
			return;
		oldmovx=0;
		oldmovy=0;
		oldx=me.getX();
		oldy=me.getY();
		if(currentChoice==-1){//Graphique est selectionne lorsque vous cliquez, en rouge
			float totalArea=0;
			float totalLen=0;
			int x=me.getX();
			int y=me.getY();
			boolean find=false;
			for(int i=0;i<index;i++){
				Drawing dr=itemList[i];
				int x1=dr.x1;
				int y1=dr.y1;
				int x2=dr.x2;
				int y2=dr.y2;
				if(dr instanceof Line){			
					float len=GraphicsUtil.getLength(dr);
					float len2=(float)Math.sqrt((Math.pow(x-x1, 2))+(Math.pow(y-y1, 2)));
					float len3=(float)Math.sqrt((Math.pow(x-x2, 2))+(Math.pow(y-y2, 2)));
					//System.out.println("len="+len+" len2="+len2+" len3="+len3);
					if((len2+len3-len)<=10){
						dr.isChecked=true;
						find=true;						
						totalLen+=len;
						drawingImg.drList.add(dr);
					}					
				}
				if(dr instanceof Rect){					
					if(x>=x1&&x<=x2&&y>=y1&&y<=y2){
						dr.isChecked=true;
						find=true;
						totalArea+=GraphicsUtil.getArea(dr);
						totalLen+=GraphicsUtil.getLength(dr);
						drawingImg.drList.add(dr);
					}					
				}
				if(dr instanceof Oval){					
					int currx=x-x1;
					int curry=y-y1;					
					if(currx>=0&&currx<=x2-x1&&curry>=0&&curry<=y2-y1){
						dr.isChecked=true;
						find=true;
						totalArea+=GraphicsUtil.getArea(dr);
						totalLen+=GraphicsUtil.getLength(dr);
						drawingImg.drList.add(dr);
					}
				}
				if(dr instanceof Circle){
					int currx=x-x1;
					int curry=y-y1;			
					if(currx>=0&&currx<=x2-x1&&curry>=0&&curry<=y2-y1){
						dr.isChecked=true;
						find=true;
						totalArea+=GraphicsUtil.getArea(dr);
						totalLen+=GraphicsUtil.getLength(dr);
						drawingImg.drList.add(dr);
					}
				}
				if(dr instanceof Polygon){					
					int minx=32767,miny=32767,maxx=0,maxy=0;
					for(int j=0;j<dr.list.size();j++){
						if(minx>dr.list.get(j).x)
							minx=dr.list.get(j).x;
						if(miny>dr.list.get(j).y)
							miny=dr.list.get(j).y;
						if(maxx<dr.list.get(j).x)
							maxx=dr.list.get(j).x;
						if(maxy<dr.list.get(j).y)
							maxy=dr.list.get(j).y;							
					}
					if(x>=minx&&x<=maxx&&y>=miny&&y<=maxy){
						dr.isChecked=true;
						find=true;
						totalArea+=GraphicsUtil.getArea(dr);
						totalLen+=GraphicsUtil.getLength(dr);
						drawingImg.drList.add(dr);
					}
				}				
			}
			if(!find){
				for(int i=0;i<index;i++){
					Drawing dr=itemList[i];
					dr.isChecked=false;
				}
				drawingImg.drList.clear();
				drawpad.setStratBar("");//set tips de barre d'etat
			}else
				drawpad.setStratBar("surface£º"+totalArea+",perimetre£º"+totalLen);//tips de barre d'etat
			repaint();			
			return;
		}
		if(currentChoice==4){//dessiner un polygone
			if(count==0){
				createNewitem();
				itemList[index].x1 = itemList[index].x2 = me.getX();
				itemList[index].y1 = itemList[index].y2 = me.getY();
				itemList[index].sideNum=sideNum;
				index++;
				isOver=false;//si seulement dessiner un sommet, on ne peut pas copie le polygone
			}
			if(count<sideNum){//si le sommet n'est pas assez£¬on ne peut pas copie le polygone
				itemList[index-1].list.add(new Point(me.getX(),me.getY()));
				count++;
				if(count==itemList[index-1].sideNum){
					count=0;
					isOver=true;
				}
			}
			Drawing dr=itemList[index-1];
			if(index>1){//Determiner le doublon£¬si existe on annule ce polygone
				for(int i=0;i<index-1;i++){
					Drawing dr2=itemList[i];
					if(GraphicsUtil.compare(dr, dr2)){
						index--;
						break;
					}
				}
			}
			return;
		}
		createNewitem();//Creer un nouveau objets graphiques de l'unite de base
		itemList[index].x1 = itemList[index].x2 = me.getX();
		itemList[index].y1 = itemList[index].y2 = me.getY();
		index++;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO relachez la souris
		//drawpad.setStratBar("relachez la souris a£º["+me.getX()+" ,"+me.getY()+"]");
		if(currentChoice==-1)
			return;		
		if(currentChoice==4){			
			return;
		}		
		itemList[index-1].x2 = me.getX();
		itemList[index-1].y2 = me.getY();	
		if(currentChoice==0){
			
			//System.out.println("angle="+itemList[index-1].angle);
			/*
			int len2=(int)Math.sqrt((float)(Math.pow(itemList[index-1].x2-itemList[index-1].x1, 2))+(float)(Math.pow(itemList[index-1].y2-itemList[index-1].y1, 2)));
			if(itemList[index-1].x2>itemList[index-1].x1){
				itemList[index-1].x2 = itemList[index-1].x1+len2;
			}else{
				itemList[index-1].x2 = itemList[index-1].x1-len2;
			}		
			itemList[index-1].y2 = itemList[index-1].y1;
			*/
		}
		Drawing dr=itemList[index-1];
		if(index>1){//Determiner le doublon£¬si existe on annule cette forme geometique
			for(int i=0;i<index-1;i++){
				Drawing dr2=itemList[i];
				if(GraphicsUtil.compare(dr, dr2)){
					index--;
					break;
				}
			}
		}		
		repaint();
	}
}

	class MouseB extends MouseMotionAdapter {
      public void mouseDragged(MouseEvent me)//mouvements de la souris
      {
    	  drawpad.setStratBar("Glisser la souris£º["+me.getX()+" ,"+me.getY()+"]");
    	  if(currentChoice==-1){//Si vous ne selectionne pas le type de graphique, deplacer les objets deja selectionnes
    		  int movx=me.getX()-oldx;
    		  int movy=me.getY()-oldy;
    		  int nowmovx=movx-oldmovx;
    		  int nowmovy=movy-oldmovy;
    		  boolean isIn=false;
    		  for(int i=0;i<index;i++){
  				Drawing dr=itemList[i];
  				if(dr.isChecked){
  					dr.x1=dr.x1+movx-oldmovx;
  					dr.y1=dr.y1+movy-oldmovy;
  					dr.x2=dr.x2+movx-oldmovx;
  					dr.y2=dr.y2+movy-oldmovy;
	  				if(dr instanceof Polygon){
	  					for(int j=0;j<dr.list.size();j++){
	  						dr.list.get(j).x=dr.list.get(j).x+movx-oldmovx;
	  						dr.list.get(j).y=dr.list.get(j).y+movy-oldmovy;
	  					}
	  				}
  				}
    		  }
    		  for(int i=0;i<index;i++){
    			  Drawing dr=itemList[i];
    			  if(dr.isChecked){
    				  for(int j=0;j<index;j++){
    	    			  Drawing dr2=itemList[j];
    	    			  if(!dr2.isChecked){
    	    				  if(GraphicsUtil.compare(dr, dr2)){
    	    					  isIn=true;
    	    					  break;
    	    				  }
    	    			  }
    				  }
    				  if(isIn)
    					  break;
    			  }
    		  }
    		  if(isIn){
    			  for(int i=0;i<index;i++){
        			  Drawing dr=itemList[i];
        			  if(dr.isChecked){
        				  	dr.x1=dr.x1-nowmovx;
        					dr.y1=dr.y1-nowmovy;
        					dr.x2=dr.x2-nowmovx;
        					dr.y2=dr.y2-nowmovy;
	      	  				if(dr instanceof Polygon){
	      	  					for(int j=0;j<dr.list.size();j++){
	      	  						dr.list.get(j).x=dr.list.get(j).x-nowmovx;
	      	  						dr.list.get(j).y=dr.list.get(j).y-nowmovy;
	      	  					}
	      	  				}        			  
        			  }
    			  }
    			  return;
    		  }
    		  oldmovx=movx;
    		  oldmovy=movy;
    		  repaint();
    		  return;		
    	  }
    	  if(currentChoice==4){	  			
    		  return;
    	  }   
    	  itemList[index-1].x2 = me.getX();
		  itemList[index-1].y2 = me.getY();
    	  repaint();
      }
      public void mouseMoved(MouseEvent me)//mouvements de la souris
      {
    	  drawpad.setStratBar("Le coordonnees de souris£º["+me.getX()+" ,"+me.getY()+"]");    	  
    	  if(currentChoice==4){//tracer des lignes entre les sommets des polygones
    		  if(count>0&&count<sideNum){
    			  itemList[index-1].x2=me.getX();
    			  itemList[index-1].y2=me.getY();
    		  }
    		  repaint();
    		  return;
    	  }   
      }
	}
}
