/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Service.ServiceQuestion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Parsath
 */
public class FXMLAddQuestionController implements Initializable {
    
    private TextField tfQuestion;
    private TextField tfRightAnswer;
    private Label LAffiche;
    @FXML
    private Button scene2;

//    Changing scenes code
//    @FXML
//    private void changeScene(ActionEvent event) throws Exception {
//        Stage stage;
//        Parent root;
//        
//        stage = (Stage) scene2.getScene().getWindow();
//        root = FXMLLoader.load(getClass().getResource("FXMLQuestionEdit.fxml"));
//            
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//        
//    }

//    Popup Modal Code
    @FXML
    private void changeScene(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent modal;
        
        modal = FXMLLoader.load(getClass().getResource("FXMLQuestionEdit.fxml"));
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Question");
            
        Scene scene = new Scene(modal);
        stage.setScene(scene);
        stage.showAndWait();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    Ajoute une question après avoir remplit les champs et cliquer sur "Ajouter"
    @FXML
    private void ajouterQuestion(ActionEvent event) {
    
        ServiceQuestion sq = new ServiceQuestion();
        Question q = new Question();
        
        q.setQuestion(tfQuestion.getText());
        q.setRightAnswer(Integer.parseInt(tfRightAnswer.getText()));
        
        sq.AddQuestion(q);
        
    }

//    Affiche toutes les questions après avoir cliquer sur "Afficher"
    @FXML
    private void afficherQuestions(ActionEvent event) {
    
        ServiceQuestion sq = new ServiceQuestion();
        
        try {
            LAffiche.setText(sq.ReadQuestions().toString());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAddQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
