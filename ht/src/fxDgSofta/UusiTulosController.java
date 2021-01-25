/**
 * 
 */
package fxDgSofta;

import java.net.URL;
import java.util.ResourceBundle;

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
 * @version 27.4.2020
 *
 */
public class UusiTulosController implements ModalControllerInterface <Tulos>, Initializable {

    @FXML private Label labelVirhe;
    @FXML private TextField editPvm;
    @FXML private TextField editHeitot;
    
    @FXML void handleCancel() {
        tulosKasiteltavana = null;
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handleOK() {
        if ( tulosKasiteltavana != null && editHeitot.getText().trim().equals("")) {
            naytaVirhe("Aseta heittojen määrä");
            return;
        }
        if ( editPvm.getText().trim().equals("")) {
            naytaVirhe("Aseta päivämäärään päivä, kuukausi ja vuosi");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }

    @Override
    public Tulos getResult() {
        return tulosKasiteltavana;
    }

    @Override
    public void handleShown() {
        //
    }

    @Override
    public void setDefault(Tulos oletus) {
        tulosKasiteltavana = oletus;
    }
    
    // ========================================================================
    
    private Tulos tulosKasiteltavana;
    private TextField[] edits;
    
    /**
     * Tehdään tarvittavat alustukset ikkunaa varten
     */
    public void alusta() {
        edits = new TextField[] {editPvm, editHeitot};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosTulokseen(k, (TextField)(e.getSource())));
        }
    }
    
    /**
     * Käsittelee muutoksen rataan.
     * @param k Int joka kertoo indeksin mitä kenttää ollaan muokkaamassa
     * @param edit TextField kenttä
     */
    private void kasitteleMuutosTulokseen(int k, TextField edit) {
        if (tulosKasiteltavana == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = tulosKasiteltavana.setPvm(s); break;
            case 2 : virhe = tulosKasiteltavana.setHeitot(s); break;
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
     * Luodaan radan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: KORJAA!
     * @param modalityStage Mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa käytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue.
     */
    public static Tulos kysyTulos(Stage modalityStage, Tulos oletus) {
        return ModalController.showModal(
                    UusiTulosController.class.getResource("DgScoresAddScore.fxml"),
                    "Uusi Tulos",
                    modalityStage, oletus, null
                );
    }
    
}
