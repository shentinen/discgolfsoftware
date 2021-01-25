   package fxDgSofta;
       
   import dgSofta.Softa;
import javafx.application.Application;
import javafx.stage.Stage;
   import javafx.scene.Scene;
   //import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
   
   /**
   * @author Santeri Hentinen  
   * @version 1.2.2020
   * 
   * Pääohjelma Frisbeegolf -ohjelman käynnistämiseksi
   */
  public class DgScoresMain extends Application {
      @Override
      public void start(Stage primaryStage) {
          try {
              final FXMLLoader ldr = new FXMLLoader(getClass().getResource("DgScoresGUIView.fxml"));
              final Pane root = (Pane)ldr.load();
              final DgScoresGUIController sfwCtrl = (DgScoresGUIController)ldr.getController();
              //BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("DgScoresGUIView.fxml"));
              //Scene scene = new Scene(root);
              //scene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
              final Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.setTitle("Frisbeegolf tulokset");
              
              /*primaryStage.setOnCloseRequest((event) -> {
                  if (!sfwCtrl.voikoSulkea() ) event.consume();
                  
              });*/ 
              
              Softa softa = new Softa();
              sfwCtrl.setSofta(softa);
              
              primaryStage.show();
              
              sfwCtrl.lueTiedosto();

                            
          } catch(Exception e) {
              e.printStackTrace();
          }
      }
      
      /**
       * Käynnistetään käyttöliittymä 
       * @param args komentorivin parametrit
       */
      public static void main(String[] args) {
          launch(args);
      }
  }