package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class BastyaTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolNemTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu jobbFeherBastya = tab.GetFeherBabuk().get(new Point(7, 7));
		Babu balFeherBastya = tab.GetFeherBabuk().get(new Point(0, 7));
		Babu jobbFeketeBastya = tab.GetFeketeBabuk().get(new Point(7, 0));
		Babu balFeketeBastya = tab.GetFeketeBabuk().get(new Point(0, 0));
		//mert alapból be van blokkolva a bástya
		assertFalse("jobbFeherBastya alapteszt", jobbFeherBastya.HovaLephet());
		assertFalse("balFeherBastya alapteszt", balFeherBastya.HovaLephet());
		assertFalse("jobbFeketeBastya alapteszt", jobbFeketeBastya.HovaLephet());
		assertFalse("balFeketeBastya alapteszt", balFeketeBastya.HovaLephet());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		tab.feketeBabuk.put(new Point(0, 0), new Bastya(0, 0, false, tab));
		tab.feherBabuk.put(new Point(0, 7), new Bastya(0, 7, true, tab));
		
		assertTrue("feher tude lepni teszt", tab.GetFeherBabuk().get(new Point(0, 7)).HovaLephet());
		assertTrue("fekete tude lepni teszt", tab.GetFeketeBabuk().get(new Point(0, 0)).HovaLephet());
		
		assertTrue(tab.GetFeherBabuk().get(new Point(0, 7)).Lepes(0, 4));
		assertEquals("feher lepes teszt", new Point(0, 4), tab.GetFeherBabuk().get(new Point(0, 4)).pozicio);
		
		assertTrue(tab.GetFeketeBabuk().get(new Point(0, 0)).Lepes(0, 3));
		assertEquals("fekete lepes teszt", new Point(0, 3), tab.GetFeketeBabuk().get(new Point(0, 3)).pozicio);
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		tab.feketeBabuk.put(new Point(0, 0), new Bastya(0, 0, false, tab));
		tab.feherBabuk.put(new Point(0, 7), new Bastya(0, 7, true, tab));
		
		assertTrue("feher tude lepni teszt", tab.GetFeherBabuk().get(new Point(0, 7)).HovaLephet());
		assertTrue("fekete tude lepni teszt", tab.GetFeketeBabuk().get(new Point(0, 0)).HovaLephet());
		
		assertTrue(tab.GetFeherBabuk().get(new Point(0, 7)).Lepes(0, 0));
		assertEquals("feher utes teszt", new Point(0, 0), tab.GetFeherBabuk().get(new Point(0, 0)).pozicio);
		assertNull("fekete leutve teszt", tab.GetFeketeBabuk().get(new Point(0, 0)));
	}
}
