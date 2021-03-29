/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.quizz;

import entities.Quizz;
import services.ServiceQuizz;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLAdminQuizzAddController implements Initializable {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfSubject;
    @FXML
    private Button addQuizzButton;
    private int userId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void initializeUser(int uId){
        userId = uId;
    }

//    Creates a "Quizz" object, adds it to the DB and transmits its' id to the FXMLAdminQuestionTableController so the user can create Questions to the newly created Quizz
    @FXML
    private void addQuizz(ActionEvent event) throws Exception {
    
        ServiceQuizz sq = new ServiceQuizz();
        Quizz q = new Quizz();
        
        q.setTitle(tfTitle.getText());
        q.setSubject(tfSubject.getText());
        q.setHelper(userId);
        
        sq.Create(q);
        
        Stage stage;
        stage = (Stage) addQuizzButton.getScene().getWindow();
        
        Parent root;
        
        FXMLLoader addModal = new FXMLLoader(getClass().getResource("/simpalha/admin/quizz/FXMLQuestionTable.fxml"));
        root = addModal.load();
        FXMLAdminQuestionTableController addQuizzModal = addModal.getController();
        addQuizzModal.addInformation(sq.LastAddedQuizzId());
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz Creation Modal");
        stage.show();
    }
    
}
