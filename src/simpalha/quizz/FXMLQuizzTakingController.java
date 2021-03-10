/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Quizz;
import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceQuizz;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzTakingController implements Initializable {

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
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    @FXML
    private void takeQuizz(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        
//        à effacer une fois on intégre la Classe Helper
        editable.setHelper(1);
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzEval.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzEvalController editModal = modal.getController();
        
        editModal.showInformation(editable.getId());
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Question");
        stage.showAndWait();
        
        reloadQuizzesList();
    }
    
}
