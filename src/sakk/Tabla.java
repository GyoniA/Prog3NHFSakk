package sakk;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Tabla implements Serializable {
	private static final long serialVersionUID = 1L;
	protected HashMap<Point, Babu> feherBabuk = new HashMap<Point, Babu>();
	protected HashMap<Point, Babu> feketeBabuk = new HashMap<Point, Babu>();
	protected Kiraly feherKiraly;
	protected Kiraly feketeKiraly;
	protected String fileNeve;
	protected transient  Image kepek[] = new Image[12];
	
	public Tabla(String fileNev) {
		fileNeve = fileNev;
		BufferedImage kep = null;
		try {
			kep = ImageIO.read(new File(fileNeve));
		} catch (IOException e) {
			e.printStackTrace();
		}
		kepek = new Image[12];
		int k = 0;
		for (int i = 0; i < 1200; i+=200) {
			for (int j = 0; j < 400; j+=200) {
					kepek[k] = kep.getSubimage(i, j, 200, 200).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
					k++;
			}
		}
	}
	
	public void BabukAlaphelyzetbe() {
		feherBabuk.clear();
		feketeBabuk.clear();
		
		feketeBabuk.put(new Point(0, 0), new Bastya(0, 0, false, this));
		feketeBabuk.put(new Point(1, 0), new Huszar(1, 0, false, this));
		feketeBabuk.put(new Point(2, 0), new Futo(2, 0, false, this));
		feketeBabuk.put(new Point(3, 0), new Vezer(3, 0, false, this));
		feketeKiraly = new Kiraly(4, 0, false, this);
		feketeBabuk.put(new Point(4, 0), feketeKiraly);
		feketeBabuk.put(new Point(5, 0), new Futo(5, 0, false, this));
		feketeBabuk.put(new Point(6, 0), new Huszar(6, 0, false, this));
		feketeBabuk.put(new Point(7, 0), new Bastya(7, 0, false, this));
		for (int i = 0; i < 8; i++) {
			feketeBabuk.put(new Point(i, 1), new Gyalog(i, 1, false, this));
		}
		
		feherBabuk.put(new Point(0, 7), new Bastya(0, 7, true, this));
		feherBabuk.put(new Point(1, 7), new Huszar(1, 7, true, this));
		feherBabuk.put(new Point(2, 7), new Futo(2, 7, true, this));
		feherBabuk.put(new Point(3, 7), new Vezer(3, 7, true, this));
		feherKiraly = new Kiraly(4, 7, true, this);
		feherBabuk.put(new Point(4, 7), feherKiraly);
		feherBabuk.put(new Point(5, 7), new Futo(5, 7, true, this));
		feherBabuk.put(new Point(6, 7), new Huszar(6, 7, true, this));
		feherBabuk.put(new Point(7, 7), new Bastya(7, 7, true, this));
		for (int i = 0; i < 8; i++) {
			feherBabuk.put(new Point(i, 6), new Gyalog(i, 6, true, this));
		}
	}
	
	public Image GetKep(int index) {
		return kepek[index];
	}
	
	public HashMap<Point, Babu> GetFeherBabuk(){
		return feherBabuk;
	}
	
	public HashMap<Point, Babu> GetFeketeBabuk(){
		return feketeBabuk;
	}
	
	public void LepesLegalizalas(Babu temp) {
		boolean tempLepettE = temp.lepettEMar;
		LinkedList<Point> legalisLepesek = new LinkedList<Point>();
		for (Point poz : temp.lepesiLehetosegek) {
			HashMap<Point, Babu> feherMasolat = new HashMap<Point, Babu>(feherBabuk);
			HashMap<Point, Babu> feketeMasolat = new HashMap<Point, Babu>(feketeBabuk);
			Point tempPoz = new Point(temp.GetPozicio());
			temp.Lepes((int)poz.getX(), (int)poz.getY());
			boolean legalis = true;
			if (temp.GetFeherE()) {
				for (Babu b : feketeBabuk.values()) {
					b.HovaLephet();
					for (Point p : b.GetUtesiLehetosegek()) {
						if (p.equals(feherKiraly.GetPozicio())) {
							legalis = false;
						}
					}
				}
			} else {
				for (Babu b : feherBabuk.values()) {
					b.HovaLephet();
					for (Point p : b.GetUtesiLehetosegek()) {
						if (p.equals(feketeKiraly.GetPozicio())) {
							legalis = false;
						}
					}
				}
			}
			if (legalis) {
				legalisLepesek.add(poz);
			}
			feherBabuk = feherMasolat;
			feketeBabuk = feketeMasolat;
			temp.pozicio = tempPoz;
		}
		
		LinkedList<Point> legalisUtesek = new LinkedList<Point>();
		for (Point poz : temp.GetUtesiLehetosegek()) {
			HashMap<Point, Babu> feherMasolat = new HashMap<Point, Babu>(feherBabuk);
			HashMap<Point, Babu> feketeMasolat = new HashMap<Point, Babu>(feketeBabuk);
			Point tempPoz = new Point(temp.GetPozicio());
			temp.Lepes((int)poz.getX(), (int)poz.getY());
			boolean legalis = true;
			if (temp.GetFeherE()) {
				for (Babu b : feketeBabuk.values()) {
					b.HovaLephet();
					for (Point p : b.GetUtesiLehetosegek()) {
						if (p.equals(feherKiraly.GetPozicio())) {
							legalis = false;
						}
					}
				}
			} else {
				for (Babu b : feherBabuk.values()) {
					b.HovaLephet();
					for (Point p : b.GetUtesiLehetosegek()) {
						if (p.equals(feketeKiraly.GetPozicio())) {
							legalis = false;
						}
					}
				}
			}
			if (legalis) {
				legalisUtesek.add(poz);
			}
			feherBabuk = feherMasolat;
			feketeBabuk = feketeMasolat;
			temp.pozicio = tempPoz;
		}
		temp.lepesiLehetosegek = legalisLepesek;
		temp.utesiLehetosegek = legalisUtesek;
		temp.lepettEMar = tempLepettE;
	}
	
	public int VesztettE(boolean feherE) {
		if (feherBabuk.size() == 1 && feketeBabuk.size() == 1) {
			return 2;
		}
		if (feherE) {
			Object feherek[] = feherBabuk.values().toArray();
			for (int i = 0; i < feherek.length; i++) {
				Babu b = (Babu)feherek[i];
				b.HovaLephet();
				LepesLegalizalas(b);
				if (!b.GetLepesiLehetosegek().isEmpty() || !b.GetUtesiLehetosegek().isEmpty()) {
					return 0;//nem vesztett
				}
			}
			
			Object feketek[] = feketeBabuk.values().toArray();
			for (int i = 0; i < feketek.length; i++) {
				Babu b = (Babu)feketek[i];
				b.HovaLephet();
				LepesLegalizalas(b);
				if (b.GetUtesiLehetosegek().contains(feherKiraly.GetPozicio())) {
					return 1;//vesztett
				}
			}
		} else {
			Object feketek[] = feketeBabuk.values().toArray();
			for (int i = 0; i < feketek.length; i++) {
				Babu b = (Babu)feketek[i];
				b.HovaLephet();
				LepesLegalizalas(b);
				if (!b.GetLepesiLehetosegek().isEmpty() || !b.GetUtesiLehetosegek().isEmpty()) {
					return 0;//nem vesztett
				}
			}
			
			Object feherek[] = feherBabuk.values().toArray();
			for (int i = 0; i < feherek.length; i++) {
				Babu b = (Babu)feherek[i];
				b.HovaLephet();
				LepesLegalizalas(b);
				if (b.GetUtesiLehetosegek().contains(feketeKiraly.GetPozicio())) {
					return 1;//vesztett
				}
			}
		}
		return 2;//dontetlen
	}
	
    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException 
    {    
		aInputStream.defaultReadObject();
		BufferedImage kep = null;
		try {
			kep = ImageIO.read(new File(fileNeve));
		} catch (IOException e) {
			e.printStackTrace();
		}
		kepek = new Image[12];
		int k = 0;
		for (int i = 0; i < 1200; i+=200) {
			for (int j = 0; j < 400; j+=200) {
					kepek[k] = kep.getSubimage(i, j, 200, 200).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
					k++;
			}
		}
    }
}
