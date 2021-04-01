
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.ressources;

import simpalha.ressources.*;
import com.convertapi.Config;
import com.convertapi.ConversionResult;
import com.convertapi.ConvertApi;
import com.convertapi.Param;
import entities.Module;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.swing.JComboBox;

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
    @FXML
    private ComboBox<String> module;
    @FXML
    private Label labelmodule;

    int i;
    ServiceRessources sr = new ServiceRessources();
    @FXML
    private Label timer;

    int counter = 0;
    @FXML
    private Button buttonress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<String> list = sr.ReadModule();
            for (int i = 0; i < list.size(); i++) {
                module.getItems().add(list.get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(AddRESOURCEFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if ((tfpath.getText().trim().isEmpty())
                || (tftitle.getText().trim().isEmpty())
                || (tfdescription.getText().trim().isEmpty())
                || (module.getSelectionModel().isEmpty())) {
            Alert fail = new Alert(Alert.AlertType.INFORMATION);
            fail.setHeaderText("FAILURE! ");
            fail.setContentText("Please fill all textfields");
            fail.showAndWait();
            if (tftitle.getText().trim().isEmpty()) {
                title.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                tftitle.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                new animatefx.animation.Shake(title).play();
                new animatefx.animation.Shake(tftitle).play();

            }
            if (tfdescription.getText().trim().isEmpty()) {
                description.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                new animatefx.animation.Shake(description).play();
                tfdescription.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                new animatefx.animation.Shake(tfdescription).play();
            }

            if (tfpath.getText().trim().isEmpty()) {
                path.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                new animatefx.animation.Shake(path).play();
                tfpath.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                new animatefx.animation.Shake(tfpath).play();
            }

            if (module.getSelectionModel().isEmpty()) {
                labelmodule.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                new animatefx.animation.Shake(labelmodule).play();
                try {
                    System.out.println(sr.Read().size());
                } catch (SQLException ex) {
                    Logger.getLogger(AddRESOURCEFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            try {

                if (sr.Read().size() < 10) {
                    ServiceRessources sr = new ServiceRessources();
                    Ressources R1 = new Ressources();
                    R1.setPath(tfpath.getText());
                    R1.setTitle(tftitle.getText());
                    R1.setDescription(tfdescription.getText());
                    R1.setModule(module.getSelectionModel().getSelectedItem());
                    sr.Create(R1);

                    //afficher prompt ajouté avec succés
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Adding resource..");
                    alert.setHeaderText(null);
                    alert.setContentText("Resource added..");
                    alert.showAndWait();

//         if (test == true) {
//         copy(browse(event));}
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("QUOTA ACHIEVED..");
                    alert.setHeaderText(null);
                    alert.setContentText("IMPOSSIBLE to add resource..");
                    alert.showAndWait();
                    i = i + 1;
                    if (i > 3) {
//                       tfm
                  tfpath.setEditable(false);
                  tfpath.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                  tfdescription.setEditable(false);
                  tfdescription.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                  tftitle.setEditable(false);
                  tftitle.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                  timer.setText("BLOCKED.");
                  buttonress.setVisible(false);
                 
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddRESOURCEFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterpng, extFilterPNG, extFilterjpg);

        //affichage de la fenetre
        File selectedFile = fc.showOpenDialog(null);
        tfpath.setText(selectedFile.getName());

        if (selectedFile != null) {

            copy(selectedFile);
        }

    }

    private void copy(File from) {
        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\" + from.getName());//add the full path /ressources + file name

//check if folder ressources is created, sinon create it
        File file = new File(dir + "\\ressources");
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
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////////
        }

    }
}
