package sakk;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class RobotJatekos {
	private boolean feherE;
	private Tabla tab;
	
	public RobotJatekos(boolean szin, Tabla t) {
		feherE = szin;
		tab = t;
	}
	
	public void SetTabla(Tabla t) {
		tab = t;
	}
	
	public void LepesGeneralas() {
		Random rand = new Random();
		if (feherE) {
			Object feherek[] = tab.GetFeherBabuk().values().toArray();
			for (int i = 0; i < feherek.length; i++) {
				Babu b = (Babu)feherek[i];
				b.HovaLephet();
				tab.LepesLegalizalas(b);
				LinkedList<Point> ut = b.GetUtesiLehetosegek();
				if (!ut.isEmpty()) {
					Point p = ut.get(rand.nextInt(ut.size()));
					b.Lepes((int)p.getX(), (int)p.getY());
					return;
				}
				LinkedList<Point> lep = b.GetLepesiLehetosegek();
				if (!lep.isEmpty()) {
					Point p = lep.get(rand.nextInt(lep.size()));
					b.Lepes((int)p.getX(), (int)p.getY());
					return;
				}
			}
		} else {
			Object feketek[] = tab.GetFeketeBabuk().values().toArray();
			for (int i = 0; i < feketek.length; i++) {
				Babu b = (Babu)feketek[i];
				b.HovaLephet();
				tab.LepesLegalizalas(b);
				LinkedList<Point> ut = b.GetUtesiLehetosegek();
				if (!ut.isEmpty()) {
					Point p = ut.get(rand.nextInt(ut.size()));
					b.Lepes((int)p.getX(), (int)p.getY());
					return;
				}
				LinkedList<Point> lep = b.GetLepesiLehetosegek();
				if (!lep.isEmpty()) {
					Point p = lep.get(rand.nextInt(lep.size()));
					b.Lepes((int)p.getX(), (int)p.getY());
					return;
				}
			}
		}
	}
}