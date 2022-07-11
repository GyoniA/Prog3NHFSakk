package sakk;

import java.awt.Image;
import java.awt.Point;

public class Gyalog extends Babu{
	private static final long serialVersionUID = 1L;
	public Gyalog(int i, int j, boolean b, Tabla tabla) {
		super(i, j, b, tabla);
	}

	@Override
	public boolean HovaLephet() {
		lepesiLehetosegek.clear();
		utesiLehetosegek.clear();
		int x = (int)pozicio.getX();
		int y = (int)pozicio.getY();
		if (feherE) {
			Point balraFel = new Point(x-1, y-1);
			Point jobbraFel = new Point(x+1, y-1);
			Point egyetFel = new Point(x, y-1);
			if (TablanVanE(balraFel) && tab.GetFeketeBabuk().containsKey(balraFel)) {
				utesiLehetosegek.add(balraFel);
			}
			if (TablanVanE(jobbraFel) && tab.GetFeketeBabuk().containsKey(jobbraFel)) {
				utesiLehetosegek.add(jobbraFel);
			}
			if (TablanVanE(egyetFel) && !tab.GetFeketeBabuk().containsKey(egyetFel) && !tab.GetFeherBabuk().containsKey(egyetFel)) {
					lepesiLehetosegek.add(egyetFel);
			}	
			if (!lepettEMar) {	
				Point kettotFel = new Point(x, y-2);
				if (TablanVanE(kettotFel) && !tab.GetFeketeBabuk().containsKey(kettotFel) && !tab.GetFeherBabuk().containsKey(kettotFel) && !tab.GetFeketeBabuk().containsKey(egyetFel) && !tab.GetFeherBabuk().containsKey(egyetFel)) {
					lepesiLehetosegek.add(kettotFel);
				}
			}
		} else {
			Point balraLe = new Point(x-1, y+1);
			Point jobbraLe = new Point(x+1, y+1);
			Point egyetLe = new Point(x, y+1);
			if (TablanVanE(balraLe) && tab.GetFeherBabuk().containsKey(balraLe)) {
				utesiLehetosegek.add(balraLe);
			}
			if (TablanVanE(jobbraLe) && tab.GetFeherBabuk().containsKey(jobbraLe)) {
				utesiLehetosegek.add(jobbraLe);
			}
			if (TablanVanE(egyetLe) && !tab.GetFeketeBabuk().containsKey(egyetLe) && !tab.GetFeherBabuk().containsKey(egyetLe)) {
				lepesiLehetosegek.add(egyetLe);
			}	
			if (!lepettEMar) {	
				Point kettotLe = new Point(x, y+2);
				if (TablanVanE(kettotLe) && !tab.GetFeketeBabuk().containsKey(kettotLe) && !tab.GetFeherBabuk().containsKey(kettotLe) && !tab.GetFeketeBabuk().containsKey(egyetLe) && !tab.GetFeherBabuk().containsKey(egyetLe)) {
					lepesiLehetosegek.add(kettotLe);
				}
			}
		}
		return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty();
	}

	@Override
	public Image GetImage() {
		if (feherE) {
			return tab.GetKep(10);
		} else {
			return tab.GetKep(11);
		}
	}
	
	@Override
	public boolean Lepes(int ujX, int ujY) {
		boolean result = super.Lepes(ujX, ujY);
		if (feherE) {
			if (result && ujY == 0) {
				tab.GetFeherBabuk().put(new Point(ujX, ujY), new Vezer(ujX, ujY, feherE, tab));
			}
		} else {
			if (result && ujY == 7) {
				tab.GetFeketeBabuk().put(new Point(ujX, ujY), new Vezer(ujX, ujY, feherE, tab));
			}
		}
		return result;
	}
}
