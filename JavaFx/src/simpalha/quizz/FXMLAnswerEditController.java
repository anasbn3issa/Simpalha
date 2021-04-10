/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Answer;
import entities.Question;
import java.io.IOException;
import services.ServiceAnswer;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLAnswerEditController implements Initializable {

    @FXML
    private TextField tfAnswer;
    @FXML
    private Button modifierEtQuitter;
    
    private Answer a1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    confirme la modification de l'Objet answer choisit et ferme la scène
    @FXML
    private void modifierReponse(ActionEvent event) {
        
        System.out.println(a1);
    
        ServiceAnswer sa = new ServiceAnswer();
        Answer a = new Answer();
        
        a.setSuggestion(tfAnswer.getText());
        a.setQ(a1.getQ());
        a.setId(a1.getId());
        
        sa.Update(a);
        
        Stage stage;
        Parent root;
        
        stage = (Stage) modifierEtQuitter.getScene().getWindow();
        stage.close();
    }
    
//    Affiche les informations de l'objet Answer transmit par "FXMLEditQuestionController" et enregistre l'Objet dans la variable a1
    public void showAnswer(Answer a, Question q){
        a1 = new Answer();
        a1.setSuggestion(a.getSuggestion());
        a1.setId(a.getId());
        a1.setQ(q);
        
        System.out.println(a1);
        
        tfAnswer.setText(a.getSuggestion());
    }
    
}
