/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.P2P;

import entities.Feedback;
import entities.Meet;
import entities.Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceFeedback;
import services.ServiceP2P;
import services.ServiceUsers;

/**
 * FXML Controller class
 *
 * @author α Ω
 */
public class NewFeedbackFXMLController implements Initializable {

    @FXML
    private TextField helper;
    @FXML
    private TextArea feedback;
    @FXML
    private Button confirm;
    
    private String meetId;
    private ServiceP2P service;
    private ServiceUsers serviceUser;
    private ServiceFeedback serviceFeedback;

    
    private Meet meet;
    @FXML
    private TextField specialite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                Platform.runLater(() -> {
                     service = new ServiceP2P();
                     serviceUser = new ServiceUsers();
                     serviceFeedback = new ServiceFeedback();
                     
                     meet = service.findById(meetId);
                     Users h = serviceUser.findById(meet.getId_helper());
                     meet.setHelperDisplay(h.getUsername());
                     
                     helper.setText(meet.getHelperDisplay());
                     specialite.setText(meet.getSpecialite());
                });
        
    }

    void initData(String id) {
        meetId = id;
    }

    @FXML
    private void confirm(ActionEvent event) {
        
        Feedback f = new Feedback(meetId, feedback.getText());
        serviceFeedback.Create(f);
        f = serviceFeedback.findByMeetId(meetId);
        meet.setFeedback_id(f.getId());
        service.Update(meet);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        stage.close();
    }

    
    
}
