package sakk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Jatek extends Thread {
	private static JFrame tb;
	private Tabla tab;
	private Babu kivalasztott = null;
	private volatile boolean feherJonE = true;
	private boolean feherJonEBackup = true;
	private volatile int jatekmod;
	private JTextField kijon;
	private Ora feherOra;
	private JTextField feherIdo;
	private Ora feketeOra;
	private JTextField feketeIdo;
	private volatile boolean megyAJatek;
	private RobotJatekos rob1;
	private RobotJatekos rob2;
	
	private class RajzoloPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		@Override
		public void paint(Graphics g) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if ((i+j)%2 == 0) {	
						g.setColor(new Color(255, 248, 189));
					}
					else {
						g.setColor(new Color(17, 92, 30));
					}
					g.fillRect(i*100, j*100, 100, 100);
					Babu b = tab.GetFeherBabuk().get(new Point(i, j));
					if(b == null) {
						b = tab.GetFeketeBabuk().get(new Point(i, j));
					}
					if (b != null) {
						if (b == kivalasztott) {
							g.setColor(new Color(245, 235, 49));
							g.fillOval(i*100, j*100, 100, 100);
						} else if(kivalasztott != null && kivalasztott.GetUtesiLehetosegek().contains(new Point(i, j))) {
							g.setColor(new Color(255, 0, 0));
							g.fillOval(i*100, j*100, 100, 100);
						}
						g.drawImage(b.GetImage(), i*100, j*100, this);
					} else if(kivalasztott != null && kivalasztott.GetLepesiLehetosegek().contains(new Point(i, j))) {
						g.setColor(new Color(130, 230, 255));
						g.fillOval(i*100+30, j*100+30, 40, 40);
					}
				}
			}
		}
	}
	
	private class EgerListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!megyAJatek) {
				return;
			}
			if (kivalasztott != null) {
				if(kivalasztott.Lepes(e.getX()/100, e.getY()/100)) {
					feherJonE = !feherJonE;
					if (feherJonE) {
						kijon.setText("A fehér játékos jön");
						feherOra.Indit();
						feketeOra.Szunet();
					} else {
						kijon.setText("A fekete játékos jön");
						feketeOra.Indit();
						feherOra.Szunet();
					}
				}
				tb.repaint();
				VegeVanMar();
				kivalasztott = null;
			} else {
				Point p = new Point(e.getX()/100, e.getY()/100);
				if (feherJonE) {
					if (tab.GetFeherBabuk().containsKey(p)) {
						kivalasztott = tab.GetFeherBabuk().get(p);
						if(kivalasztott != null && kivalasztott.HovaLephet()) {
							tab.LepesLegalizalas(kivalasztott);
						} else {
							kivalasztott = null;
						}
					}
				} else {
					if (tab.GetFeketeBabuk().containsKey(p)) {
						kivalasztott = tab.GetFeketeBabuk().get(p);
						if(kivalasztott != null && kivalasztott.HovaLephet()) {
							tab.LepesLegalizalas(kivalasztott);
						} else {
							kivalasztott = null;
						}
					}
				}
				if (kivalasztott != null && kivalasztott.GetLepesiLehetosegek().isEmpty() && kivalasztott.GetUtesiLehetosegek().isEmpty()) {
					kivalasztott = null;
				}
				tb.repaint();
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	private class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			String filenev = "sakk.ser";
			if (command.equals("Mentes")) {
				ObjectOutputStream out;
				try {
					out = new ObjectOutputStream(new FileOutputStream(filenev));
					out.writeObject(tab);
					feherJonEBackup = feherJonE;
					feherOra.IdoMentes();
					feketeOra.IdoMentes();
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} else if(command.equals("Visszatoltes")){
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(new FileInputStream(filenev));
					tab = (Tabla)in.readObject();
					rob1.SetTabla(tab);
					rob2.SetTabla(tab);
					feherJonE = feherJonEBackup;
					feherOra.IdoVisszaallitas();
					feketeOra.IdoVisszaallitas();
					if (feherJonE) {
						kijon.setText("A fehér játékos jön");
					} else {
						kijon.setText("A fekete játékos jön");
					}
					tb.repaint();
					in.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}
			}
		}
	}
	
	private class JatekmodActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			jatekmod = Integer.parseInt(e.getActionCommand());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Jatek j = new Jatek();
		tb = new JFrame("Sakk");
		tb.setMinimumSize(new Dimension(816, 884));
		Object[] options = {"Ember ember ellen", "Ember gép ellen", "Gép gép ellen"};
		j.jatekmod = JOptionPane.showOptionDialog(tb, "Válasszon játékmódot:", "Sakk", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		
		j.tab = new Tabla("SakkBabukPlaceholder.png");
		
		j.tab.BabukAlaphelyzetbe();
		
		JPanel tarolo = new JPanel(new BorderLayout());
		
		RajzoloPanel sakkPanel = j.new RajzoloPanel();
		sakkPanel.addMouseListener(j.new EgerListener());
		
		j.feherIdo = new JTextField("Feher játékos ideje: ", 30);
		j.feketeIdo = new JTextField("Fekete játékos ideje: ", 30);
		j.feherIdo.setEditable(false);
		j.feketeIdo.setEditable(false);
		j.feherOra = new Ora(true, 300, j, j.feherIdo);
		j.feketeOra = new Ora(false, 300, j, j.feketeIdo);
		j.feherOra.start();
		j.feketeOra.start();
		
		tarolo.add(sakkPanel, BorderLayout.CENTER);
		JPanel alsoSzovegPanel = new JPanel();
		alsoSzovegPanel.setLayout(new BorderLayout());
		j.kijon = new JTextField("");
		j.kijon.setColumns(10);
		j.kijon.setEditable(false);
		if (j.feherJonE) {
			j.kijon.setText("A fehér játékos jön");
			j.feherOra.Indit();
			j.feketeOra.Szunet();
		} else {
			j.kijon.setText("A fekete játékos jön");
			j.feketeOra.Indit();
			j.feherOra.Szunet();
		}
		
		alsoSzovegPanel.add(j.feherIdo, BorderLayout.LINE_START);
		alsoSzovegPanel.add(j.kijon, BorderLayout.CENTER);
		alsoSzovegPanel.add(j.feketeIdo, BorderLayout.LINE_END);
		
		tarolo.add(alsoSzovegPanel, BorderLayout.PAGE_END);
		
		tb.add(tarolo);
		
		JMenu menu = new JMenu("Opciók");
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Mentés", UIManager.getIcon("FileView.floppyDriveIcon"));
		menuItem.setActionCommand("Mentes");
		MenuActionListener mal = j.new MenuActionListener();
		menuItem.addActionListener(mal);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Visszatöltés", UIManager.getIcon("FileView.hardDriveIcon"));
		menuItem.setActionCommand("Visszatoltes");
		menuItem.addActionListener(mal);
		menu.add(menuItem);

		JatekmodActionListener jal = j.new JatekmodActionListener();
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem;
		rbMenuItem = new JRadioButtonMenuItem("Ember ember ellen");
		rbMenuItem.setActionCommand("0");
		rbMenuItem.addActionListener(jal);
		if (j.jatekmod == 0) {
			rbMenuItem.setSelected(true);
		}
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Ember gép ellen", UIManager.getIcon("FileView.computerIcon"));
		rbMenuItem.setActionCommand("1");
		rbMenuItem.addActionListener(jal);
		if (j.jatekmod == 1) {
			rbMenuItem.setSelected(true);
		}
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Gép gép ellen", UIManager.getIcon("FileView.computerIcon"));
		rbMenuItem.setActionCommand("2");
		rbMenuItem.addActionListener(jal);
		if (j.jatekmod == 2) {
			rbMenuItem.setSelected(true);
		}
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		tb.setJMenuBar(menuBar);
	
		tb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tb.setResizable(false);
		tb.pack();
		tb.setVisible(true);
		j.start();
	}
	
	public void run() {
		rob1 = new RobotJatekos(true, tab);
		rob2 = new RobotJatekos(false, tab);
		megyAJatek = true;
		while (megyAJatek) {
			try {
				if (jatekmod != 0) {
					
					if (jatekmod == 1) {
						if(!feherJonE) {
							rob2.LepesGeneralas();
							feherJonE = true;
							kijon.setText("A fehér játékos jön");
							feherOra.Indit();
							feketeOra.Szunet();
							tb.repaint();
							VegeVanMar();
						}
					} else {
						if(feherJonE) {
							rob1.LepesGeneralas();
							feherJonE = false;
							kijon.setText("A fekete játékos jön");
							feketeOra.Indit();
							feherOra.Szunet();
							tb.repaint();
						} else {
							rob2.LepesGeneralas();
							feherJonE = true;
							kijon.setText("A fehér játékos jön");
							feherOra.Indit();
							feketeOra.Szunet();
							tb.repaint();
						}
						VegeVanMar();
					}
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void ElfogyottAzIdo(boolean feherE) {
		if(feherE) {
			System.out.println("Fekete játékos nyert!\n(elfogyott a fehér játékos ideje)");
			megyAJatek = false;
			JOptionPane.showMessageDialog(tb, "Fekete játékos nyert!\n(elfogyott a fehér játékos ideje)");
			
		} else {
			System.out.println("Fehér játékos nyert!\n(elfogyott a fekete játékos ideje)");
			megyAJatek = false;
			JOptionPane.showMessageDialog(tb, "Fehér játékos nyert!\n(elfogyott a fekete játékos ideje)");
		}
	}
	
	public void VegeVanMar() {
		int allas = tab.VesztettE(true);
		if(allas == 1) {
			System.out.println("Fekete játékos nyert!");
			feherOra.Szunet();
			feketeOra.Szunet();
			megyAJatek = false;
			JOptionPane.showMessageDialog(tb, "Fekete játékos nyert!");
			
		} else if (allas == 2) {
			System.out.println("Döntetlen!");
			feherOra.Szunet();
			feketeOra.Szunet();
			megyAJatek = false;
			JOptionPane.showMessageDialog(tb, "Döntetlen!");
			
		} else {
			allas = tab.VesztettE(false);
			if(allas == 1) {
				System.out.println("Fehér játékos nyert!");
				feherOra.Szunet();
				feketeOra.Szunet();
				megyAJatek = false;
				JOptionPane.showMessageDialog(tb, "Fehér játékos nyert!");
				
			} else if (allas == 2) {
				System.out.println("Döntetlen!");
				feherOra.Szunet();
				feketeOra.Szunet();
				megyAJatek = false;
				JOptionPane.showMessageDialog(tb, "Döntetlen!");
				
			}
		}
	}
}
