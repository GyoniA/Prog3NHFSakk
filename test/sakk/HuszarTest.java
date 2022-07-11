package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class HuszarTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolIsTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu feketeHuszar = tab.GetFeketeBabuk().get(new Point(1, 0));
		Babu feherHuszar = tab.GetFeherBabuk().get(new Point(1, 7));
		assertTrue("feher huszar atugras teszt", feherHuszar.HovaLephet());
		assertTrue("fekete huszar tude atugras teszt", feketeHuszar.HovaLephet());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeHuszar = new Huszar(1, 0, false, tab);
		Babu feherHuszar = new Huszar(1, 7, true, tab);
		
		tab.feketeBabuk.put(new Point(1, 0), feketeHuszar);
		tab.feherBabuk.put(new Point(1, 7), feherHuszar);
		
		tab.feketeBabuk.put(new Point(1, 0), feketeHuszar);
		tab.feherBabuk.put(new Point(1, 7), feherHuszar);
		
		assertTrue("feher tude lepni teszt", feherHuszar.HovaLephet());
		assertTrue(feherHuszar.Lepes(2, 5));
		assertEquals("feher lepes teszt", new Point(2, 5), feherHuszar.pozicio);
		
		assertTrue("fekete tude lepni teszt", feketeHuszar.HovaLephet());
		assertTrue(feketeHuszar.Lepes(2, 2));
		assertEquals("fekete lepes teszt", new Point(2, 2), feketeHuszar.pozicio);
		
		
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeHuszar = new Huszar(2, 5, false, tab);
		Babu feherHuszar = new Huszar(1, 7, true, tab);
		
		tab.feketeBabuk.put(new Point(2, 5), feketeHuszar);
		tab.feherBabuk.put(new Point(1, 7), feherHuszar);
		
		
		assertTrue("feher tude lepni teszt", feherHuszar.HovaLephet());
		
		assertTrue(feherHuszar.Lepes(2, 5));
		assertEquals("feher utes teszt", new Point(2, 5), feherHuszar.pozicio);
		assertNull("fekete leutve teszt", tab.GetFeketeBabuk().get(new Point(2, 5)));
	}
}

