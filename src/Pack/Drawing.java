package Pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

//classe type de graphique
public class Drawing implements Serializable {//serialisation

int x1,x2,y1,y2;   	    //les variables de coordonner
int  R,G,B;				//les variables de couleur,red,green,blue
float stroke;			//la propriete d'epaisseur de ligne
float area=0;//surface
float length=0;//perimetre
boolean isChecked=false;//est-ce que le graphique est selectionne
float angle=0;//angle de rotation
int sideNum=3;//le nombre de cotes du polygone
List<Point> list=new ArrayList<Point>();//les coordonnees des sommets du polygone

void draw(Graphics2D g2d ){}//la fonctions de dessin
}

class Line extends Drawing//ligne
{
	void draw(Graphics2D g2d) {
		//g2d.setPaint(new Color(R, G, B));//definir les proprietes de Paint¡£
		if(isChecked)
			g2d.setPaint(new Color(255, 0, 0));//couleur rouge
		else g2d.setPaint(new Color(0, 0, 0));//couleur rouge
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		// setStroke(Stroke s)  pour definir Stroke
		// BasicStroke  definir un ensemble de proprietes de profil graphique
		// BasicStroke.CAP_ROUND  utiliser circulaire pour finir sous-chemins non fermees
		// BasicStroke.JOIN_BEVEL liaison de segment
		g2d.translate(x1, y1);//transformation de coordonnees
		float radian=(float)(angle*Math.PI/180.0);//angle de rotation
		g2d.rotate(radian);
		g2d.drawLine(0, 0, (x2-x1), (y2-y1));// Tracer une ligne droite
		g2d.rotate(-1.0*radian);//angle de recuperation
		g2d.translate((-1)*x1, (-1)*y1);//Recuperation de transformation de coordonnees			
	}
}
class Rect extends Drawing{//rectangle
	void draw(Graphics2D g2d ){
		if(isChecked)
			g2d.setPaint(new Color(255, 0, 0));
		else g2d.setPaint(new Color(0, 0, 0));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.translate(x1, y1);
		float radian=(float)(angle*Math.PI/180.0);
		g2d.rotate(radian);
		g2d.drawRect(0, 0, Math.abs(x1-x2), Math.abs(y1-y2));
		g2d.rotate(-1.0*radian);
		g2d.translate((-1)*x1, (-1)*y1);	
	}
}

class Oval extends Drawing{//ellipse
	void draw(Graphics2D g2d ){		
		if(isChecked)
			g2d.setPaint(new Color(255, 0, 0));
		else g2d.setPaint(new Color(0, 0, 0));
		g2d.setStroke(new BasicStroke(stroke));		
		g2d.translate(x1, y1);
		float radian=(float)(angle*Math.PI/180.0);
		g2d.rotate(radian);
		g2d.drawOval(0,0, Math.abs(x1-x2), Math.abs(y1-y2));
		g2d.rotate(-1.0*radian);
		g2d.translate((-1)*x1, (-1)*y1);		
	}
}

class Circle extends Drawing{//circulaire
	void draw(Graphics2D g2d ){
		if(isChecked)
			g2d.setPaint(new Color(255, 0, 0));
		else g2d.setPaint(new Color(0, 0, 0));
		g2d.setStroke(new BasicStroke(stroke));
		g2d.translate(x1, y1);
		float radian=(float)(angle*Math.PI/180.0);
		g2d.rotate(radian);
		g2d.drawOval(0,0, Math.max(Math.abs(x1-x2), 
				Math.abs(y1-y2)), Math.max(Math.abs(x1-x2), Math.abs(y1-y2)));
		g2d.rotate(-1.0*radian);
		g2d.translate((-1)*x1, (-1)*y1);	
	}
}
class Polygon extends Drawing{//polygone	
	void draw(Graphics2D g2d ){
		if(isChecked)
			g2d.setPaint(new Color(255, 0, 0));
		else g2d.setPaint(new Color(0, 0, 0));
		g2d.setStroke(new BasicStroke(stroke));		
		float radian=(float)(angle*Math.PI/180.0);		
		int size=list.size();
		int xa[]=new int[size];
		int ya[]=new int[size];
		for(int i=0;i<size;i++){
			xa[i]=list.get(i).x;
			ya[i]=list.get(i).y;
		}
		if(size<sideNum){//Si le polygone n'est pas terminee, il peut afficher des lignes entre les sommets
			int i=0;
			for(i=0;i<size-1;i++){
				g2d.translate(xa[i], ya[i]);
				g2d.rotate(radian);
				g2d.drawLine(0, 0, xa[i+1]-xa[i], ya[i+1]-ya[i]);
				g2d.rotate(-1.0*radian);
				g2d.translate((-1)*xa[i], (-1)*ya[i]);	
			}
			g2d.translate(xa[i], ya[i]);
			g2d.rotate(radian);
			g2d.drawLine(0, 0, x2-xa[i], y2-ya[i]);
			g2d.rotate(-1.0*radian);
			g2d.translate((-1)*xa[i], (-1)*ya[i]);	
		}else{ //dessiner un polygone
			int i=0;
			for(i=0;i<size-1;i++){
				g2d.translate(xa[i], ya[i]);
				g2d.rotate(radian);
				g2d.drawLine(0, 0, xa[i+1]-xa[i], ya[i+1]-ya[i]);
				g2d.rotate(-1.0*radian);
				g2d.translate((-1)*xa[i], (-1)*ya[i]);	
			}
			g2d.translate(xa[i], ya[i]);
			g2d.rotate(radian);
			g2d.drawLine(0, 0, xa[0]-xa[i], ya[0]-ya[i]);
			g2d.rotate(-1.0*radian);
			g2d.translate((-1)*xa[i], (-1)*ya[i]);
		}			
	}
}
class Rubber extends Drawing{// Gomme
	void draw(Graphics2D g2d ){
		g2d.setPaint(new Color(255,255,255));//blanc
		g2d.setStroke(new BasicStroke(stroke+4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
	    g2d.drawLine(x1, y1,x2, y2);
	}	
}
