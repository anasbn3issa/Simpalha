/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceCandidature;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class CandidatureAdminController implements Initializable {

    @FXML
    private Button valider;

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
    private TableColumn<?, ?> stucol;
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
    private Button refuser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stucol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("dateToString"));
        statuscol.setCellValueFactory(new PropertyValueFactory<>("statusToString"));
        specialitecol.setCellValueFactory(new PropertyValueFactory<>("spécialité"));
        idu.setCellValueFactory(new PropertyValueFactory<>("idu"));
        idc.setCellValueFactory(new PropertyValueFactory<>("idc"));
        afficherCandidature();
    }

    private void afficherCandidature() {
        ServiceCandidature cs = new ServiceCandidature();
        ObservableList<Candidature> cand = FXCollections.observableArrayList(cs.getAllCandidatures());
        table.setItems(cand);
    }

    /*private void trierCandidature() {
        table.getItems().clear();
        ServiceCandidature cs=new ServiceCandidature();
        ObservableList<Candidature> commandesObs = FXCollections.observableArrayList(cs.trierCommande(recherche.getText(), attentecheck.isSelected(), validéecheck.isSelected(), refuséecheck.isSelected()));
        table.setItems(commandesObs);
    }*/
    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {

        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource(".fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

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
                refuser.setDisable(false);
                break;
            default:
                valider.setDisable(true);
                refuser.setDisable(true);
                supprimer.setDisable(true);
                break;
        }
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
            changerEtatBoutons(-1);
        }
    }

    @FXML
    private void recherchecandidature(ActionEvent event) {
        /* trierCandidature(); */
    }

    @FXML
    private void candidatures(MouseEvent event) {
        //note that on this line you can substitue "Screen2.fxml" for a string chosen prior to this line.
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
    private void refuserc(ActionEvent event) {
        ServiceCandidature cs = new ServiceCandidature();
        Candidature c = (Candidature) table.getSelectionModel().getSelectedItem();
        cs.modifierEtatCandidature(c, 3);
        c.setStatus(3);
        changerEtatBoutons(3);
        table.refresh();
    }

}
