/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users.admin;

import entities.Candidature;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServiceCandidature;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class GestionCandidaturesController implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private Button refuser;
    @FXML
    private Button supprimer;
    @FXML
    private TextField recherche;
    @FXML
    private CheckBox attentecheck;
    @FXML
    private CheckBox validéecheck;
    @FXML
    private CheckBox refuséecheck;
    @FXML
    private TableView<Candidature> table;
    @FXML
    private TableColumn<?, ?> idu;
    @FXML
    private TableColumn<?, ?> statuscol;
    @FXML
    private TableColumn<?, ?> specialitecol;
    @FXML
    private TableColumn<?, ?> idc;
    @FXML
    private TableColumn<?, ?> datecol;
    @FXML
    private TableColumn<?, ?> stucol1;
    @FXML
    private TableColumn<?, ?> statuscol1;
    @FXML
    private PieChart statusapp;
    @FXML
    private BarChart<?, ?> appBarChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        datecol.setCellValueFactory(new PropertyValueFactory<>("dateToString"));
        //statuscol.setCellValueFactory(new PropertyValueFactory<>("statusToString"));
        specialitecol.setCellValueFactory(new PropertyValueFactory<>("spécialité"));
        idu.setCellValueFactory(new PropertyValueFactory<>("idu"));
        idc.setCellValueFactory(new PropertyValueFactory<>("idc"));
        System.out.println(idu);
        TableColumn<Candidature, Double> progressCol = new TableColumn("Avancement");
        progressCol.setCellValueFactory(new PropertyValueFactory<Candidature, Double>(
                ""));
        progressCol.setCellFactory(ProgressBarTableCell.<Candidature>forTableColumn());
        Callback<TableColumn<Candidature, Double>, TableCell<Candidature, Double>> cellFactoryProgress
                = new Callback<TableColumn<Candidature, Double>, TableCell<Candidature, Double>>() {
            @Override
            public TableCell<Candidature, Double> call(TableColumn<Candidature, Double> reclamationStringTableColumn) {
                final TableCell<Candidature, Double> cell = new TableCell<Candidature, Double>() {
                    ProgressBar progress = new ProgressBar(0);

                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Candidature cdr = getTableView().getItems().get(getIndex());
                            String text = cdr.getStatusToString();
                            if (text.equals("En attente")) {
                                progress.setProgress(0.5);
                            } else if (text.equals("Confirmée")) {
                                progress.setProgress(1);
                            } else if (text.equals("Refusée")) {
                                progress.setProgress(0);
                            }
                            setGraphic(progress);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        progressCol.setCellFactory(cellFactoryProgress);
        table.getColumns().add(progressCol);

        afficherCandidature();
        //etat candidature pie chart
        ServiceCandidature cs = new ServiceCandidature();
        ArrayList<Candidature> candidatures = cs.Read();
        ObservableList<PieChart.Data> etatPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("En attente (" + candidatures.stream().filter(c -> c.getStatus() == 1).count() + ")", candidatures.stream().filter(c -> c.getStatus() == 1).count()),
                new PieChart.Data("Confirmée (" + candidatures.stream().filter(c -> c.getStatus() == 2).count() + ")", candidatures.stream().filter(c -> c.getStatus() == 2).count()),
                new PieChart.Data("Refusée (" + candidatures.stream().filter(c -> c.getStatus() == 3).count() + ")", candidatures.stream().filter(c -> c.getStatus() == 3).count())
        );
        statusapp.setData(etatPieChartData);
    }

    private void afficherCandidature() {
        ServiceCandidature cs = new ServiceCandidature();
        ObservableList<Candidature> cand = FXCollections.observableArrayList(cs.Read());
        System.out.println(cs.Read());
        table.setItems(cand);

    }

    private void trierCandidature() {
        table.getItems().clear();
        table.getItems().clear();
        ServiceCandidature cs = new ServiceCandidature();
        ObservableList<Candidature> candi = FXCollections.observableArrayList(cs.trierCandidature(recherche.getText(), attentecheck.isSelected(), validéecheck.isSelected(), refuséecheck.isSelected()));
        table.setItems(candi);
        table.refresh();
    }

    @FXML
    private void goToViewPosts(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showP2P(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/P2P/P2PFXML.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void candidatures(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("CandidatureAdmin.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void validercandidature(ActionEvent event) {
        ArrayList<String> Candidature = new ArrayList<>();
        Candidature c = (Candidature) table.getSelectionModel().getSelectedItem();
        ServiceCandidature cs = new ServiceCandidature();

        if (!Candidature.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation impossible");
            alert.setHeaderText("Vérifiez votre candidature");
            alert.setContentText(Candidature.stream().reduce("", (noms, nom) -> noms + nom + "\n"));
            alert.showAndWait();
        } else {
            cs.modifierEtatCandidature(c, 2);
            c.setStatus(2);
            changerEtatBoutons(2);
            table.refresh();
            c.getStatusToString();

        }
    }

    private void changerEtatBoutons(int status) {
        switch (status) {
            case 1:
                //en attente
                valider.setDisable(true);
                refuser.setDisable(true);

                supprimer.setDisable(true);
                break;
            case 2:
                //confirmee
                valider.setDisable(true);
                refuser.setDisable(false);
                supprimer.setDisable(true);
                break;
            case 3:
                //annulee
                valider.setDisable(false);
                supprimer.setDisable(true);
                refuser.setDisable(true);
                break;
            default:
                valider.setDisable(true);
                refuser.setDisable(true);
                supprimer.setDisable(true);
                break;
        }
    }

    @FXML
    private void refuserc(ActionEvent event) {
        ServiceCandidature cs = new ServiceCandidature();
        Candidature c = (Candidature) table.getSelectionModel().getSelectedItem();
        cs.modifierEtatCandidature(c, 3);
        c.setStatus(3);
        changerEtatBoutons(3);
        table.refresh();
    }

    @FXML
    private void supprimercandidature(ActionEvent event) {
        ServiceCandidature cs = new ServiceCandidature();
        Candidature c = (Candidature) table.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            cs.Delete(c);
            table.getItems().remove(c);
            c = (Candidature) table.getSelectionModel().getSelectedItem();
        }
        if (c != null) {
            /* afficher();*/
            changerEtatBoutons(c.getStatus());
        } else {
            table.getItems().removeAll(table.getItems());
            // changerEtatBoutons(-1);
        }
        table.refresh();
    }

    @FXML
    private void recherchecandidature(ActionEvent event) {
        trierCandidature();

    }

    @FXML
    private void triCandidature(MouseEvent event) {
        trierCandidature();
        table.refresh();
    }

}
