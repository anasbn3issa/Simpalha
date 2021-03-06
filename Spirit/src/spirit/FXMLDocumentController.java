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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Parsath
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField tfQuestion;
    @FXML
    private TextField tfRightAnswer;
    @FXML
    private Label LAffiche;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    Ajoute une question après avoir remplit les champs et cliquer sur "Ajouter"
    @FXML
    private void AjouterQuestion(ActionEvent event) {
    
        ServiceQuestion sq = new ServiceQuestion();
        Question q = new Question();
        
        q.setQuestion(tfQuestion.getText());
        q.setRightAnswer(Integer.parseInt(tfRightAnswer.getText()));
        
        sq.AddQuestion(q);
        
    }

//    Affiche toutes les questions après avoir cliquer sur "Afficher"
    @FXML
    private void AfficherQuestions(ActionEvent event) {
    
        ServiceQuestion sq = new ServiceQuestion();
        
        try {
            LAffiche.setText(sq.ReadQuestions().toString());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
