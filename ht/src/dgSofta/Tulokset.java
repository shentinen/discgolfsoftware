/**
 * 
 */
package dgSofta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Santeri Hentinen
 * @version 19.3.2020
 */
public class Tulokset implements Iterable<Tulos> {
    
    private final ArrayList<Tulos> alkiot;
    private int lkm = 0;
    
    /**
     * Oletusmuodostaja
     */
    public Tulokset() {
        alkiot = new ArrayList<Tulos>();
    }
    
    /**
     * Getter Tulos oliolle joka annetaan Tulokset -olion alkiot ArrayLististä
     * @param i mistä paikasta  
     * @return mitä löyty
     * @throws IndexOutOfBoundsException jos on huono indexi
     */
    public Tulos anna(int i) throws IndexOutOfBoundsException {
        if(i < 0 || i >= lkm)
            throw new IndexOutOfBoundsException("Laiton indexi: " + i);
        return alkiot.get(i);
    }
    
    /**
     * Lisää uuden tuloksen alkiot ArrayListiin
     * @param tulos mikä lisätään
     * @example
     * <pre name="test">
     * Tulokset tulokset = new Tulokset();
     * Tulos tulos1 = new Tulos(), tulos2 = new Tulos();
     * tulokset.getLkm() === 0;
     * tulokset.lisaa(tulos1); tulokset.getLkm() === 1;
     * tulokset.lisaa(tulos2); tulokset.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Tulos tulos){
        alkiot.add(tulos);
        lkm++;
    }
    
    
    /**
     * getteri Tulokset -olion Tulosten lukumäärälle
     * @return Tulosten lukumäärän
     * @example
     * <pre name="test">
     * Tulokset tulokset = new Tulokset();
     * Tulos tulos1 = new Tulos(), tulos2 = new Tulos();
     * tulokset.getLkm() === 0;
     * tulokset.lisaa(tulos1); tulokset.getLkm() === 1;
     * tulokset.lisaa(tulos2); tulokset.getLkm() === 2;
     * </pre>
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /**
     * @param tunnusnro minkä radan tulokset halutaan antaa
     * @return taulukollisen tuloksia jolla on oikea ID
     * @example
     * <pre name="test">
     * Tulokset tulokset = new Tulokset();
     * Tulos tulos1 = new Tulos(), tulos2 = new Tulos(), tulos3 = new Tulos();
     * tulos1.setId(1); tulos2.setId(1); tulos3.setId(2);
     * tulokset.lisaa(tulos1); tulokset.lisaa(tulos2); tulokset.lisaa(tulos3);
     * tulokset.annaTulokset(1).size() === 2;
     * </pre>
     */
    public List<Tulos> annaTulokset(int tunnusnro) {
        List<Tulos> loydetyt = new ArrayList<Tulos>();
        for  ( Tulos tul : alkiot ) {
            if ( tul.getId() == tunnusnro ) loydetyt.add(tul);
        }
        return loydetyt;
    }
    
    /**
     * Yrittään tulosten tallentamista tiedostoon
     * @throws SailoException Jos joku menee pieleen 
     */
    public void tallenna() throws SailoException {
        File ftied = new File("tulokset.dat");
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            for ( int i = 0; i < this.alkiot.size(); i++) {
                Tulos tulos = anna(i);
                fo.println(tulos.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittaminen epäonnistui");
        }
    }

    /**
     * testipääohjelma tulokset luokalle 
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        Tulokset tulokset = new Tulokset();
        Tulos tulos1 = new Tulos(), tulos2 = new Tulos();
        tulos1.setId(3);
        tulos2.setId(4);
        tulos1.taytaTulosRoinalla();
        tulos2.taytaTulosRoinalla();
        
        tulokset.lisaa(tulos1);
        tulokset.lisaa(tulos2);
        
        System.out.println();
        System.out.println("=========================== TULOKSET TESTI!!! ===========================");
        System.out.println();
        
        for (int i = 0; i < tulokset.getLkm(); i++) {
            Tulos tulos = tulokset.anna(i);
            System.out.println("TULOKSEN ID: " + i);
            tulos.tulosta(System.out);
        }
    }

    /**
     * Yrittään lukee tulokset tiedostoa
     * @throws SailoException Jos ei onnaa
     */
    public void lueTiedosto() throws SailoException {
        File ftied = new File("tulokset.dat");
        try ( BufferedReader fi = new BufferedReader(new FileReader(ftied.getCanonicalPath()))){
            while ( true ) {
                String rivi = fi.readLine();
                if ( rivi == null ) break;
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Tulos tulos = new Tulos();
                tulos.parse(rivi);
                lisaa(tulos);
            }    
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea!");
        } catch (IOException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea!");
        }
        
    }

    /**
     * Poistetaan tämä tulos
     * @param tulos poistettava tulos
     * @return tosi jos löytyi poistettava
     * @example
     * <pre name="test">
     * Tulokset tulokset = new Tulokset();
     * Tulos tulos1 = new Tulos(), tulos2 = new Tulos(), tulos3 = new Tulos();
     * tulos1.setId(1); tulos2.setId(1); tulos3.setId(2);
     * tulokset.lisaa(tulos1); tulokset.lisaa(tulos2); tulokset.lisaa(tulos3);
     * tulokset.poista(tulos3);
     * tulokset.getLkm() === 2;
     * </pre>
     */
    public boolean poista(Tulos tulos) {
        boolean ret = alkiot.remove(tulos);
        this.lkm--;
        return ret;
        
    }
    
    /**
     * @param tunnusNro Viite siihen minkä radan tulokset poistetaan
     * @return montako poistettiin.
     * @example
     * <pre name="test">
     * Tulokset tulokset = new Tulokset();
     * Tulos tulos1 = new Tulos(), tulos2 = new Tulos(), tulos3 = new Tulos();
     * tulos1.setId(1); tulos2.setId(1); tulos3.setId(2);
     * tulokset.lisaa(tulos1); tulokset.lisaa(tulos2); tulokset.lisaa(tulos3);
     * tulokset.getLkm() === 3;
     * tulokset.poistaRadanTulokset(1) === 2;
     * tulokset.getLkm() === 1;
     * </pre>
     */
    public int poistaRadanTulokset(int tunnusNro) {
        int n = 0;
        for (Iterator<Tulos> it = alkiot.iterator(); it.hasNext(); ) {
            Tulos tul = it.next();
            if ( tul.getId() == tunnusNro ) {
                it.remove();
                this.lkm--;
                n++;
            }
        }
        return n;
    }

    @Override
    public Iterator<Tulos> iterator() {
        return this.iterator();
    }
}
