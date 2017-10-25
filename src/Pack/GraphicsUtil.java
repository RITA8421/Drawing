package Pack;

import java.awt.Point;
import java.util.List;

public class GraphicsUtil {
	//Calcule superficie du polygone
	public static double getPolygonArea(List<Point> list)
	{
		double area = 0.00;
		for(int i = 0;i<list.size();i++){
			if(i<list.size()-1){
				Point p1 = list.get(i);
				Point p2 = list.get(i+1);
				area += p1.getX()*p2.getY() - p2.getX()*p1.getY();
			}else{
				Point pn = list.get(i);
				Point p0 = list.get(0);
				area += pn.getX()*p0.getY()- p0.getX()*pn.getY();
			}	   
		}
		area = area/2.00;
		return area;
	}	
	
	//Calcule superficie
	public static float getArea(Drawing dr){
		if(dr instanceof Line){
			return 0;
		}
		if(dr instanceof Rect){
			return Math.abs(dr.x1-dr.x2)*Math.abs(dr.y1-dr.y2);
		}
		if(dr instanceof Oval){
			int a=Math.abs(dr.x1-dr.x2)/2;
			int b=Math.abs(dr.y1-dr.y2)/2;
			return (float)(Math.PI*a*b);
		}
		if(dr instanceof Circle){
			int max=Math.abs(dr.x1-dr.x2)/2;
			int a=Math.abs(dr.y1-dr.y2)/2;
			if(max<a)
				max=a;
			return (float)(Math.PI*Math.pow(max, 2));
		}
		if(dr instanceof Polygon){
			return (float)getPolygonArea(((Polygon)dr).list);
		}
		return 0;
	}
	
