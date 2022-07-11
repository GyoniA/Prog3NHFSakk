package sakk;

import java.awt.Image;
import java.awt.Point;

public class Bastya extends Babu{
	private static final long serialVersionUID = 1L;

	public Bastya(int i, int j, boolean b, Tabla tabla) {
		super(i, j, b, tabla);
	}

	@Override
	public boolean HovaLephet() {
		lepesiLehetosegek.clear();
		utesiLehetosegek.clear();
		int x = (int)pozicio.getX();
		int y = (int)pozicio.getY();
		Point poz;
		boolean mehet;
		int iranyok[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		if (feherE) {
			for (int[] irany : iranyok) {
				mehet = true;
				poz = new Point(x, y);
				poz.translate(irany[0], irany[1]);
				while (TablanVanE(poz) && mehet) {
					if (tab.GetFeketeBabuk().containsKey(poz)) {
						utesiLehetosegek.add(new Point(poz));
						mehet = false;
					} else if (tab.GetFeherBabuk().containsKey(poz)) {
						mehet = false;
					} else {
						lepesiLehetosegek.add(new Point(poz));
					}
					poz.translate(irany[0], irany[1]);
				}
			}
		} else {
			for (int[] irany : iranyok) {
				mehet = true;
				poz = new Point(x, y);
				poz.translate(irany[0], irany[1]);
				while (TablanVanE(poz) && mehet) {
					if (tab.GetFeherBabuk().containsKey(poz)) {
						utesiLehetosegek.add(new Point(poz));
						mehet = false;
					} else if (tab.GetFeketeBabuk().containsKey(poz)) {
						mehet = false;
					} else {
						lepesiLehetosegek.add(new Point(poz));
					}
					poz.translate(irany[0], irany[1]);
				}
			}
		}
		
		return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty();
	}
	
	@Override
	public Image GetImage() {
		if (feherE) {
			return tab.GetKep(8);
		} else {
			return tab.GetKep(9);
		}
	}

}