package dgSofta.test;
// Generated by ComTest BEGIN
import dgSofta.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.28 18:28:46 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class RadatTest {



  // Generated by ComTest BEGIN
  /** testLisaa61 */
  @Test
  public void testLisaa61() {    // Radat: 61
    Radat radat = new Radat(); 
    Rata joutsa = new Rata(), sysma = new Rata(); 
    assertEquals("From: Radat line: 64", 0, radat.getLkm()); 
    radat.lisaa(joutsa); assertEquals("From: Radat line: 65", 1, radat.getLkm()); 
    radat.lisaa(sysma); assertEquals("From: Radat line: 66", 2, radat.getLkm()); 
    radat.lisaa(joutsa); assertEquals("From: Radat line: 67", 3, radat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa87 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa87() throws CloneNotSupportedException {    // Radat: 87
    Radat radat = new Radat(); 
    Rata joutsa = new Rata(), joutsa2 = new Rata(); 
    joutsa.rekisteroi(); joutsa2.rekisteroi(); 
    assertEquals("From: Radat line: 93", 0, radat.getLkm()); 
    radat.korvaaTaiLisaa(joutsa); assertEquals("From: Radat line: 94", 1, radat.getLkm()); 
    radat.korvaaTaiLisaa(joutsa2); assertEquals("From: Radat line: 95", 2, radat.getLkm()); 
    Rata joutsa3 = joutsa.clone(); 
    radat.korvaaTaiLisaa(joutsa3); assertEquals("From: Radat line: 97", 2, radat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista117 */
  @Test
  public void testPoista117() {    // Radat: 117
    Radat radat = new Radat(); 
    Rata joutsa = new Rata(), sysma = new Rata(), hartola = new Rata(); 
    joutsa.rekisteroi(); sysma.rekisteroi(); hartola.rekisteroi(); 
    radat.lisaa(joutsa); radat.lisaa(sysma); radat.lisaa(hartola); 
    assertEquals("From: Radat line: 122", 3, radat.getLkm()); 
    assertEquals("From: Radat line: 123", 1, radat.poista(sysma.getTunnusNro())); 
    assertEquals("From: Radat line: 124", 2, radat.getLkm()); 
  } // Generated by ComTest END
}