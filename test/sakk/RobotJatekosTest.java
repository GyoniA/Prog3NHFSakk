package sakk;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class RobotJatekosTest {

	@Test
	public void test() {
		Tabla tab1 = new Tabla("SakkBabukPlaceholder.png");
		tab1.BabukAlaphelyzetbe();
		Tabla tab2 = new Tabla("SakkBabukPlaceholder.png");
		tab2.BabukAlaphelyzetbe();
		RobotJatekos r = new RobotJatekos(true, tab1);
		r.SetTabla(tab2);
		r.LepesGeneralas();
		assertNotEquals(tab1, tab2);
	}

}
