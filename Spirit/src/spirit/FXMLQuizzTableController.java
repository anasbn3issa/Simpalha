/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spirit;

import Entities.Question;
import Entities.Quizz;
import Service.ServiceQuestion;
import Service.ServiceQuizz;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzTableController implements Initializable {

    @FXML
    private TableView<Quizz> LAffiche;
    @FXML
    private TableColumn<Quizz, Integer> idColumn;
    @FXML
    private TableColumn<Quizz, String> quizzColumn;
    @FXML
    private TableColumn<Quizz, String> subjectColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reloadQuizzesList();
    }    
    
//    Reusable function to reload the table
    public void reloadQuizzesList(){
    
        ServiceQuizz sq = new ServiceQuizz();
        
        try {
            LAffiche.setItems(sq.ObservableListAllQuizzes());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLQuestionTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addQuizz(ActionEvent event) throws Exception {
        
        Stage stage = new Stage();
        Parent modal;
        
        modal = FXMLLoader.load(getClass().getResource("FXMLQuizzAdd.fxml"));
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Quizz Creation Modal");
            
        Scene scene = new Scene(modal);
        stage.setScene(scene);
        stage.showAndWait();
        
        reloadQuizzesList();
    }

    @FXML
    private void editQuizz(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        
//        à effacer une fois intégre la Classe Helper
        editable.setHelper(1);
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEdit.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEditController editModal = modal.getController();
        
        editModal.showInformation(editable);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
        reloadQuizzesList();
    }

    @FXML
    private void deleteQuizz(ActionEvent event) {
    
        ServiceQuizz sq2 = new ServiceQuizz();
        
        ObservableList<Quizz> quizzesSelected;
        quizzesSelected = LAffiche.getSelectionModel().getSelectedItems();
        
        quizzesSelected.forEach(e -> {
            sq2.RemoveQuizz(e.getId());
        });
        
        
//        reloadQuestionsList(3);
        reloadQuizzesList();
    }
    
}
