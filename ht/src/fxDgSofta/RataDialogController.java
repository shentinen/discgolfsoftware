package fxDgSofta;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dgSofta.Rata;
import dgSofta.Softa;
import dgSofta.Tulos;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Santeri Hentinen
 * @version 22.4.2020
 * Kysytään radan tiedot käyttäjältä
 */
public class RataDialogController implements ModalControllerInterface <Rata>, Initializable {
    
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
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    @FXML private void handleOK() {
        if ( rataKohdalla != null && rataKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() {
        rataKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    @Override
    public Rata getResult() {
        return rataKohdalla;
    }

    @Override
    public void handleShown() {
        this.editNimi.requestFocus();
        
    }

    @Override
    public void setDefault(Rata oletus) {
        rataKohdalla = oletus;
        naytaRata(rataKohdalla);
        
    }
    
    //=========================================================================
    
    private Rata rataKohdalla;
    private TextField[] edits;
    /**
     * Pahalta haiseva globaali muuttuja
     */
    public static Softa softa; // TODO: TÄÄ POIS!

    /**
     * Tekee tarvittavat alustukset ko. ikkunaan
     */
    public void alusta() {
        edits = new TextField[] {editNimi, editOsoite, editPaikkakunta, editVaylia, editPar, editParas, editHuonoin, editKa};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosRataan(k, (TextField)(e.getSource())));
        }
    }
    
    /**
     * Näyttää virheen virheLabelissa mikäli sellaista on
     * @param virhe Virhe
     */
    private void naytaVirhe(String virhe)  {
        if ( virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
   
    
    /**
     * tyhjennetään kentät mikäli ei ole yhtään tulosta mitä näyttää
     * @param kentat Mitkä kysessä
     */
    public static void siivoaTulosTiedot(TextField[] kentat) {
        for (int i = 0; i < kentat.length; i++) {
            kentat[i].clear();
        }
    }
    
    
    /**
     * Käsittelee muutoksen rataan.
     * @param k Int joka kertoo indeksin mitä kenttää ollaan muokkaamassa
     * @param edit TextField kenttä
     */
    private void kasitteleMuutosRataan(int k, TextField edit) {
        if (rataKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = rataKohdalla.setNimi(s); break;
            case 2 : virhe = rataKohdalla.setOsoite(s); break;
            case 3 : virhe = rataKohdalla.setPaikkakunta(s); break;
            case 4 : virhe = rataKohdalla.setVaylia(s); break;
            case 5 : virhe = rataKohdalla.setPar(s); break;
            default:
        }
        if ( virhe == null ) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    /**
     * Näytetään radan tiedot TextField komponentteihin
     * @param rata näytettävä rata
     */ 
    public void naytaRata(Rata rata) {
        naytaRata(edits, rata, softa);
    }
    
    /**
     * Näytetään radan tiedot TextField komponentteihin
     * @param edits taulukko jossa on tekstikenttiä
     * @param rata Näytettävä rata
     * @param sfw Sovellus jota käytetään //TODO: En tiiä miksi tää on mulla täällä mukana...
     */
    public static void naytaRata(TextField[] edits, Rata rata, Softa sfw) {
        if ( rata == null ) {
            return;
        }
        edits[0].setText(rata.getNimi());
        edits[1].setText(rata.getOsoite());
        edits[2].setText(rata.getPaikkakunta());
        edits[3].setText(rata.getVaylia());
        edits[4].setText("" + rata.getPar());
        edits[5].setText("" + etsiParas(rata, sfw));
        edits[6].setText("" + etsiHuonoin(rata, sfw));
        edits[7].setText("" + laskeKa(rata, sfw));
    }
    
    
    /**
     * Laskee tietylle radalla heitettyjen tulosten perusteella keskiarvon.
     * @param rata Mille lasketaan
     * @param sfw Mitä sovellusta käytetään TODO: taas täällä
     * @return Palauttaa heitettyjen heittojen keskiarvon kyseisellä radalla
     */
    private static double laskeKa(Rata rata, Softa sfw) {
        if ( sfw.annaTulokset(rata).size() == 0 || sfw.annaTulokset(rata) == null ) {
            return 0;
        }
        List <Tulos> tulokset = sfw.annaTulokset(rata);
        int lkm = tulokset.size();
        double summa = 0;
        for (Tulos tul : tulokset) {
            summa += tul.getHeittoja();
        }
        return summa/lkm;
    }

    /**
     * @param rata Minkä radan tuloksia tutkitaan
     * @param sfw Sovellus jota käytetään
     * @return Palauttaa parhaan tuloksen heittojen määrän
     */
    public static int etsiParas(Rata rata, Softa sfw) {
        if ( sfw.annaTulokset(rata).size() == 0 || sfw.annaTulokset(rata) == null ) {
            return 0;
        }
        List <Tulos> tulokset = sfw.annaTulokset(rata);
        Tulos paras = tulokset.get(0);
        for ( int i = 1; i < tulokset.size(); i++) {
            if ( tulokset.get(i).compareTo(paras) == 1 ) paras = tulokset.get(i);
        }
        return paras.getHeittoja();
    }
    
    /**
     * @param rata Minkä radan tuloksia tutkitaan
     * @param sfw Mikä sovellusolio kysessä TODO: Tätä kömpelö mun mielestä viedä aina mukana mutten tajuu miten päästä eroonkaan
     * @return Huonoimman tuloksen heittomäärän
     */
    public static int etsiHuonoin(Rata rata, Softa sfw) {
        if ( sfw.annaTulokset(rata).size() == 0 || sfw.annaTulokset(rata) == null ) {
            return 0;
        }
        List <Tulos> tulokset = sfw.annaTulokset(rata);
        Tulos huonoin = tulokset.get(0);
        for ( int i = 1; i < tulokset.size(); i++) {
            if ( tulokset.get(i).compareTo(huonoin) == -1 ) huonoin = tulokset.get(i);
        }
        return huonoin.getHeittoja();
    }
    
    /**
     * Luodaan radan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: KORJAA!
     * @param modalityStage Mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa käytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue.
     */
    public static Rata kysyRata(Stage modalityStage, Rata oletus) {
        return ModalController.showModal(
                    RataDialogController.class.getResource("DgScoresCourse.fxml"),
                    oletus.getNimi(),
                    modalityStage, oletus, null
                );
    }
    
}
