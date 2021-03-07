/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Answer;
import Entities.Question;
import Service.ServiceAnswer;
import Service.ServiceQuestion;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
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
    @FXML
    private Button modifierEtQuitter;
    @FXML
    private TableView<Answer> tableAnswers;
    @FXML
    private TableColumn<Answer, Integer> idColumn;
    @FXML
    private TableColumn<Answer, String> suggestionColumn;
    
    private Question q1;
    @FXML
    private TextField tfReponse;

    /**
     * Initializes the controller class and loads the answers to the question
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
//    Reusable function to reload the answers to question q1
    public void reloadAnswersList(){
    
        ServiceAnswer sa = new ServiceAnswer();
        
        try {
            tableAnswers.setItems(sa.ObservableListAnswers(this.q1));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEditQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        reloadAnswersList();
    }

    @FXML
    private void ajouterReponse(ActionEvent event) {
    
        ServiceAnswer sa = new ServiceAnswer();
        Answer a = new Answer();
        
        a.setSuggestion(tfReponse.getText());
        a.setQ(q1);
        
        sa.AddAnswer(a);
        
        reloadAnswersList();
    }

    @FXML
    private void supprimerReponse(ActionEvent event) {
    
        ServiceAnswer sa2 = new ServiceAnswer();
        
        ObservableList<Answer> answersSelected;
        answersSelected = tableAnswers.getSelectionModel().getSelectedItems();
        
        answersSelected.forEach(e -> {
            sa2.RemoveAnswer(e.getId());
        });
        
        
        reloadAnswersList();
        
    }

    @FXML
    private void modifierReponse(ActionEvent event) throws IOException {
        Answer editable = tableAnswers.getSelectionModel().getSelectedItem();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLAnswerEdit.fxml"));
        Parent root = modal.load();
        
        FXMLAnswerEditController editModal = modal.getController();
        
        editModal.showAnswer(editable,q1);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Answer");
        stage.showAndWait();
        
        reloadAnswersList();
    }
    
}
