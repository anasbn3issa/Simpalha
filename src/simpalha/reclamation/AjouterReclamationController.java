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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
public class AjouterReclamationController implements Initializable {

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private Button confirmer;
    @FXML
    private TextField Idreportee;
    @FXML
    private TextField Idreported;
    @FXML
    private DatePicker dateRec;
    @FXML
    private DatePicker dateResolution;
    @FXML
    private TextArea des;
    @FXML
    private Button upload;
    @FXML
    private ToggleButton Record;
    @FXML
    private Button annuler;
    private File FileSelected;
    private String filename;
    private String wavfile;
    private JavaSoundRecorder javaSoundRecorder;
    private IServiceReclamation sr;
    @FXML
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sr = new IServiceReclamation();
        
                image.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        }
        );

        image.setOnDragDropped(
                new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event
            ) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        FileSelected = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath(🙁"C:\Users\X\Desktop\ScreenShot.6.png"
                        image.setImage(new javafx.scene.image.Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);

                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        }
        );
        
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        Reclamation Rec = new Reclamation();
        Rec.setDescription(des.getText());
        Rec.setFileSelected(filename);
        Rec.setRecord(wavfile);
        Rec.setId(id.getText());
        Rec.setIdreported(Idreported.getText());
        Rec.setIdreportee(Idreportee.getText());
        LocalDate L = dateRec.getValue();
        Rec.setDateRec(dateRec.getValue());
        Rec.setDateResolution(dateResolution.getValue());
        sr.Create(Rec);

        String title = "réclamation ajoutée avec succès ";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    @FXML
    private void upload(ActionEvent event)throws URI.MalformedURIException {
           FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");

        FileSelected = fc.showOpenDialog(null);
        if (FileSelected != null) {
            try {
                filename = copy(FileSelected);
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
        File dest = new File(dir + "\\ressources\\" + sdf1.format(timestamp) + from.getName());//add the full path /ressources + file name

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
            /////////////////
        }
        return dest.getName();
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
    private void annuler(ActionEvent event) {
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
