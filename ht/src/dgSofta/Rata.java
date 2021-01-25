 /**
 * 
 */
package dgSofta;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.Random.*;

/**
 * Muokkaa yksittäisiä kenttiä ja tietää niiden sisällön
 * @author Santeri Hentinen
 * @version 18.3.2020
 */
public class Rata implements Cloneable {
    
    private int tunnusNro;
    private String radanNimi = "";
    private String osoite = "";
    private String paikkakunta = "";
    private int parTulos = 0;
    private int vaylia = 0;
    
    private static int seuraavaNro = 1;
    
    /**
     * aliohjelma radan tulostamiselle
     * @param out Mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println();
        out.println(String.format("%03d", tunnusNro) + " " + this.radanNimi);
        out.println("    Osoite: " + this.osoite);
        out.println("    Paikkakunta: " + this.paikkakunta);
        out.println("    Radan par tulos: " + this.parTulos);
        out.println("    Väylien lukumäärä: " + this.vaylia);
        out.println();
        out.println("============================");
    }
    
    /**
     * Apumetodi jolla saadaan täytettyä radan tiedot
     * TODO: poista kun hommat toimii
     * @example
     * <pre name="test">
     * Rata rata = new Rata();
     * rata.taytaRataTiedoilla();
     * rata.getNimi().equals("") === false;
     * rata.getOsoite().equals("") === false;
     * rata.getPaikkakunta().equals("Sysmä") === true;
     * rata.getVaylia().equals("") === false;
     * (rata.getPar() >= 3*9 && rata.getPar() <= 4*24) === true;
     * </pre>
     */
    public void taytaRataTiedoilla() {
        this.radanNimi = "Jokumesta DGP " + rand(1000, 9999);
        this.osoite = "Missävaan " + rand(1, 999);
        this.paikkakunta = "Sysmä";
        this.vaylia = rand(9, 24);
        this.parTulos = this.vaylia*rand(3, 4);
    }
    
    /**
     * Aliohjelma tilanteeseen jossa haluttu os ei olekaan PrintStream
     * @param os Outputstream johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    
    /**
     * Rekisteröidään uusi rata ja annataan sille tunnusNro relaatiota varten
     * @return palauttaa tunnusNumeronsa
     * @example
     * <pre name="test">
     * Rata rata = new Rata();
     * rata.getTunnusNro() === 0;
     * rata.rekisteroi();
     * Rata rata2 = new Rata();
     * rata2.rekisteroi();
     * int n1 = rata.getTunnusNro();
     * int n2 = rata2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    /**
     * Getteri radan Tunnusnumerolle
     * @return Radan tunnusnumeron
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /**
     * Radan toString metodi jotta sitä voidaan tulostella testausvaiheessa johonkin.
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" +
                    this.radanNimi + "|" +
                    this.osoite + "|" +
                    this.paikkakunta + "|" +
                    this.parTulos + "|" + 
                    this.vaylia;
                
    }
    

    /**
     * Testaillaan Rata -olion toimintoja
     * @param args Ei käytössä 
     */
    public static void main(String[] args) {
        Rata joutsa = new Rata();
        Rata sysma = new Rata();
        
        joutsa.rekisteroi();
        sysma.rekisteroi();
        
        joutsa.tulosta(System.out);
        joutsa.taytaRataTiedoilla();
        joutsa.tulosta(System.out);
        
        sysma.tulosta(System.out);
        sysma.taytaRataTiedoilla();
        sysma.tulosta(System.out);
        
    }

    /**
     * Getteri radan Nimelle
     * @return radan nimen Stringinä
     */
    public String getNimi() {
        return this.radanNimi;
    }

