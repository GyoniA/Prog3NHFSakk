package sakk;

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

public class Kiraly extends Babu{
	private static final long serialVersionUID = 1L;
	public Kiraly(int i, int j, boolean b, Tabla tabla) {
		super(i, j, b, tabla);
	}

	@Override
	public boolean HovaLephet() {
		lepesiLehetosegek.clear();
		utesiLehetosegek.clear();
		int x = (int)pozicio.getX();
		int y = (int)pozicio.getY();
		LinkedList<Point> iranyok = new LinkedList<Point>();
		iranyok.add(new Point(x, y+1));
		iranyok.add(new Point(x, y-1));
		iranyok.add(new Point(x+1, y+1));
		iranyok.add(new Point(x+1, y-1));
		iranyok.add(new Point(x+1, y));
		iranyok.add(new Point(x-1, y+1));
		iranyok.add(new Point(x-1, y-1));
		iranyok.add(new Point(x-1, y));
		if (feherE) {
			for (Point p : iranyok) {
				if (TablanVanE(p)) {
					if(!tab.GetFeherBabuk().containsKey(p)) {
						if(!tab.GetFeketeBabuk().containsKey(p)) {
							lepesiLehetosegek.add(p);
						} else {
							utesiLehetosegek.add(p);
						}
					}
				}
			}
			//sáncolás
			if (!lepettEMar) {
				Babu jobbBastya = tab.GetFeherBabuk().get(new Point(7, 7));
				if (jobbBastya != null && !jobbBastya.lepettEMar) {
					jobbBastya.HovaLephet();
					if (jobbBastya.GetLepesiLehetosegek().contains(new Point(5, 7))) {
						lepesiLehetosegek.add(new Point(6, 7));
					}
				}
			
				Babu ballBastya = tab.GetFeherBabuk().get(new Point(0, 7));
				if (ballBastya != null && !ballBastya.lepettEMar) {
					ballBastya.HovaLephet();
					if (ballBastya.GetLepesiLehetosegek().contains(new Point(3, 7))) {
						lepesiLehetosegek.add(new Point(2, 7));
					}
				}
			}
			
		} else {
			for (Point p : iranyok) {
				if (TablanVanE(p)) {
					if(!tab.GetFeketeBabuk().containsKey(p)) {
						if(!tab.GetFeherBabuk().containsKey(p)) {
							lepesiLehetosegek.add(p);
						} else {
							utesiLehetosegek.add(p);
						}
					}
				}
			}
			//sáncolás
			if (!lepettEMar) {
				Babu jobbBastya = tab.GetFeketeBabuk().get(new Point(7, 0));
				if (jobbBastya != null && !jobbBastya.lepettEMar) {
					jobbBastya.HovaLephet();
					if (jobbBastya.GetLepesiLehetosegek().contains(new Point(5, 0))) {
						lepesiLehetosegek.add(new Point(6, 0));
					}
				}
			
				Babu ballBastya = tab.GetFeketeBabuk().get(new Point(0, 0));
				if (ballBastya != null && !ballBastya.lepettEMar) {
					ballBastya.HovaLephet();
					if (ballBastya.GetLepesiLehetosegek().contains(new Point(3, 0))) {
						lepesiLehetosegek.add(new Point(2, 0));
					}
				}
			}
		}
		
		
		return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty();
	}

	@Override
	public Image GetImage() {
		if (feherE) {
			return tab.GetKep(0);
		} else {
			return tab.GetKep(1);
		}
	}
	
	public boolean Lepes(int ujX, int ujY) {
		Point celpont = new Point(ujX, ujY);
		if(lepesiLehetosegek.contains(celpont)) {//megnezi hogy tud-e lepni a megadott helyre
			if(feherE) {
				tab.GetFeherBabuk().put(celpont, this);
				tab.GetFeherBabuk().remove(pozicio);
				if (!lepettEMar) {
					if(ujX == 6 && ujY == 7) {
						Babu jobbBastya = tab.GetFeherBabuk().get(new Point(7, 7));
						tab.GetFeherBabuk().put(new Point(5, 7), jobbBastya);
						tab.GetFeherBabuk().remove(new Point(7, 7));
						jobbBastya.pozicio = new Point(5, 7);
					} else if (ujX == 2 && ujY == 7) {
						Babu ballBastya = tab.GetFeherBabuk().get(new Point(0, 7));
						tab.GetFeherBabuk().put(new Point(3, 7), ballBastya);
						tab.GetFeherBabuk().remove(new Point(0, 7));
						ballBastya.pozicio = new Point(3, 7);
					}
				}
			} else {
				tab.GetFeketeBabuk().put(celpont, this);
				tab.GetFeketeBabuk().remove(pozicio);
				if (!lepettEMar) {
					if(ujX == 6 && ujY == 0) {
						Babu jobbBastya = tab.GetFeketeBabuk().get(new Point(7, 0));
						tab.GetFeketeBabuk().put(new Point(5, 0), jobbBastya);
						tab.GetFeketeBabuk().remove(new Point(7, 0));
						jobbBastya.pozicio = new Point(5, 0);
					} else if (ujX == 2 && ujY == 0) {
						Babu ballBastya = tab.GetFeketeBabuk().get(new Point(0, 0));
						tab.GetFeketeBabuk().put(new Point(3, 0), ballBastya);
						tab.GetFeketeBabuk().remove(new Point(0, 0));
						ballBastya.pozicio = new Point(3, 0);
					}
				}
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
