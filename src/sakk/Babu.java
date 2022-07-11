package sakk;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

public abstract class Babu implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Point pozicio;
	protected boolean feherE;
	protected boolean lepettEMar = false;
	protected LinkedList<Point> lepesiLehetosegek = new LinkedList<Point>();
	protected LinkedList<Point> utesiLehetosegek = new LinkedList<Point>();
	protected Tabla tab;
	
	public Babu(int x, int y, boolean iFeherE, Tabla t) {
		tab = t;
		pozicio = new Point(x, y);
		feherE = iFeherE;
	}
	
	public static boolean TablanVanE(Point p) {
		return (p.getX() >= 0 && p.getX() < 8 && p.getY() >= 0 && p.getY() < 8);
	}
	
	public boolean GetFeherE() {
		return feherE;
	}
	
	public Point GetPozicio() {
		return pozicio;
	}
	
	public LinkedList<Point>  GetLepesiLehetosegek() {
		return lepesiLehetosegek;
	}
	
	public LinkedList<Point>  GetUtesiLehetosegek() {
		return utesiLehetosegek;
	}
	
	//visszadja a babu kepet, amit ki kell rajzolni
	public abstract Image GetImage();
	
	//ha nem lehet sehova se lepni hamissal ter vissza, beallitja a lepesiLehetosegek listat
	public abstract boolean HovaLephet();
	
	public boolean Lepes(int ujX, int ujY) {
		Point celpont = new Point(ujX, ujY);
		if(lepesiLehetosegek.contains(celpont)) {//megnezi hogy tud-e lepni a megadott helyre
			if(feherE) {
				tab.GetFeherBabuk().put(celpont, this);
				tab.GetFeherBabuk().remove(pozicio);
			} else {
				tab.GetFeketeBabuk().put(celpont, this);
				tab.GetFeketeBabuk().remove(pozicio);
			}
			
			pozicio = celpont;
			lepettEMar = true;
			return true;
		} else if (utesiLehetosegek.contains(celpont)) {//megnezi hogy tud-e utni
			if(feherE) {
				tab.GetFeherBabuk().put(celpont, this);
				tab.GetFeketeBabuk().remove(celpont);
				tab.GetFeherBabuk().remove(pozicio);
			} else {
				tab.GetFeketeBabuk().put(celpont, this);
				tab.GetFeherBabuk().remove(celpont);
				tab.GetFeketeBabuk().remove(pozicio);
			}
			pozicio = celpont;
			lepettEMar = true;
			return true;
		}
		return false; //nem tudott oda lepni a babu
	}
}
