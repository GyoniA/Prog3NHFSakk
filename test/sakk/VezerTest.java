package sakk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class VezerTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testAlaphelyzetbolIsTudLepni() {
		tab.BabukAlaphelyzetbe();
		Babu feketeVezer = tab.GetFeketeBabuk().get(new Point(3, 0));
		Babu feherVezer = tab.GetFeherBabuk().get(new Point(3, 7));
		//mert alapból be van blokkolva a futó
		assertFalse("feherVezer alapteszt", feherVezer.HovaLephet());
		assertFalse("feketeVezer alapteszt", feketeVezer.HovaLephet());
	}

	@Test
	public void testEloreLepes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeVezer = new Vezer(3, 0, false, tab);
		Babu feherVezer = new Vezer(3, 7, true, tab);
		
		tab.feketeBabuk.put(new Point(3, 0), feketeVezer);
		tab.feherBabuk.put(new Point(3, 7), feherVezer);
		
		assertTrue("feher tude lepni teszt", feherVezer.HovaLephet());
		assertTrue(feherVezer.Lepes(3, 3));
		assertEquals("feher lepes teszt", new Point(3, 3), feherVezer.pozicio);
		
		assertTrue("fekete tude lepni teszt", feketeVezer.HovaLephet());
		assertTrue(feketeVezer.Lepes(7, 4));
		assertEquals("fekete lepes teszt", new Point(7, 4), feketeVezer.pozicio);
	}
	
	@Test
	public void testUtes() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeVezer = new Vezer(3, 0, false, tab);
		Babu feherVezer = new Vezer(3, 7, true, tab);
		
		tab.feketeBabuk.put(new Point(3, 0), feketeVezer);
		tab.feherBabuk.put(new Point(3, 7), feherVezer);
		
		assertTrue("feher tude lepni teszt", feherVezer.HovaLephet());
		
		assertTrue(feherVezer.Lepes(3, 0));
		assertEquals("feher utes teszt", new Point(3, 0), feherVezer.pozicio);
		assertNull("fekete leutve teszt", tab.GetFeketeBabuk().get(new Point(3, 0)));
	}
}

