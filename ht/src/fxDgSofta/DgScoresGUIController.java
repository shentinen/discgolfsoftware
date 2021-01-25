package fxDgSofta;


//import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dgSofta.Rata;
import dgSofta.SailoException;
import dgSofta.Softa;
//import dgSofta.Tulokset;
import dgSofta.Tulos;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.ohj2.Mjonot;
//import fi.jyu.mit.ohj2.Mjonot;
//import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextArea;
//import javafx.scene.text.Font;
import javafx.scene.control.TextField;

/**
 * @author vesal
 * @version 31.12.2015
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 */
public class DgScoresGUIController implements Initializable {
    
    @FXML private ListChooser<Rata> chooserRadat;
    @FXML private ScrollPane panelRata;
    @FXML private ScrollPane panelTulos;
    @FXML private StringGrid<Tulos> tableTulokset;
    
    @FXML private TextField editNimi;
    @FXML private TextField editOsoite;
    @FXML private TextField editPaikkakunta;
    @FXML private TextField editVaylia;
    @FXML private TextField editPar;
    @FXML private TextField editParas;
    @FXML private TextField editHuonoin;
    @FXML private TextField editKa;
    
    @FXML private Label labelVirhe;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML void handleUusiRata() {
        uusiRata();
    }
    
    @FXML void handlePoistaRata() {
        poistaRata();
    }
    
    @FXML void handleMuokkaaRata() {
        muokkaaRata();
    }
    
    @FXML void handleTulos() {
        tulos();
    }
    
    @FXML void handlePoistaTulos() {
        poistaTulos();
    }
    
    
    @FXML void handleTulosEdit() {
        tulosEdit();
    }
    
    @FXML void handleTallenna() {
        tallenna();
    }

    
    
    // ====================================================================================================================================
    
    private Softa softa;
    private TextField[] edits;
    
