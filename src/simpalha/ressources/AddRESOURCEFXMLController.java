
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import entities.Ressources;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceRessources;

/**
 * FXML Controller class
 *
 * @author cyrin
 */
public class AddRESOURCEFXMLController implements Initializable {

    @FXML
     private TextField tftitle;
    @FXML
    private TextField tfpath;
    @FXML
    private TextField tfdescription;
    
       boolean test = false;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //acces page wajdi
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
    //action bouton ajout
    private void AjouterRessource(ActionEvent event) {
            if ( (tfpath.getText().trim().isEmpty())
                    || (tftitle.getText().trim().isEmpty())
                    || (tfdescription.getText().trim().isEmpty())
                    ){
        Alert fail= new Alert(Alert.AlertType.INFORMATION);
        fail.setHeaderText("FAILURE! ");
        fail.setContentText("Please fill all textfields");
        fail.showAndWait();}
            else {
          ServiceRessources sr = new ServiceRessources();
          Ressources R1= new Ressources();
          R1.setPath(tfpath.getText());
          R1.setTitle(tftitle.getText());
          R1.setDescription(tfdescription.getText());
          sr.Create(R1);
          
          //afficher promt ajouté avec succés
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding resource..");
            alert.setHeaderText(null);
            alert.setContentText("Resource added..");
            alert.showAndWait();
          
//         if (test == true) {
//         copy(browse(event));}
          
        }}
       
    
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

    @FXML
    private void browse(ActionEvent event) {
     
         FileChooser fc = new FileChooser();

        File selectedFile = fc.showOpenDialog(null);
        tfpath.setText(selectedFile.getAbsolutePath());
        
        

        if (selectedFile != null) {
       
            copy(selectedFile);
        }
    
    }
    
    
    private void copy(File from) {
        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\"+from.getName());//add the full path /ressources + file name
        
//check if folder ressources is created, sinon create it
        File file = new File(dir+"\\ressources");
        file.mkdirs();
        /////
        //COPY FILE OPERATION
        InputStream is = null;
        OutputStream os = null;
        try {
            try {
                is = new FileInputStream(from);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                is.close();
                os.close();
            }catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////////
        }


   }
}
