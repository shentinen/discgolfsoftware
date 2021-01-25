 /**
 * 
 */
package dgSofta;

import java.util.List;

/**
 * Toimii tiedonvälittäjänä Radat ja Tulokset -luokkien välillä
 * TODO: Lukee ja kirjoittaa tiedostoja tarpeen mukaan apuluokkia hyväksi käyttäen
 * @author Santeri Hentinen
 * @version 19.3.2020
 *
 */
public class Softa {
    
    private Radat radat = new Radat();
    private Tulokset tulokset = new Tulokset();
    
    /**
     * oletusmuodostaja.. aik tylsä
     */
    public Softa() {
        // ei oo hommia
    }
    
    /**
     * getteri Tulokset -oliolle
     * @return Palauttaa atribuuttinsa; Tulokset -olion
     */
    public Tulokset getTulokset() {
        return this.tulokset;
    }
    
    /**
     * Haetaan kaikki radan tulokset
     * @param rata jolle Tulokset haetaan
     * @return Listan löydetyistä tuloksista joilla on oikea ID
     */
    public List<Tulos> annaTulokset(Rata rata) {
        return tulokset.annaTulokset(rata.getTunnusNro());
    }
    
    /**
     * lisätään uusi rata, metodi kutsuu oikean luokan lisää metodia koska ei itte osaa.
     * @param rata mikä rata lisätään 
     */
    public void lisaa(Rata rata) {
        radat.lisaa(rata);
    }
    
    /**
     * Korvaa radan tietorakenteessaa, Ottaa raada omistukssnsa. Etistään samalla tunnusnumerolla oleva rata. Jos ei löydy,
     * niin lisätään uutenä ratana
     * @param rata Mikä halutaan lisätä
     */
    public void korvaaTaiLisaa(Rata rata) {
        radat.korvaaTaiLisaa(rata);
    }
    
    /**
     * Lisätään uusi tulos, metodi kutsuu tulokset luokan lisää metodia koska ei itte osaa.
     * @param tulos lisättävä tulos
     */
    public void lisaa(Tulos tulos) {
        tulokset.lisaa(tulos);
    }
    
    /**
     * Getteri Radat -attribuutin alkiot taulukon Ratojen lukumäärälle
     * @return palauttaa Ratojen lukumäärän
     */
    public int getRatoja() {
        return radat.getLkm();
    }
    
    /**
     * Tallennetaan tiedostoon
     * @throws SailoException Jos joku menee pieleen
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        
        try {
            radat.tallenna();
        } catch (SailoException e) {
             virhe += e.getMessage();
        }
        
        try {
            tulokset.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();        
        }
        if (virhe.length() > 0)     
            throw new SailoException(virhe);
    }
    

    /**
     * testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        var sfw = new Softa();
        
        Rata joutsa = new Rata(), laajis = new Rata();
        joutsa.rekisteroi();
        joutsa.taytaRataTiedoilla();
        laajis.rekisteroi();
        laajis.taytaRataTiedoilla();
        
        sfw.lisaa(joutsa);
        sfw.lisaa(laajis);
        
        Tulos tulos1 = new Tulos();
        tulos1.setId(joutsa.getTunnusNro());
        tulos1.taytaTulosRoinalla();
        
        var tulos2 = new Tulos();
        tulos2.setId(laajis.getTunnusNro());
        tulos2.taytaTulosRoinalla();
        
        System.out.println("==================================== SOFTAN TESTI! ====================================");
        
        for (int i = 0; i < sfw.getRatoja();  i++) {
            Rata rata = sfw.annaRata(i);
            System.out.println("Rata paikassa: " + i);
            rata.tulosta(System.out);
        }
        
        tulos1.tulosta(System.out);
        tulos2.tulosta(System.out);
    }

    /**
     * Getteri yksittäiselle radalle, mutku ei osaa niin pyytää jeesiä Radat luokalta
     * @param i Monennesko rata annetaan
     * @return pyydetyn radan
     * @throws IndexOutOfBoundsException Jos pyydetään tyhmää indexiä
     */
    public Rata annaRata(int i) throws IndexOutOfBoundsException{
        return radat.anna(i);
    }

    /**
     * luetaan tiedostot
     * @throws SailoException Jos ei onnistu lukee
     */
    public void lueTiedosto() throws SailoException {
        radat.lueTiedosto();
        tulokset.lueTiedosto();
    }

    /**
     * Poistaa tämän radan
     * @param rata Mikä halutaan poistaa
     * @return montako jäsentä poistettiin
     */
    public int poistaRata(Rata rata) {
        if ( rata == null ) return 0;
        int ret = radat.poista(rata.getTunnusNro());
        tulokset.poistaRadanTulokset(rata.getTunnusNro());
        return ret;
    }

    /**
     * Poistaa tämän tuloksen
     * @param tulos Mikä halutaan poistaa
     */
    public void poistaTulos(Tulos tulos) {
        if ( tulos == null ) return;
        tulokset.poista(tulos);
    }

}