    /**
     * Luetaan tiedostot josta noukitaan olioille oikeat tiedot
     */
    protected void lueTiedosto() {
        try {
            softa.lueTiedosto();
            hae(0);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    } 
    
    
    /**
     * Luodaan uusi rata TODO: Ei osaa ksysyä tietoja dialogilla vielä
     */
    private void uusiRata() {
        Rata uusi = new Rata();
        uusi = DgScoresUusiRataGUIController.kysyRata(null, uusi);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        softa.lisaa(uusi);
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Poistetaan valittuna oleva rata
     */
    private void poistaRata() {
        Rata rata = chooserRadat.getSelectedObject();
        if ( rata == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko rata: " + rata.getNimi(), "Kyllä", "Ei") )
            return;
        softa.poistaRata(rata);
        int index = chooserRadat.getSelectedIndex();
        hae(0);
        chooserRadat.setSelectedIndex(index);
        if ( softa.getRatoja() == 0 ) {
            for (int i = 0; i < edits.length; i++) {
                edits[i].clear();
            }
        }
        
    }
    
    private void poistaTulos() {
        Tulos tulos = tableTulokset.getObject(); 
        if ( tulos == null ) return;
        if ( !Dialogs.showQuestionDialog("Tuloksen Poisto", "Poistetaanko valittu tulos", "Kyllä", "Ei") )
            return;
        softa.poistaTulos(tulos);
        hae(chooserRadat.getSelectedObject().getTunnusNro());
    }
    
    
    /**
     * Muokataan valittuna olevaa rataa. TODO: Jostain syystä palauttaa myös kloonin radasta, eli tekee siitä tarpeettoman kopion listaan.
     */
    private void muokkaaRata() {
        RataDialogController.softa = this.softa;
        Rata rataKohdalla = chooserRadat.getSelectedObject();
        
        if ( rataKohdalla == null ) return;
        try {
            Rata rata = RataDialogController.kysyRata(null, rataKohdalla.clone());
            if ( rata == null ) return;
            softa.korvaaTaiLisaa(rata);
            hae(rata.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        }
    }
    
    
    /**
     * Luodaan uusi tulos ja TODO: Toistaiseksi se täytetään roinalla, Pitää olla valittuna mille radalle tulos luodaan 
     */
    private void tulos() {
        if ( chooserRadat.getSelectedObject() == null ) 
                Dialogs.showMessageDialog("Valitse ensin rata, jolle tulos lisätään");
        else{
            Tulos uusi = new Tulos();
            uusi.setId(chooserRadat.getSelectedObject().getTunnusNro());
            uusi = UusiTulosController.kysyTulos(null, uusi);
            if ( uusi == null ) return;
            softa.lisaa(uusi);
            naytaRata();
        }
        
    }
    
    /**
     * Yritään muokata tulosta TODO: Ei osaa vielä
     */
    private void tulosEdit() {
        ModalController.showModal(DgScoresGUIController.class.getResource("DgScoresEditScore.fxml"),
                "[Radan Nimi ja tuloksen pvm.]", null, "");
    }
    
    /**
     * Kutsutaan Softa luokan tallenna -metodia joka kirjoittaa .dat tiedostoihin Sovelluksen tiedot talteen.
     */
    private void tallenna() {
        try {
            softa.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

    /**
     * asetetaan kontrollerin sofatviite
     * @param softa mihin viitataan
     */
    public void setSofta(Softa softa) {
        this.softa = softa;
        naytaRata();
    }
    
    /**
     * Getteri Softalle
     * @return Viitteen Mainista käyttöönotettuun sovellukseen
     */
    public Softa getSofta() {
        return this.softa;
    }
    
    /**
     * Tehdään tarvittavat alustukset Controllerin toimintaa varten.
     */
    private void alusta() {
        chooserRadat.clear();
        chooserRadat.addSelectionListener(e -> naytaRata());
        edits = new TextField[] {editNimi, editOsoite, editPaikkakunta, editVaylia, editPar, editParas, editHuonoin, editKa};
    }
    /**
     * Näytetään kohdalla olevan radan tiedot.
     */
    private void naytaRata() {
        Rata rataKohdalla = chooserRadat.getSelectedObject();
        if (rataKohdalla == null) return;
        RataDialogController.naytaRata(edits, rataKohdalla, this.softa);
        naytaTulokset(rataKohdalla);
    }
    
    /**
     * Näytetään kaikki valitun radan tulokset Sovelluksen oikean reunan String Grid elementtiin
     * @param rata Minkä radan tulokset halutaan näyttää
     */
    private void naytaTulokset(Rata rata) {
        tableTulokset.clear();
        if ( rata == null ) return;
        
        List<Tulos> tulokset = softa.annaTulokset(rata);
        if ( tulokset.size() == 0 ) return;
        for ( Tulos tul : tulokset ) naytaTulos(tul, rata);
        
    }
    
    /**
     * Otetaan yksittäisestä tulos -oliosta tiedot ja näytetään ne StringGrid -elementin kentissä
     * @param tul Mikä tulos halutaan näyttää StringGrid -elementin boxeihin
     * @param rata Minkä radan tuloksia halutaan näyttää
     */
    private void naytaTulos (Tulos tul, Rata rata) {
        int radanPar = rata.getPar();
        String[] rivi = tul.toString().split("\\|");
        int tulos = Mjonot.erota(new StringBuilder(rivi[2]), '|', 0);
        tableTulokset.add(tul, rivi[1], "" + tulos, "" + (tulos - radanPar));
    }
    
    /**
     * Haetaan rata ID:n perusteella
     * @param ratanro Radan ID
     */
    private void hae(int ratanro) {
        chooserRadat.clear();
        int index = 0;
        for (int i = 0; i < softa.getRatoja(); i++) {
            Rata rata = softa.annaRata(i);
            if(rata.getTunnusNro() == ratanro) index = i;
            chooserRadat.add(rata.getNimi(), rata);
        }
        chooserRadat.setSelectedIndex(index); //tästä tulee muutosviesti joka näytetään
    }
    
    @SuppressWarnings("unused")
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


}