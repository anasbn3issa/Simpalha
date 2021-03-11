/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import entities.Ressources;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceRessources;

/**
 * FXML Controller class
 *
 * @author cyrin
 */
public class UpdateRESOURCESFXMLController implements Initializable {
 @FXML
    private TextField tfmtitle;
    @FXML
    private TextField tfmdescription;
    @FXML
    private TextField tfmpath;
    @FXML
    private TextField tfmidr;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showRESOURCES(MouseEvent event) {
               try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ressources/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    private void ModifierRessource(ActionEvent event) {
        
         if ((tfmidr.getText().trim().isEmpty()) ||
                 (tfmpath.getText().trim().isEmpty()) || 
                 (tfmdescription.getText().trim().isEmpty()) || 
                 (tfmtitle.getText().trim().isEmpty())){
        Alert fail= new Alert(Alert.AlertType.INFORMATION);
        fail.setHeaderText("FAILURE! ");
        fail.setContentText("Please fill all the TEXTFIELDS ! ");
        fail.showAndWait();
          }
        else{
        
        
        ServiceRessources sr = new ServiceRessources();
//       Ressources R= sr.Search(Integer.valueOf(tfmodif.getText()));

//        sr.Update(R);
        Ressources R = new Ressources();
        R.setIdR(Integer.parseInt(tfmidr.getText()));
        R.setPath(tfmpath.getText());
        R.setDescription(tfmdescription.getText());
        R.setTitle(tfmtitle.getText());

        sr.Update(R);
        //afficher prompt ajouté avec succés
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Updating resource..");
        alert.setHeaderText(null);
        alert.setContentText("Update done with success!");

    }
    }

    @FXML
    private void RechercherRessource(ActionEvent event) throws SQLException {

        ServiceRessources sr = new ServiceRessources();

        int idmodif = Integer.parseInt(tfmidr.getText());

        Ressources R = sr.Search(idmodif);
        tfmtitle.setText(R.getTitle());
        tfmdescription.setText(R.getDescription());
        tfmpath.setText(R.getPath());

    }

    
    //Retour a la page d'acceuil de ressources
    @FXML
    private void PagePrecedente(ActionEvent event) {
        try {
            Parent page2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UpdateRESOURCESFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
