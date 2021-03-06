/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Service.ServiceQuestion;
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
public class FXMLEditQuestionController implements Initializable {

    private Button scene1;
    @FXML
    private TextField tfQuestion;
    @FXML
    private TextField tfRightAnswer;
    
    private Question q1;
    @FXML
    private Button modifierEtQuitter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


//    Modifie une question après avoir remplit les champs et cliquer sur "Modifier"
    @FXML
    private void modifierQuestion(ActionEvent event) {
    
        ServiceQuestion sq = new ServiceQuestion();
        Question q = new Question();
        
        q.setQuestion(tfQuestion.getText());
        q.setAnswer(Integer.parseInt(tfRightAnswer.getText()));
        
        sq.EditQuestion(q1.getId(), q);
        
        Stage stage;
        Parent root;
        
        stage = (Stage) modifierEtQuitter.getScene().getWindow();
        stage.close();
    }
    
//    Affiche les informations de l'objet transmit par "FXMLTableQuestionController" et enregistre l'Objet dans la variable q1
    public void showInformation(Question q){
        q1 = new Question();
        q1.setAnswer(q.getAnswer());
        q1.setId(q.getId());
        q1.setQuestion(q.getQuestion());
        
        tfQuestion.setText(q.getQuestion());
        tfRightAnswer.setText(String.valueOf(q.getAnswer()));
    }
    
}
