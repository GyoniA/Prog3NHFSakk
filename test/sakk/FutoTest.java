package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class FutoTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolNemTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu jobbFeherFuto = tab.GetFeherBabuk().get(new Point(5, 7));
		Babu balFeherFuto = tab.GetFeherBabuk().get(new Point(2, 7));
		Babu jobbFeketeFuto = tab.GetFeketeBabuk().get(new Point(5, 0));
		Babu balFeketeFuto = tab.GetFeketeBabuk().get(new Point(2, 0));
		//mert alapból be van blokkolva a futó
		assertFalse("jobbFeherFuto alapteszt", jobbFeherFuto.HovaLephet());
		assertFalse("balFeherFuto alapteszt", balFeherFuto.HovaLephet());
		assertFalse("jobbFeketeFuto alapteszt", jobbFeketeFuto.HovaLephet());
		assertFalse("balFeketeFuto alapteszt", balFeketeFuto.HovaLephet());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		tab.feketeBabuk.put(new Point(2, 0), new Futo(2, 0, false, tab));
		tab.feherBabuk.put(new Point(2, 7), new Futo(2, 7, true, tab));
		
		assertTrue("feher tude lepni teszt", tab.GetFeherBabuk().get(new Point(2, 7)).HovaLephet());
		assertTrue("fekete tude lepni teszt", tab.GetFeketeBabuk().get(new Point(2, 0)).HovaLephet());
		
		assertTrue(tab.GetFeherBabuk().get(new Point(2, 7)).Lepes(5, 4));
		assertEquals("feher lepes teszt", new Point(5, 4), tab.GetFeherBabuk().get(new Point(5, 4)).pozicio);
		
		assertTrue(tab.GetFeketeBabuk().get(new Point(2, 0)).Lepes(5, 3));
		assertEquals("fekete lepes teszt", new Point(5, 3), tab.GetFeketeBabuk().get(new Point(5, 3)).pozicio);
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		tab.feketeBabuk.put(new Point(5, 4), new Futo(5, 4, false, tab));
		tab.feherBabuk.put(new Point(2, 7), new Futo(2, 7, true, tab));
		
		assertTrue("feher tude lepni teszt", tab.GetFeherBabuk().get(new Point(2, 7)).HovaLephet());
		
		assertTrue(tab.GetFeherBabuk().get(new Point(2, 7)).Lepes(5, 4));
		assertEquals("feher utes teszt", new Point(5, 4), tab.GetFeherBabuk().get(new Point(5, 4)).pozicio);
		assertNull("fekete leutve teszt", tab.GetFeketeBabuk().get(new Point(5, 4)));
	}
}

