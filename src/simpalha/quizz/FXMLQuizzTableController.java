/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Quizz;
import java.io.IOException;
import services.ServiceQuizz;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simpalha.FXMLDocumentController;

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
    @FXML
    private Button btShowGraph;

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

//    Opens the Quizz addition Modal (FXMLQuizzAddController)
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

//    Opens the Quizz editing Modal and transmits the id of the Quizz to FXMLQuizzEditController
    @FXML
    private void editQuizz(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        
//        à effacer une fois on intégre la Classe Helper
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

//    deletes a quizz by its ID
    @FXML
    private void deleteQuizz(ActionEvent event) {
    
        ServiceQuizz sq2 = new ServiceQuizz();
        
        ObservableList<Quizz> quizzesSelected;
        quizzesSelected = LAffiche.getSelectionModel().getSelectedItems();
        
        quizzesSelected.forEach(e -> {
            sq2.Delete(e);
        });
        
        
        reloadQuizzesList();
    }

    @FXML
    private void showGraph(ActionEvent event) throws Exception {
        Quizz editable = LAffiche.getSelectionModel().getSelectedItem();
        int quizzId;
        
        quizzId = editable.getId();
        
        FXMLLoader modal = new FXMLLoader(getClass().getResource("FXMLQuizzResultGraph.fxml"));
        Parent root = modal.load();
        
        FXMLQuizzResultGraphController editModal = modal.getController();
        
        editModal.showResults(quizzId);
        
        Stage stage;
        stage = (Stage) btShowGraph.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quizz");
        stage.show();
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showQuizz(MouseEvent event) {
    }

    @FXML
    private void showDashboard(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