    /**
     * Parse metodi jonka avulla voidaan oikeaa muotoa oleva merkkijono pätkiä ja asettaa
     * siinä olevat tiedot oikeille paikoilleen Rata -oliolle
     * @param rivi merkkijono josta halutaan poimia tiedot radalle.
     * @example
     * <pre name="test">
     * Rata rata = new Rata();
     * rata.toString().equals("0||||0|0") === true;
     * rata.parse("1|Joutsa DGP|Savontie 3|Joutsa|58|18");
     * rata.toString().equals("1|Joutsa DGP|Savontie 3|Joutsa|58|18") === true;
     * rata.parse("2|Luhanka|||0|0");
     * rata.toString().equals("2|Luhanka|Savontie 3|Joutsa|0|0") === true;
     * rata.parse("0||||60|15");
     * rata.toString().equals("0|Luhanka|Savontie 3|Joutsa|60|15") === true;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        this.radanNimi = Mjonot.erota(sb, '|', radanNimi);
        this.osoite = Mjonot.erota(sb, '|', osoite);
        this.paikkakunta = Mjonot.erota(sb, '|', paikkakunta);
        this.parTulos = Mjonot.erota(sb, '|', parTulos);
        this.vaylia = Mjonot.erota(sb, '|', vaylia);
    }

    /**
     * Setteri radan Id:lle realaatiota varten, varmistaa ettei kahdella radalla ole samaa id:tä
     * @param id Mikä id halutaan laittaa
     */
    private void setTunnusNro(int id) {
        this.tunnusNro = id;
        if ( this.tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }

    /**
     * Getteri osoitteelle
     * @return Radan osoitteen merkkijonona
     */
    public String getOsoite() {
        return this.osoite;
    }
    
    /**
     * Getteri paikkakunnalle
     * @return Radan paikkakunnan merkkijonona
     */
    public String getPaikkakunta() {
        return this.paikkakunta;
    }

    /**
     * Getteri radan Väylien lukumäärälle
     * @return Vaylien lukumäärän merkkijonona
     */
    public String getVaylia() {
        String s = "" + this.vaylia;
        return s;
    }
    
    /**
     * Getteri radan Par tulokselle
     * @return Par tuloksen kokonaislukuna
     */
    public int getPar() {
        return this.parTulos;
    }
    
    /**
     * Metodi radan kloonaamista varten
     * @return Kloonin radasta
     * @throws CloneNotSupportedException Jos joku menee pieleen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Rata testi = new Rata();
     * testi.parse("1|JoutsaDGP|Savontie 3|Joutsa|58|18");
     * Rata testinkopio = testi.clone();
     * testinkopio.toString() === testi.toString();
     * testinkopio.parse("2||||0|0");
     * testinkopio.toString().equals(testi.toString()) === false;
     * </pre>
     */
    @Override
    public Rata clone() throws CloneNotSupportedException {
        Rata uusi = (Rata) super.clone();
        return uusi;
    }

    /**
     * Setteri radan nimelle
     * @param s Mikä merkkijono halutaan asettaa radan nimeksi
     * @return Virheen Stringinä jos annettu merkkijono ei kelpaa
     */
    public String setNimi(String s) {
        this.radanNimi = s;
        if( s.equals("")) return "Nimi ei saa olla tyhjä";
        return null;
    }
    
    /**
     * Setteri radan osoitteelle
     * @param s Mikä merkkijono halutaan radan osoitteeksi asettaa
     * @return Virheen merkkijonona jos annettu merkkijono ei kelpaa
     */
    public String setOsoite(String s) {
        this.osoite = s;
        if( s.equals("")) return "Osoite ei saa olla tyhjä";
        return null;
    }
   
    /**
     * Setteri Radan paikkakunnalle
     * @param s Miksikä muutetaan
     * @return Virheen merkkijonona jos annettu merkkijono ei kelpaa
     */
    public String setPaikkakunta(String s) {
        this.paikkakunta = s;
        if( s.equals("")) return "Paikkakunta ei saa olla tyhjä";
        return null;
    }
    
    /**
     * Setteri radna väylien lukumäärälle 
     * @param s Miksi muutetaan, Radalle voidaan asettaa väylien lukumääräksi vain 18 tällä hetkellä
     * @return Virheen merkkijonona jos annettu merkkijono ei kelpaa
     */
    public String setVaylia(String s) {
        if (!s.matches("[0-9]*")) return "Vaylia täytyy olla jokin luku";
        StringBuilder sb = new StringBuilder(s);
        int luku = Mjonot.erotaInt(sb, 0);
        this.vaylia = luku;
        if ( luku != 18 ) return "Vain 18 väyläisiä ratoja hyväksytään";
        return null;
    }
    
    /**
     * Setteri radan Par -tulokselle
     * @param s Miksikä halutaan Par -tulos muuttaa, täytyy olla pelkkiä numeroita ja alle 100
     * @return Virheen merkkijonona jos annettu merkkijono ei kelpaa
     */
    public String setPar(String s) {
        if (!s.matches("[0-9]*")) return "Vaylia täytyy olla jokin luku";
        StringBuilder sb = new StringBuilder(s);
        int luku = Mjonot.erotaInt(sb, 0);
        if ( luku > 100 ) return "Ei noin pitkiä ratoja taida edes olla ;)";
        this.parTulos = luku;
        return null;
    }
    
}
