/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.suggestion;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Suggestion;
import entities.Users;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import services.IServiceSuggestion;
import services.ServiceNotification;
import services.ServiceUsers;
import simpalha.admin.FXMLDocumentController;
import simpalha.admin.quizz.FXMLAdminQuizzTableController;
import simpalha.admin.reclamation.DetailsReclamationController;
import simpalha.notification.FXMLNotificationController;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class SuggestionAdminController implements Initializable {

    private IServiceSuggestion service;
    private String idSugg;
    private IServiceSuggestion sr;
    private UserSession usr;
    Suggestion sugg;
    private File wavFile;
    private SourceDataLine sourceLine;
    private final int BUFFER_SIZE = 128000;

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private TextField Sujet;
    @FXML
    private TextArea Suggestion;
    @FXML
    private Button annuler;
    @FXML
    private ToggleButton Record;
    @FXML
    private Text currentUserNameLabel;
    ServiceUsers serviceUsers;
    UserSession userSession;
    Users currentUser;
    int userId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sr = new IServiceSuggestion();
        
        serviceUsers = new ServiceUsers();
        userSession = UserSession.getInstace(0);
        userId = userSession.getUserid();
        currentUser = serviceUsers.findById(userId);

        //currentUserNameLabel.setText(currentUser.getUsername());

        usr = UserSession.getInstace(0);
        System.out.println(usr);
        launchServiceNotification();
        
        
        sugg = service.findById(Integer.valueOf(idSugg));
        System.out.println(sugg);
        Sujet.setText(sugg.getSujet());
        Suggestion.setText(sugg.getSuggestion());
        
    }

    void initData(String id_Sugg) {
        idSugg = id_Sugg;
    }

    @FXML
    private void annuler(ActionEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/admin/suggestion/ListeSuggestions.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    public void launchServiceNotification() {
        ServiceNotification sn = new ServiceNotification(usr.getUserid());

        Thread t = new Thread(sn);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void notificationsShow(MouseEvent event) {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("simpalha/notification/FXMLNotification.fxml"));
        Parent root = null;
        try {
            root = modal.load();
        } catch (IOException io) {
        };

        FXMLNotificationController editModal = modal.getController();

        editModal.reloadAllNotificationsList(usr.getUserid());

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notifications");
        stage.showAndWait();
    }

    // f hethy lezm n3awd nredefini l bouton eli bsh yhezni lel page ViewPosts (contact wajdi)
    @FXML
    private void goToViewPosts(MouseEvent event) {
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

    private void AddNewPost(ActionEvent event) {

        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/AddNewPost.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/P2P/P2PFXML.fxml"
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
    private void candidatures(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/CandidatureUser.fxml"
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
    private void profile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/Profile.fxml"
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
    private void showQuizz(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/quizz/FXMLQuizzTable.fxml"
                    )
            );

            Parent root = loader.load();

            FXMLAdminQuizzTableController tableController = loader.getController();

            tableController.initializeUserId(usr.getUserid());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Record(ActionEvent event) {
        String dir = System.getProperty("user.dir");//get project source path
        wavFile = new File(dir + "\\ressources\\" + sugg.getRecord());//add the full path /ressources + file name
        System.out.println(wavFile.getPath());
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(wavFile);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(DetailsReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetailsReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = stream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }
}
