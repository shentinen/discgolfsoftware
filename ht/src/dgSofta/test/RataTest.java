package dgSofta.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import dgSofta.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.28 17:48:39 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class RataTest {



  // Generated by ComTest BEGIN
  /** testTaytaRataTiedoilla48 */
  @Test
  public void testTaytaRataTiedoilla48() {    // Rata: 48
    Rata rata = new Rata(); 
    rata.taytaRataTiedoilla(); 
    assertEquals("From: Rata line: 51", false, rata.getNimi().equals("")); 
    assertEquals("From: Rata line: 52", false, rata.getOsoite().equals("")); 
    assertEquals("From: Rata line: 53", true, rata.getPaikkakunta().equals("Sysmä")); 
    assertEquals("From: Rata line: 54", false, rata.getVaylia().equals("")); 
    assertEquals("From: Rata line: 55", true, (rata.getPar() >= 3*9 && rata.getPar() <= 4*24)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi80 */
  @Test
  public void testRekisteroi80() {    // Rata: 80
    Rata rata = new Rata(); 
    assertEquals("From: Rata line: 82", 0, rata.getTunnusNro()); 
    rata.rekisteroi(); 
    Rata rata2 = new Rata(); 
    rata2.rekisteroi(); 
    int n1 = rata.getTunnusNro(); 
    int n2 = rata2.getTunnusNro(); 
    assertEquals("From: Rata line: 88", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse154 */
  @Test
  public void testParse154() {    // Rata: 154
    Rata rata = new Rata(); 
    assertEquals("From: Rata line: 156", true, rata.toString().equals("0||||0|0")); 
    rata.parse("1|Joutsa DGP|Savontie 3|Joutsa|58|18"); 
    assertEquals("From: Rata line: 158", true, rata.toString().equals("1|Joutsa DGP|Savontie 3|Joutsa|58|18")); 
    rata.parse("2|Luhanka|||0|0"); 
    assertEquals("From: Rata line: 160", true, rata.toString().equals("2|Luhanka|Savontie 3|Joutsa|0|0")); 
    rata.parse("0||||60|15"); 
    assertEquals("From: Rata line: 162", true, rata.toString().equals("0|Luhanka|Savontie 3|Joutsa|60|15")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone222 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone222() throws CloneNotSupportedException {    // Rata: 222
    Rata testi = new Rata(); 
    testi.parse("1|JoutsaDGP|Savontie 3|Joutsa|58|18"); 
    Rata testinkopio = testi.clone(); 
    assertEquals("From: Rata line: 227", testi.toString(), testinkopio.toString()); 
    testinkopio.parse("2||||0|0"); 
    assertEquals("From: Rata line: 229", false, testinkopio.toString().equals(testi.toString())); 
  } // Generated by ComTest END
}