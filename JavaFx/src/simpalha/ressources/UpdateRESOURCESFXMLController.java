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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceRessources;
import utils.UserSession;

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
     *
     */
    public Ressources R;
    private int idR;
    @FXML
    private Label title;
    @FXML
    private Label path;
    @FXML
    private Label description;
    @FXML
    private Label labelmodule;
    @FXML
    private ComboBox<String> module;
    
    ServiceRessources sr= new ServiceRessources();

    public UpdateRESOURCESFXMLController() {
        this.R = new Ressources();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  tfmpath.setEditable(false);
        try {
            // TODO
            List<String> list= sr.ReadModule(); 
            for(int i=0; i<list.size(); i++)
            module.getItems().add(list.get(i));
        } catch (SQLException ex) {
            Logger.getLogger(UpdateRESOURCESFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
          
        
        Platform.runLater(() -> {
            ServiceRessources sr = new ServiceRessources();
            Ressources R = sr.Search(idR);

            tfmtitle.setText(R.getTitle());
            tfmdescription.setText(R.getDescription());
            tfmpath.setText(R.getPath());

        }
        );

    }
////     @Override
//    public void initialize(Ressources R) {
//        // TODO
//        tfmtitle.setText(R.getTitle());
//        tfmdescription.setText(R.getDescription());
//        tfmpath.setText(R.getPath());
//    }    

    public void initRess(int id) {
        idR = id;
    }

    public void showRess(Ressources R) {
        tfmtitle.setText(R.getTitle());
        tfmdescription.setText(R.getDescription());
        tfmpath.setText("ressources\\"+R.getPath());
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

        if ((tfmidr.getText().trim().isEmpty())
                || (tfmpath.getText().trim().isEmpty())
                || (tfmdescription.getText().trim().isEmpty())
                || (tfmtitle.getText().trim().isEmpty())
                || (module.getSelectionModel().isEmpty()))
        {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setHeaderText("FAILURE! ");
            fail.setContentText("Please fill all the TEXTFIELDS ! ");
            fail.showAndWait();
              if  (tfmtitle.getText().trim().isEmpty())
                 { title.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(title).play();
                 tfmtitle.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(tfmtitle).play();}
              
             if  (tfmdescription.getText().trim().isEmpty())
                 {description.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(description).play();
                 tfmdescription.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(tfmdescription).play();}
             
             if  (tfmpath.getText().trim().isEmpty())
                 {path.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(path).play();
                 tfmpath.setStyle("-fx-border-color:RED; -fx-border-width: 2px");
                 new animatefx.animation.Shake(tfmpath).play();}
             
             if (module.getSelectionModel().isEmpty())
                 {labelmodule.setStyle("-fx-text-fill:RED; -fx-font-weight: bold");
                 new animatefx.animation.Shake(labelmodule).play();}
 
        } else {
           
            ServiceRessources sr = new ServiceRessources();
//       Ressources R= sr.Search(Integer.valueOf(tfmodif.getText()));

//        sr.Update(R);
            R = sr.Search(idR);
            System.out.println(idR);
//            Ressources R = new Ressources();
            R.setIdR(idR);
            R.setPath(tfmpath.getText());
            R.setDescription(tfmdescription.getText());
            R.setTitle(tfmtitle.getText());
            R.setModule(module.getValue());
            System.out.println(R.toString());

            sr.Update(R);
             //afficher prompt ajouté avec succés
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Updating resource..");
            alert.setHeaderText(null);
            alert.setContentText("Update done with success!");
            
//            //redirect
//              try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource(
//                            "ressources/FXMLDocument.fxml"
//                    )
//            );
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
//            stage.setScene(
//                    new Scene(loader.load())
//            );
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
            
            

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

    @FXML
    private void browse(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.setTitle("Choose image for resource..");
        File selectedFile = fc.showOpenDialog(null);
        
         FileChooser.ExtensionFilter extFilterJPG = 
                 new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
         FileChooser.ExtensionFilter extFilterjpg = 
                 new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
         FileChooser.ExtensionFilter extFilterPNG = 
                 new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
         FileChooser.ExtensionFilter extFilterpng = 
                 new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
         fc.getExtensionFilters().addAll(extFilterJPG,extFilterpng,extFilterPNG,extFilterjpg);
        
        
        tfmpath.setText(selectedFile.getName());


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

    @FXML
    private void showPOSTS(MouseEvent event) {
          //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showQUIZZ(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/quizz/FXMLQuizz.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showRec(MouseEvent event) {
          //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/ressources/FXMLDocument.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showProfile(MouseEvent event) {
         //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Profile.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void logout(MouseEvent event) {
         UserSession.getInstace(0).cleanUserSession();
       //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/users/Login.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

}
