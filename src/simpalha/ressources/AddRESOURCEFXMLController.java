
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import com.convertapi.Config;
import com.convertapi.ConversionResult;
import com.convertapi.ConvertApi;
import com.convertapi.Param;
import entities.Ressources;
import java.awt.Color;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceRessources;

import java.io.File;

import java.io.FileOutputStream;

import java.io.OutputStream;
import java.nio.file.Paths;

import java.util.Date;
import java.util.concurrent.CompletableFuture;




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
    @FXML
    private Label title;
    @FXML
    private Label path;
    @FXML
    private Label description;
    
    
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
        fail.showAndWait();
             if  (tftitle.getText().trim().isEmpty())
                 { title.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 tftitle.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(title).play();
                 new animatefx.animation.Shake(tftitle).play();}
             if  (tfdescription.getText().trim().isEmpty())
                 {description.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(description).play();
                 tfdescription.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(tfdescription).play();}
             if  (tfpath.getText().trim().isEmpty())
                 {path.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(path).play();
                 tfpath.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(tfpath).play();}
 
            }
            else {
          ServiceRessources sr = new ServiceRessources();
          Ressources R1= new Ressources();
          R1.setPath(tfpath.getText());
          R1.setTitle(tftitle.getText());
          R1.setDescription(tfdescription.getText());
          sr.Create(R1);
          
          //afficher prompt ajouté avec succés
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
//
//    private void convertAPI(File file) throws IOException
//    {       
//           Config.setDefaultSecret("bBmruLPE5tSddzRa");
//           CompletableFuture<ConversionResult> result = ConvertApi.convert("docx", "pdf", new Param("file", Paths.get(file.getAbsolutePath())));
//
//// save to file
//result.get().saveFile(Paths.get("my_file.pdf")).get();
//    }
    
    @FXML
    private void browse(ActionEvent event) {
     
         FileChooser fc = new FileChooser();
                 fc.setTitle("Choose image for resource..");

         //determination du type de data (image)
         FileChooser.ExtensionFilter extFilterJPG = 
                 new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
         FileChooser.ExtensionFilter extFilterjpg = 
                 new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
         FileChooser.ExtensionFilter extFilterPNG = 
                 new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
         FileChooser.ExtensionFilter extFilterpng = 
                 new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
         fc.getExtensionFilters().addAll(extFilterJPG,extFilterpng,extFilterPNG,extFilterjpg);
        
         //affichage de la fenetre
         File selectedFile = fc.showOpenDialog(null);
         tfpath.setText(selectedFile.getName());


        
        

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
