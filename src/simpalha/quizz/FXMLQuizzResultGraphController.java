/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import entities.Quizz;
import entities.QuizzResult;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceQuestion;
import services.ServiceQuizz;
import services.ServiceQuizzResult;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzResultGraphController implements Initializable {

    @FXML
    private TableView<QuizzResult> LAffiche;
    @FXML
    private TableColumn<QuizzResult, Integer> idColumn;
    @FXML
    private TableColumn<QuizzResult, Integer> resultColumn;
    @FXML
    private TableColumn<QuizzResult, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<QuizzResult, Integer> quizzColumn;
    @FXML
    private TableColumn<QuizzResult, Integer> studentColumn;
    @FXML
    private LineChart<String, Integer> progressionChart;
    @FXML
    private CategoryAxis timeAxis;
    @FXML
    private NumberAxis resultsAxis;
    @FXML
    private Label lQuizzTitle;

    private int addedQuizzId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

//    Populating the Series of an XYChart that will be implemented in our LineChart
    public XYChart.Series loadSeries(){
        ServiceQuizzResult sqr = new ServiceQuizzResult();
        XYChart.Series series = new XYChart.Series();
        series.setName("Quizz Result for a student at a certain point");
        ArrayList<QuizzResult> quizzChart = new ArrayList<>(sqr.findAllById(addedQuizzId));
        
        quizzChart.forEach((r) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String formattedDateTime = r.getDate().format(formatter);
            
            series.getData().add(new XYChart.Data<>(formattedDateTime,r.getResult()));
        });
        
        return series;
    }
    
//    Reusable function to reload the table
    public void reloadResultsList(int id) {
    
        ServiceQuizzResult qr = new ServiceQuizzResult();
        
        try {
            LAffiche.setItems(qr.ObservableListQuizzResults(id));
            
            XYChart.Series series = loadSeries();
            progressionChart.getData().add(series);
            resultsAxis.setLowerBound(0);
            resultsAxis.setUpperBound(20);
            resultsAxis.setAutoRanging(false);
        } catch (Exception ex) {
            Logger.getLogger(FXMLQuizzResultGraphController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    sets title of quizz
    public void setTitle(int id){
        Quizz q = new Quizz();
        ServiceQuizz sq = new ServiceQuizz();
        
        q = sq.findById(id);
        
        lQuizzTitle.setText("\""+q.getTitle() + "\" Quizz Progression");
    }
    
//    initializes addedQuizzId and loads the table of Results
    public void showResults(int quizzId){
        addedQuizzId = quizzId;
        setTitle(quizzId);
        
        reloadResultsList(addedQuizzId);
    }


    @FXML
    private void editQuizz(ActionEvent event) {
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
