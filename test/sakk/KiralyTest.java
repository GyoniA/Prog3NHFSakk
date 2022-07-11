package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class KiralyTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolIsTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu feketeKiraly = tab.feketeKiraly;
		Babu feherKiraly = tab.feherKiraly;
		//mert alapból be van blokkolva a kiraly
		assertFalse("feherKiraly alapteszt", feherKiraly.HovaLephet());
		assertFalse("feketeKiraly alapteszt", feketeKiraly.HovaLephet());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeKiraly = tab.feketeKiraly;
		Babu feherKiraly = tab.feherKiraly;
		
		assertTrue("feher tude lepni teszt", feherKiraly.HovaLephet());
		assertTrue(feherKiraly.Lepes(5, 7));
		assertEquals("feher lepes teszt", new Point(5, 7), feherKiraly.pozicio);
		
		assertTrue("fekete tude lepni teszt", feketeKiraly.HovaLephet());
		assertTrue(feketeKiraly.Lepes(3, 1));
		assertEquals("fekete lepes teszt", new Point(3, 1), feketeKiraly.pozicio);
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeKiraly = tab.feketeKiraly;
		Babu feherGyalog = new Gyalog(4, 1, true, tab);
		tab.feherBabuk.put(new Point(4, 1), feherGyalog);
		
		assertTrue("fekete tude lepni teszt", feketeKiraly.HovaLephet());
		
		assertTrue(feketeKiraly.Lepes(4, 1));
		assertEquals("fekete utes teszt", new Point(4, 1), feketeKiraly.pozicio);
		assertNull("feher leutve teszt", tab.GetFeherBabuk().get(new Point(4, 1)));
	}
	
	@Test
	public void testSancolas() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feherKiraly = tab.feherKiraly;
		Babu feherBastya = new Bastya(7, 7, true, tab);
		tab.feherBabuk.put(new Point(7, 7), feherBastya);
		
		assertTrue("feherKiraly tude lepni teszt", feherKiraly.HovaLephet());
		
		assertTrue(feherKiraly.Lepes(6, 7));
		assertEquals("feherKiraly lepes teszt", new Point(6, 7), feherKiraly.pozicio);
		assertEquals("feherBastya lepes teszt", new Point(5, 7), feherBastya.pozicio);
		
	}
}

