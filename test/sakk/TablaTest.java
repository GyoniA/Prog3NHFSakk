package sakk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class TablaTest {
	Tabla tab;
	
	@Before
	public void setUp() {
		tab = new Tabla("SakkBabukPlaceholder.png");
	}
	
	@Test
	public void testLepesLegalizalas() {
		tab.feketeKiraly = new Kiraly(4, 0, false, tab);
		tab.feketeBabuk.put(new Point(4, 0), tab.feketeKiraly);
		tab.feherKiraly = new Kiraly(4, 7, true, tab);
		tab.feherBabuk.put(new Point(4, 7), tab.feherKiraly);
		
		Babu feketeVezer = new Vezer(1, 4, false, tab);
		Babu feherVezer = new Vezer(2, 5, true, tab);
		
		tab.feketeBabuk.put(new Point(1, 4), feketeVezer);
		tab.feherBabuk.put(new Point(2, 5), feherVezer);
		
		assertTrue("feher tude lepni teszt", feherVezer.HovaLephet());
		
		tab.LepesLegalizalas(feherVezer);
		tab.LepesLegalizalas(feketeVezer);
		
		assertFalse("feher nem tud ellepni a kiraly vedelmebol teszt", feherVezer.Lepes(7, 4));
	}

}
