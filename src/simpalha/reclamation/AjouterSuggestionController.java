/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.reclamation;

import static com.teamdev.jxbrowser.deps.io.spine.ui.Language.sr;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Suggestion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.IServiceSuggestion;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.JavaSoundRecorder;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AjouterSuggestionController implements Initializable {

    private IServiceSuggestion service;
    private String idSugg;

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private TextField Sujet;
    @FXML
    private TextField Suggestion;
    @FXML
    private ToggleButton Record;
    private JavaSoundRecorder javaSoundRecorder;
    private String wavfile;
    @FXML
    private Button annuler;
    @FXML
    private Button confirmer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new IServiceSuggestion();
    }

    @FXML
    private void notificationsShow(MouseEvent event) {
    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    @FXML
    private void candidatures(MouseEvent event) {
    }

    @FXML
    private void RecSugg(MouseEvent event) {
    }

    @FXML
    private void profile(MouseEvent event) {
    }

    void initData(String id_Sugg) {
        idSugg = id_Sugg;
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
            loader = FXMLLoader.load(getClass().getResource("ReclamationSuggestions.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        Suggestion s = new Suggestion();
        s.setSujet(Sujet.getText());
        s.setSuggestion(Suggestion.getText());
        service.Create(s);
        
        
        String title = "suggestion ajoutée avec succès ";
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle(title);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

}
