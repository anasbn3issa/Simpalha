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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Parsath
 */
public class FXMLAddQuestionController implements Initializable {
    
    @FXML
    private TextField tfQuestion;
    @FXML
    private TextField tfRightAnswer;
    private TableView<Question> LAffiche;
    @FXML
    private Button ajouterEtQuitter;
    
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
        q.setAnswer(Integer.parseInt(tfRightAnswer.getText()));
        
        sq.AddQuestion(q);
        
        Stage stage;
        Parent root;
        
        stage = (Stage) ajouterEtQuitter.getScene().getWindow();
        stage.close();
        
    }
    
}
