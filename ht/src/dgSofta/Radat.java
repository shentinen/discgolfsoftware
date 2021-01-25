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

/**
 * Ylläpitää radat tietorakennetta, osaa lisätä (TODO: sekä poistaa) ratoja
 * @author Santeri Hentinen
 * @version 19.3.2020
 *
 */
public class Radat {
    
    private int                 MAX_RATOJA    = 4;
    private int                 lkm           = 0;
    private Rata[]              alkiot;
    private boolean             muutettu = false;
    
    /**
     * Muodostaja Radoille
     */
    public Radat() {
        alkiot = new Rata[MAX_RATOJA];
    }
    
    /**
     * @return Totuusarvon siitä onko tietorakennetta muutettu
     */
    public boolean onkoMuutettu() {
        return this.muutettu;
    }
    
    /**
     * Muodostaja Radat -oliolle
     * @param maxLkm kuinka suuri taulukko halutaan tehdä
     */
    public Radat(int maxLkm) {
        this.alkiot = new Rata[maxLkm];
    }
    
    /**
     * Getteri Ratojen lukumäärälle
     * @return ratojen lukumäärän
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /**
     * Lisää yksittäisen radan Radat olion alkiot -taulukkoon.
     * @param rata Mikä rata halutaan lisätä
     * @example
     * <pre name="test">
     * Radat radat = new Radat();
     * Rata joutsa = new Rata(), sysma = new Rata();
     * radat.getLkm() === 0;
     * radat.lisaa(joutsa); radat.getLkm() === 1;
     * radat.lisaa(sysma); radat.getLkm() === 2;
     * radat.lisaa(joutsa); radat.getLkm() === 3;
     * </pre>
     */
    public void lisaa(Rata rata) {
        if (lkm >= alkiot.length) {
            Rata[] uuet = new Rata[alkiot.length + 10];
            for (int i = 0; i < lkm; i++) {
                uuet[i] = this.alkiot[i];
                System.arraycopy(alkiot, 0, uuet, 0, lkm);
                this.alkiot = uuet;
            }
        }
        alkiot[lkm++] = rata;
    }
    
    /**
     * Korvaa radan tietorakenteesta mikäli yhteinen Id löytyy, muulloin lisää uuden
     * radan tietorakenteeseen
     * @param rata Mikä halutaan lisätä tai korvata
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * #PACKAGEIMPORT
     * Radat radat = new Radat();
     * Rata joutsa = new Rata(), joutsa2 = new Rata();
     * joutsa.rekisteroi(); joutsa2.rekisteroi();
     * radat.getLkm() === 0;
     * radat.korvaaTaiLisaa(joutsa); radat.getLkm() === 1;
     * radat.korvaaTaiLisaa(joutsa2); radat.getLkm() === 2;
     * Rata joutsa3 = joutsa.clone();
     * radat.korvaaTaiLisaa(joutsa3); radat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Rata rata) {
        int id = rata.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = rata;
                muutettu = true;
            }
        }
        if ( muutettu == false ) lisaa(rata);
    }
    
    /**
     * Poistaa radan tunnusnumeron perusteella, ja muokkaa alkiot taulukon oikeaan muotoon
     * sen jälkeen
     * @param tunnusNro Mikä rata poistetaan
     * @return 1 jos poistettiin, 0 jos ei löytynyt
     * @example
     * <pre name="test">
     * Radat radat = new Radat();
     * Rata joutsa = new Rata(), sysma = new Rata(), hartola = new Rata();
     * joutsa.rekisteroi(); sysma.rekisteroi(); hartola.rekisteroi();
     * radat.lisaa(joutsa); radat.lisaa(sysma); radat.lisaa(hartola);
     * radat.getLkm() === 3;
     * radat.poista(sysma.getTunnusNro()) === 1;
     * radat.getLkm() === 2;
     * </pre>
     */
    public int poista(int tunnusNro) {
        int index = etsiId(tunnusNro);
        if ( index < 0 ) return 0;
        lkm--;
        for ( int i = index; i < lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
        
    }
    
    /**
     * @param id tunnusnumero jonka mukaan etsitään
     * @return löytyneen indeksi tai -1 jos ei löytynyt
     */
    public int etsiId(int id) {
        for ( int i = 0; i < lkm; i++) {
            if ( id == alkiot[i].getTunnusNro()) return i;
        }
        return -1;
    }
    
    /**
     * Ikäänkuin Getteri Radat olion taulukkoatribuuteissa sijaitseville yksittäisille radoille.
     * @param i monennesko Rata halutaan    
     * @return pyydetyssä paikassa olevan radan
     * @throws IndexOutOfBoundsException Jos pyydetään liian isoo
     */
    public Rata anna(int i) throws IndexOutOfBoundsException {
        if(i < 0 || i >= lkm)
            throw new IndexOutOfBoundsException("Laiton indexi: " + i);
        return alkiot[i];
    }
    
    /**
     * tallennetaan radat tiedostoon
     * @throws SailoException Jos joku menee mönkään 
     */
    public void tallenna() throws SailoException {
        File ftied = new File("radat.dat");
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            for ( int i = 0; i < this.lkm; i++) {
                Rata rata = anna(i);
                fo.println(rata.toString());
            }
        } catch ( FileNotFoundException ex ) {  
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittaminen epäonnistui");
        }
    }
    
    /**
     * Testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Radat radat = new Radat();
        Rata joutsa = new Rata(), hude = new Rata();
        
        joutsa.rekisteroi();
        joutsa.taytaRataTiedoilla();
        hude.rekisteroi();
        hude.taytaRataTiedoilla();
        radat.lisaa(joutsa);
        radat.lisaa(hude);
        
        System.out.println();
        System.out.println("=========================== RADAT TESTI!!! ===========================");
        System.out.println();
            
        for (int i = 0; i < radat.getLkm(); i++) {
            Rata rata = radat.anna(i);
            System.out.println("Radan ID: " + i);
            rata.tulosta(System.out);
        }
        
        System.out.println("=========================== RADAT TESTI2===========================");
        
         radat.poista(hude.getTunnusNro());
         
         for (int i = 0; i < radat.getLkm(); i++) {
             Rata rata = radat.anna(i);
             System.out.println("Radan ID: " + i);
             rata.tulosta(System.out);
         }
        
    }

    /**
     * Yrittään lukea ratatiedosto
     * @throws SailoException Jos ei onnaa
     */
    public void lueTiedosto() throws SailoException {
        File ftied = new File("radat.dat");
        try ( BufferedReader fi = new BufferedReader(new FileReader(ftied.getCanonicalPath()))){
            while ( true ) {
                String rivi = fi.readLine();
                if ( rivi == null ) break;
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Rata rata = new Rata();
                rata.parse(rivi);
                lisaa(rata);
            }    
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea!");
        } catch (IOException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea!");
        }
    }

}
