/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.reclamation;

import com.sun.org.apache.xerces.internal.util.URI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Reclamation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.IServiceReclamation;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.JavaSoundRecorder;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ModifierReclamationController implements Initializable {
    private IServiceReclamation service;
    private String idRec;

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private Button add;
    @FXML
    private TextField id;
    @FXML
    private TextArea des;
    @FXML
    private ToggleButton Record;
    @FXML
    private Button upload;
    @FXML
    private ImageView image;
    @FXML
    private Button valider;
    @FXML
    private Button annuler;
    private TextField helper;
    private File FileSelected;
    private String filename;
    private String wavfile;
    private JavaSoundRecorder javaSoundRecorder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //run les paramétre avant l'execution d l'interface
        Platform.runLater(() -> {
            service = new IServiceReclamation();

            Reclamation rec = service.findbyId(idRec);

            des.setText(rec.getDescription());
            helper.setText(rec.getIdreported());
        }
        );
    }    

    @FXML
    private void modifierRec(ActionEvent event) {
        Reclamation rec = service.findbyId(idRec);
        rec.setDescription(des.getText());
        service.Update(rec);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ListerReclamation.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifierReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
         String title="réclamation modifiée avec succès ";
        TrayNotification tray =new TrayNotification();
        AnimationType type =AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }
    //recuperer les données
    void initData(String id) {
        idRec = id;
    }

    @FXML
    private void Record(ActionEvent event) {
           if (Record.isSelected()) {
            System.out.println("on");
            javaSoundRecorder = new JavaSoundRecorder();
            Thread thread = new Thread(javaSoundRecorder);
            thread.start();
        } else {
            System.out.println("off");
            wavfile = javaSoundRecorder.finish();
            javaSoundRecorder.cancel();
        }
    }

    @FXML
    private void upload(ActionEvent event)throws URI.MalformedURIException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        
        FileSelected = fc.showOpenDialog(null);
        if (FileSelected != null) {
            try {
                filename= copy(FileSelected);
                image.setImage(new javafx.scene.image.Image(FileSelected.toURI().toURL().toString()));
            } catch (MalformedURLException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
        private String copy(File from) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        String dir = System.getProperty("user.dir");//get project source path
        File dest = new File(dir + "\\ressources\\" +sdf1.format(timestamp)+ from.getName());//add the full path /ressources + file name
        
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
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
     
        }
        return dest.getName();
    }

    @FXML
    private void valider(ActionEvent event) {
    }

    @FXML
    private void annulerRec(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("ListerReclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
    
}
