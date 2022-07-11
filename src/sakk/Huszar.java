package sakk;

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

public class Huszar extends Babu{
	private static final long serialVersionUID = 1L;
	public Huszar(int i, int j, boolean b, Tabla tabla) {
		super(i, j, b, tabla);
	}

	@Override
	public boolean HovaLephet() {
		lepesiLehetosegek.clear();
		utesiLehetosegek.clear();
		int x = (int)pozicio.getX();
		int y = (int)pozicio.getY();
		LinkedList<Point> iranyok = new LinkedList<Point>();
		iranyok.add(new Point(x+1, y+2));
		iranyok.add(new Point(x+1, y-2));
		iranyok.add(new Point(x+2, y+1));
		iranyok.add(new Point(x+2, y-1));
		iranyok.add(new Point(x-1, y+2));
		iranyok.add(new Point(x-1, y-2));
		iranyok.add(new Point(x-2, y+1));
		iranyok.add(new Point(x-2, y-1));
		
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
		}
		return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty();
	}

	@Override
	public Image GetImage() {
		if (feherE) {
			return tab.GetKep(6);
		} else {
			return tab.GetKep(7);
		}
	}
	
}