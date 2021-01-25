/**
 * 
 */
package dgSofta;

import java.io.OutputStream;
import static kanta.Random.*;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Santeri Hentinen
 * @version 19.3.2020
 */
public class Tulos {
    
    private int tunnusNro;
    private String pvm = "";
    private int heitot = 0;
    
    private static final int TESTITUNNUS = 1;
    
    /**
     * Tulos -olion tulosta metodi
     * @param out mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Radan ID: " + String.format("%03d", tunnusNro) + " testid " + rand(1000, 9999));
        out.println("    Tulokset: " + "" + this.heitot);
        out.println("    Pelattu: " + this.pvm);
        out.println();
        out.println("===============================================");
        out.println();
    }
    
    @Override
    public String toString() {
        return "" + getId() + "|" +
                    this.pvm + "|" +
                    "" + this.heitot + "|";
    }
    
    /**
     * Aliohjelma tilanteeseen jossa haluttu os ei olekaan PrintStream
     * @param os mihkä tulostaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Setteri tuloksen Id:lle
     * @param id Tunnusnumero joka tulokselle halutaan antaa
     */
    public void setId(int id) {
        this.tunnusNro = id;
    }
    
    /**
     * getteri tuloksen id:lle
     * @return Oman Id:nsä
     * @example
     * <pre name="test">
     * Tulos tulos = new Tulos();
     * tulos.setId(3);
     * tulos.getId() === 3;
     * </pre>
     */
    public int getId() {
        return this.tunnusNro;
    }
    
    /**
     * Taytetaan Tulos -olio datalla jotta sitä on järkevä tulostella ja testata
     */
    public void taytaTulosRoinalla() {
        int d = rand(1, 28);
        int m = rand(1,12);
        int y = rand(2000, 2020);
        this.heitot = 42;
        this.pvm = d + "." + m + "." + y;
    }
    
    
    /**
     * Testipääohjelma
     * @param args ei käytössä 
     */
    public static void main(String[] args) {
    Tulos tulos1 = new Tulos();
    Tulos tulos2 = new Tulos();
    
    System.out.println(tulos1.toString());
    
    tulos1.setId(TESTITUNNUS);
    tulos2.setId(TESTITUNNUS);
    
    
    tulos1.tulosta(System.out);
    tulos1.taytaTulosRoinalla();
    tulos1.tulosta(System.out);
    
    tulos2.tulosta(System.out);
    tulos2.taytaTulosRoinalla();
    tulos2.tulosta(System.out);
    }
    
    /**
     * Setteri tuloksen päivämäärän asettamista varten
     * @param paivays Minä päivän tulos heitettiin
     * @return virheen merkkijonona mikäli annettu merkkijono ei ollut sallitun mukainen
     */
    public String setPvm(String paivays) {
        if (!paivays.matches("[0-9][0-9.]*[0-9]")) return "Paivays saa sisältää vain numeroita ja pisteitä";
        this.pvm = paivays;
        return null;
    }
    
    /**
     * setteri heittojen määrälle
     * @param maara Montako kertaa heitettiin
     * @return Mikä virhe
     */
    public String setHeitot(String maara) {
        if (!maara.matches("[0-9]*")) return "Heittojen määrä tulee olla numero";
        StringBuilder sb = new StringBuilder(maara);
        int luku = Mjonot.erotaInt(sb, 0);
        if (luku > 100) return "Heitikö oikeesti yli 100?";
        this.heitot = luku;
        return null;
    }

    /**
     * Parse metodi jolla voidaan ottaa oikeanlaisesta merkkijonosta
     * tiedot Tulos -oliolle
     * @param rivi Merkkijono josta halutaan ottaa tiedot
     * @example
     * <pre name="test">
     * Tulos tulos = new Tulos();
     * tulos.toString().equals("0||0|") === true;
     * tulos.parse("1|25.3.2018|65");
     * tulos.toString().equals("1|25.3.2018|65|");
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        this.pvm = Mjonot.erota(sb, '|', pvm);
        this.heitot = Mjonot.erota(sb, '|', heitot);
    }

    /**
     * @return Heittjoen yhteenlasketun määrän
     */
    public int getHeittoja() {
        return this.heitot;
    }
    
    /**
     * @param tulos Mistä ongitaan tulokset ulos TODO: EI KÄYTÖSSÄ TÄSSÄ VERSIOSSA!
     * @return palauttaa taulukon jossa on intteinä kyseiset tulokset
     */
    public int[] tuloksetTaulukoksi(String tulos) {
        StringBuilder sb = new StringBuilder(tulos);
        int[] t = new int[(sb.length()/2)+1];
        for (int i = 0; i < t.length; i++) {
            t[i] = Mjonot.erota(sb, '_', 1);
        }
        return t;
    }
    
    /**
     * TODO: EI KÄYTÖSSÄ TÄSSÄ VERSIOSSA!
     * @param t taulukko jossa on kyseisen tuloksen tulokset joka väylältä
     * @return palauttaa heittojen yhteenlasketun kokonaismäärän
     */
    public int ynnaaTulokset(int[] t) {
        int par = 0;
        for ( int i = 0; i < t.length; i++) {
            par += t[i];
        }
        return par;
    }

    /**
     * @param tulos Mihin tulokseen vertaillaan
     * @return 1 jos vertailtava tulos on parempi, -1 jos vertailun kohde on parempi, ja 0 jos tulokset ovat yhtä hyviä
     */
    public int compareTo(Tulos tulos) {
        if ( this.getHeittoja() < tulos.getHeittoja() ) return 1;
        if ( this.getHeittoja() > tulos.getHeittoja() ) return -1;
        return 0;
    }
}
