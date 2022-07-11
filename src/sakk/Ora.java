package sakk;

import javax.swing.JTextField;

public class Ora extends Thread {
	private int ido;
	private int idoBackup;
	private boolean megyAzOra;
	private boolean feherE;
	private Jatek jat;
	private JTextField jtf;
	public Ora(boolean f, int i, Jatek j, JTextField jf) {
		feherE = f;
		ido = i;
		idoBackup = i;
		jat = j;
		jtf = jf;
		if (feherE) {
			jtf.setText("Feher játékos ideje: " + ido + "s");
		} else {
			jtf.setText("Fekete játékos ideje: " + ido + "s");
		}
		megyAzOra = false;
	}
	
	public void Szunet() {
		megyAzOra = false;
	}
	
	public void Indit() {
		megyAzOra = true;
	}
	
	public void IdoMentes() {
		idoBackup = ido;
	}
	
	public void IdoVisszaallitas() {
		ido = idoBackup;
	}
	
	public void run() {
		boolean vanMegIdo = true;
		while (vanMegIdo) {
			try {
				if (megyAzOra) {
					if(ido != 0) {
						ido--;
						if (feherE) {
							jtf.setText("Feher játékos ideje: " + ido + "s");
						} else {
							jtf.setText("Fekete játékos ideje: " + ido + "s");
						}
					} else {
						vanMegIdo = false;
						jat.ElfogyottAzIdo(feherE);
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
}
