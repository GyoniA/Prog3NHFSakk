package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class GyalogTest {


	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolKettotIsTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu feketeGyalog = tab.GetFeketeBabuk().get(new Point(1, 1));
		Babu feherGyalog = tab.GetFeherBabuk().get(new Point(1, 6));
		assertTrue("feher gyalog tude lepni teszt", feherGyalog.HovaLephet());
		assertEquals("feher gyalog ket helyre tude lepni teszt", 2, feherGyalog.lepesiLehetosegek.size());
		assertTrue("fekete gyalog tude lepni teszt", feketeGyalog.HovaLephet());
		assertEquals("fekete gyalog ket helyre tude lepni teszt", 2, feketeGyalog.lepesiLehetosegek.size());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeGyalog = new Gyalog(1, 1, false, tab);
		Babu feherGyalog = new Gyalog(1, 6, true, tab);
		
		tab.feketeBabuk.put(new Point(1, 1), feketeGyalog);
		tab.feherBabuk.put(new Point(1, 6), feherGyalog);
		
		assertTrue("feher tude lepni teszt", feherGyalog.HovaLephet());
		assertTrue(feherGyalog.Lepes(1, 4));
		assertEquals("feher lepes teszt", new Point(1, 4), feherGyalog.pozicio);
		
		assertTrue("fekete tude lepni teszt", feketeGyalog.HovaLephet());
		assertTrue(feketeGyalog.Lepes(1, 2));
		assertEquals("fekete lepes teszt", new Point(1, 2), feketeGyalog.pozicio);
		
		
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeGyalog = new Gyalog(2, 5, false, tab);
		Babu feherGyalog = new Gyalog(1, 6, true, tab);
		
		tab.feketeBabuk.put(new Point(2, 5), feketeGyalog);
		tab.feherBabuk.put(new Point(1, 6), feherGyalog);
		
		
		assertTrue("feher tude lepni teszt", feherGyalog.HovaLephet());
		
		assertTrue(feherGyalog.Lepes(2, 5));
		assertEquals("feher utes teszt", new Point(2, 5), feherGyalog.pozicio);
		assertNull("fekete leutve teszt", tab.GetFeketeBabuk().get(new Point(2, 5)));
	}
	
	@Test
	public void testVezerreValas() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feherGyalog = new Gyalog(1, 1, true, tab);
		
		tab.feherBabuk.put(new Point(1, 1), feherGyalog);
		
		
		assertTrue("feher tude lepni teszt", feherGyalog.HovaLephet());
		
		assertTrue(feherGyalog.Lepes(1, 0));
		assertTrue("feher vezerre valtozotte", tab.GetFeherBabuk().get(new Point(1, 0)).getClass().equals(Vezer.class));
	}
}

