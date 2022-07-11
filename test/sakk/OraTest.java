package sakk;

import static org.junit.Assert.assertEquals;

import javax.swing.JTextField;

import org.junit.Test;

public class OraTest {

	@Test
	public void testOra() throws InterruptedException {
		Jatek j = new Jatek();
		JTextField feherIdo = new JTextField("Feher játékos ideje: ", 30);
		Ora feherOra = new Ora(true, 2, j, feherIdo);
		feherOra.start();
		Thread.sleep(1100);
		assertEquals("Feher játékos ideje: 2s", feherIdo.getText());
		feherOra.Indit();
		Thread.sleep(1100);
		assertEquals("Feher játékos ideje: 1s", feherIdo.getText());
		feherOra.Szunet();
		Thread.sleep(1100);
		assertEquals("Feher játékos ideje: 1s", feherIdo.getText());
	}

}
