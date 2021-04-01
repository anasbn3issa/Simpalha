/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.reclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.Maconnexion;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class LineChartController implements Initializable {

    @FXML
    private AnchorPane view;
    @FXML
    private Button Imprimer;
    @FXML
    private Button annuler;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                
        double total = 0;
            DecimalFormat df2 = new DecimalFormat(".##");
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle("Nombre de reclamations par jour");
            stage.setWidth(600);
            stage.setHeight(600);


            //defining the axes
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Date");
            //creating the chart
            final LineChart<String,Number> lineChart =
                    new LineChart<String,Number>(xAxis,yAxis);
            lineChart.getData().add(buildDataLineChart());
            ((Group) scene.getRoot()).getChildren().add(lineChart);
            stage.setScene(scene);
            view.getChildren().clear();
            view.getChildren().setAll(lineChart);
    } 
    
            private XYChart.Series<String,Number> buildDataLineChart() {
             XYChart.Series series = new XYChart.Series();
             series.setName("Nombre de reclamations par jour");

              ResultSet rs = null;
              XYChart.Series d;
            try {
            String requete = "SELECT reclamation.dateRec,COUNT(reclamation.Id) as nbr FROM reclamation group by DAYNAME(reclamation.dateRec)";

            Statement pst = Maconnexion.getInstance().getConnection().prepareStatement(requete); // import java.sql.Statement
            rs = pst.executeQuery(requete);
            while (rs.next())
            {
                series.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            }

            return series;

            } catch (Exception e) {

            System.out.println("Error on DB connection BuildDataLineChart");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());

             }
            return series;
        
    }

            
          public void print() {

        Printer printer = Printer.getDefaultPrinter();

        PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {

            job.getJobSettings().setJobName("TestPrint");

            if (job.showPrintDialog(this.rootPane.getScene().getWindow())) {

                job.printPage(layout, this.view);
            } else {
                System.out.println("Print canceled.");
            }

            job.endJob();
        }
     
 
}       
            
    @FXML
    private void Imprimer(ActionEvent event) {
       print();
    }

    @FXML
    private void annuler(ActionEvent event) {
            Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/admin/reclamation/ListeReclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }
    
}
