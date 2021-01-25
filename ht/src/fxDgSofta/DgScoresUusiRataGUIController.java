package fxDgSofta;


import java.net.URL;
import java.util.ResourceBundle;

import dgSofta.Rata;
import fi.jyu.mit.fxgui.Dialogs;
//import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author vesal
 * @version 31.12.2015
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 */
public class DgScoresUusiRataGUIController implements ModalControllerInterface<Rata>, Initializable{
    @FXML private TextField editNimi;
    @FXML private TextField editOsoite;
    @FXML private TextField editPaikkakunta;
    @FXML private TextField editVaylia;
    @FXML private TextField editPar;
    @FXML private Label labelOngelma;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    @FXML void handleOK() {
        if ( rataKohdalla != null && editNimi.getText().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        if (editVaylia.getText().equals("18")) ModalController.closeStage(labelOngelma);    
        else naytaVirhe("Vain 18 väyläisiä ratoja hyväksytään");
        
    }
    
    @FXML void handleCancel() {
        rataKohdalla = null;
        ModalController.closeStage(labelOngelma);
    }
    
    @Override
    public Rata getResult() {
        return rataKohdalla;
    }


    @Override
    public void handleShown() {
        // 
    }


    @Override
    public void setDefault(Rata rata) {
        rataKohdalla = rata;
        
    }
    
  //====================================================================================
    private Rata rataKohdalla;
    private TextField[] edits;
    
    
    private void alusta() {
        edits = new TextField[] {editNimi, editOsoite, editPaikkakunta, editVaylia, editPar};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosRataan(k, (TextField)(e.getSource())));
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
     * Näyttää virheen virheLabelissa mikäli sellaista on
     * @param virhe Virhe
     */
    private void naytaVirhe(String virhe)  {
        if ( virhe == null || virhe.isEmpty()) {
            labelOngelma.setText("");
            labelOngelma.getStyleClass().removeAll("virhe");
            return;
        }
        labelOngelma.setText(virhe);
        labelOngelma.getStyleClass().add("virhe");
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
                    DgScoresUusiRataGUIController.class.getResource("DgScoresNewCourse.fxml"),
                    "Uusi Rata",
                    modalityStage, oletus, null
                );
    }

    

}