	//Calcule perimetre
	public static float getLength(Drawing dr){
		if(dr instanceof Line){
			return (float)Math.sqrt(Math.pow(dr.x1-dr.x2,2)+Math.pow(dr.y1-dr.y2,2));
		}
		if(dr instanceof Rect){
			int width=Math.abs(dr.x1-dr.x2);
			int height=Math.abs(dr.y1-dr.y2);
			return width*2+height*2;
		}
		if(dr instanceof Oval){
			int a=Math.abs(dr.x1-dr.x2)/2;
			int b=Math.abs(dr.y1-dr.y2)/2;
			int min;
			if(a>b)
				min=b;
			else min=a;
			return (float)(2.0*Math.PI*min)+4*(Math.abs(a-b));
		}
		if(dr instanceof Circle){
			int max=Math.abs(dr.x1-dr.x2)/2;
			int a=Math.abs(dr.y1-dr.y2)/2;
			if(max<a)
				max=a;
			return (float)(2.0*Math.PI*max);
		}
		if(dr instanceof Polygon){
			Polygon p=(Polygon)dr;
			int size=p.list.size();
			float area=0;
			int i=0;
			for(i=0;i<size-1;i++){
				area+=(float)Math.sqrt(Math.pow(p.list.get(i).x-p.list.get(i+1).x,2)+Math.pow(p.list.get(i).y-p.list.get(i+1).y,2));
			}
			area+=(float)Math.sqrt(Math.pow(p.list.get(i).x-p.list.get(0).x,2)+Math.pow(p.list.get(i).y-p.list.get(0).y,2));
			return area;
		}
		return 0;
	}
    //Le coordonnees de sommet sup¨¦rieur gauche p1£¬le coordonnees de sommet en bas a droite p2£¬le coordonnee de point p  
	public static boolean checkInRect(Point p,Point p1,Point p2)  
    {  
        boolean ret1=false;
        if((p.x>=p1.x && p.x<=p2.x)||(p.x>=p2.x && p.x<=p1.x))
        	ret1=true;
        boolean ret2=false;
        if((p.y>=p1.y && p.y<=p2.y)||(p.y>=p2.y && p.y<=p1.y))
        	ret2=true;
        if(ret1&&ret2){
        	//System.out.println("xxxxxxxxxxxx");
        }
        return (ret1&&ret2);  
    }  
	//Determiner le doublon£¬si existe return true, else return false
	public static boolean compare(Drawing dr1,Drawing dr2){
		int minx1=32767,miny1=32767,maxx1=0,maxy1=0;
		int minx2=32767,miny2=32767,maxx2=0,maxy2=0;
				
		if(dr1 instanceof Line){
			if(minx1>dr1.x1)
				minx1=dr1.x1;
			if(minx1>dr1.x2)
				minx1=dr1.x2;
			if(maxx1<dr1.x1)
				maxx1=dr1.x1;
			if(maxx1<dr1.x2)
				maxx1=dr1.x2;	
			
			if(miny1>dr1.y1)
				miny1=dr1.y1;
			if(miny1>dr1.y2)
				miny1=dr1.y2;
			if(maxy1<dr1.y1)
				maxy1=dr1.y1;
			if(maxy1<dr1.y2)
				maxy1=dr1.y2;
			//System.out.println("x1,y1"+minx1+","+miny1+"x2,y2"+maxx1+","+maxy1);
		}
		if(dr1 instanceof Rect){
			minx1=dr1.x1;
			miny1=dr1.y1;
			maxx1=dr1.x2;
			maxy1=dr1.y2;
		}
		if(dr1 instanceof Oval){
			minx1=dr1.x1;
			miny1=dr1.y1;
			maxx1=dr1.x2;
			maxy1=dr1.y2;
		}
		if(dr1 instanceof Circle){
			minx1=dr1.x1;
			miny1=dr1.y1;
			if((dr1.x2-dr1.x1)>(dr1.y2-dr1.y1)){
				maxx1=dr1.x2;
				maxy1=dr1.y1+dr1.x2-dr1.x1;
			}
		}
		if(dr1 instanceof Polygon){
			int size=dr1.list.size();
			for(int i=0;i<size;i++){
				Point p=dr1.list.get(i);
				if(minx1>p.x)
					minx1=p.x;
				if(miny1>p.y)
					miny1=p.y;
				if(maxx1<p.x)
					maxx1=p.x;
				if(maxy1<p.y)
					maxy1=p.y;
			}
		}
		
		if(dr2 instanceof Line){
			if(minx2>dr2.x1)
				minx2=dr2.x1;
			if(minx2>dr2.x2)
				minx2=dr2.x2;
			if(maxx2<dr2.x1)
				maxx2=dr2.x1;
			if(maxx2<dr2.x2)
				maxx2=dr2.x2;
			
			if(miny2>dr2.y1)
				miny2=dr2.y1;
			if(miny2>dr2.y2)
				miny2=dr2.y2;
			if(maxy2<dr2.y1)
				maxy2=dr2.y1;
			if(maxy2<dr2.y2)
				maxy2=dr2.y2;
			//System.out.println("x1,y1="+minx2+","+miny2+"    x2,y2="+maxx2+","+maxy2);
		}
		if(dr2 instanceof Rect){
			minx2=dr2.x1;
			miny2=dr2.y1;
			maxx2=dr2.x2;
			maxy2=dr2.y2;
		}
		if(dr2 instanceof Oval){
			minx2=dr2.x1;
			miny2=dr2.y1;
			maxx2=dr2.x2;
			maxy2=dr2.y2;
		}
		if(dr2 instanceof Circle){
			minx2=dr2.x1;
			miny2=dr2.y1;
			if((dr2.x2-dr2.x1)>(dr2.y2-dr2.y1)){
				maxx2=dr2.x2;
				maxy2=dr2.y1+dr2.x2-dr2.x1;
			}
		}
		if(dr2 instanceof Polygon){
			int size=dr2.list.size();
			for(int i=0;i<size;i++){
				Point p=dr2.list.get(i);
				if(minx2>p.x)
					minx2=p.x;
				if(miny2>p.y)
					miny2=p.y;
				if(maxx2<p.x)
					maxx2=p.x;
				if(maxy2<p.y)
					maxy2=p.y;
			}
		}
		/*
		Point p=new Point();
		Point p1=new Point(minx2,miny2);
		Point p2=new Point(maxx2,maxy2);
		p.x=minx1;
		p.y=miny1;
		if(checkInRect(p,p1,p2))
			return true;		
		p.x=maxx1;
		p.y=maxy1;
		if(checkInRect(p,p1,p2))
			return true;
		p.x=minx1;
		p.y=maxy1;
		if(checkInRect(p,p1,p2))
			return true;		
		p.x=maxx1;
		p.y=miny1;
		if(checkInRect(p,p1,p2))
			return true;
		
		
		Point p12=new Point(minx1,miny1);
		Point p22=new Point(maxx1,maxy1);
		p.x=minx2;
		p.y=miny2;
		if(checkInRect(p,p12,p22))
			return true;		
		p.x=maxx2;
		p.y=maxy2;
		if(checkInRect(p,p12,p22))
			return true;
		p.x=minx2;
		p.y=maxy2;
		if(checkInRect(p,p12,p22))
			return true;		
		p.x=maxx2;
		p.y=miny2;
		if(checkInRect(p,p12,p22))
			return true;
			*/
		//System.out.println("not in11111");
		Rect rect1=new Rect();
		rect1.x1=minx1;
		rect1.y1=miny1;
		rect1.x2=maxx1;
		rect1.y2=maxy1;
		Rect rect2=new Rect();
		rect2.x1=minx2;
		rect2.y1=miny2;
		rect2.x2=maxx2;
		rect2.y2=maxy2;
		if(isOverlap(rect1,rect2)){
			//System.out.println("overlap");
			return true;
		}
		return false;
	}
	static boolean isOverlap(Rect rc1, Rect rc2)
	{
	    if (rc1.x2> rc2.x1 &&
	        rc2.x2 > rc1.x1 &&
	        rc1.y2 > rc2.y1 &&
	        rc2.y2 > rc1.y1
	       )
	        return true;
	    else
	    return false;
	}
}
