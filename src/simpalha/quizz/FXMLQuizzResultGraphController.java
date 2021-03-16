/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.quizz;

import com.mysql.jdbc.Util;
import entities.Quizz;
import entities.QuizzResult;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import services.ServiceQuizz;
import services.ServiceQuizzResult;
import simpalha.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Parsath
 */
public class FXMLQuizzResultGraphController implements Initializable {

    private TableView<QuizzResult> LAffiche;
    @FXML
    private LineChart<String, Integer> progressionChart;
    @FXML
    private CategoryAxis timeAxis;
    @FXML
    private NumberAxis resultsAxis;
    @FXML
    private Label lQuizzTitle;

    private int addedQuizzId;
    private int userId;
    @FXML
    private Button btExitGraph;
    @FXML
    private Label lbAverage;
    @FXML
    private Label lbAnnotation;
    
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
        double average = calculateAverageOfQuizzes(qr.findAllById(id));
        
        try {
            setAverageAnnotation(average);
//            LAffiche.setItems(qr.ObservableListQuizzResults(id));
            XYChart.Series series = loadSeries();
            progressionChart.getData().add(series);
            resultsAxis.setLowerBound(0);
            resultsAxis.setUpperBound(20);
            resultsAxis.setAutoRanging(false);
        } catch (Exception ex) {
            Logger.getLogger(FXMLQuizzResultGraphController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setAverageAnnotation(double average){
        
        lbAverage.setText(new DecimalFormat("#.00").format(average)+"/20");
        
        if(average<5){
            lbAverage.setTextFill(Color.web("#e84855"));
            lbAnnotation.setTextFill(Color.web("#e84855"));
            lbAnnotation.setText("Kids aren't getting smarter..");
            
        }
        else if(average>=5 && average<10)
            lbAnnotation.setText("It could be worse!");
        else if(average>=10 && average<15)
            lbAnnotation.setText("Getting close to excellence!");
        else{
            lbAverage.setTextFill(Color.web("#06ba63"));
            lbAnnotation.setTextFill(Color.web("#06ba63"));
            lbAnnotation.setText("Outstanding, or was your test too easy?");
        }
    }
    
    private double calculateAverage(List<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
          for (Integer mark : marks) {
              sum += mark;
          }
          return sum.doubleValue() / marks.size();
        }
        return sum;
    }
    
    private double calculateAverageOfQuizzes(List<QuizzResult> quizzList){
        
        List<Integer> quizzMarks = quizzList.stream().map(QuizzResult::getResult).collect(Collectors.toList());
        
        System.out.println(quizzMarks);
        
        return calculateAverage(quizzMarks);
    }
    
//    sets title of quizz
    public void setTitle(int id){
        Quizz q = new Quizz();
        ServiceQuizz sq = new ServiceQuizz();
        
        q = sq.findById(id);
        
        lQuizzTitle.setText("\""+q.getTitle() + "\" Quizz Progression");
    }
    
//    initializes addedQuizzId and userId and loads the table of Results
    public void showResults(int quizzId, int uId){
        userId = uId;
        addedQuizzId = quizzId;
        setTitle(quizzId);
        
        reloadResultsList(addedQuizzId);
    }

    @FXML
    private void exitGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/quizz/FXMLQuizz.fxml"
                    )
            );
            
            Parent root = loader.load();
            
            FXMLQuizzController tableController = loader.getController();
            
            tableController.initializeUserId(userId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/quizz/FXMLQuizz.fxml"
                    )
            );
            
            Parent root = loader.load();
            
            FXMLQuizzController tableController = loader.getController();
            
            tableController.initializeUserId(userId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } 
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
