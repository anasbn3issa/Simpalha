/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.ressources;

import simpalha.ressources.*;
import entities.Ressources;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import static java.sql.JDBCType.NULL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceRessources;

/**
 *
 * @author α Ω
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    private Label labelaffiche;
    private TextField tfsupp;
    @FXML
    private TableView<Ressources> resourceslist;
    private ServiceRessources sr;
    @FXML
    private TextField tfsearch;
    private Ressources R1;

//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // afficher les elements initialement:
            sr = new ServiceRessources();

            TableColumn<Ressources, String> titleCol = new TableColumn<>("Title");
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Ressources, String> descriptionCol = new TableColumn<>("Description");
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            TableColumn<Ressources, String> pathCol = new TableColumn<>("Path");
            pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

            TableColumn<Ressources, String> pathModule = new TableColumn<>("Module");
            pathModule.setCellValueFactory(new PropertyValueFactory<>("module"));

            //y'affichili fl module fl path !!!!!!!!!!!!!!!!!!
            //preparation de la structure
            resourceslist.getColumns().add(titleCol);
            resourceslist.getColumns().add(descriptionCol);
            resourceslist.getColumns().add(pathCol);
            resourceslist.getColumns().add(pathModule);

            //affichage
            resourceslist.getItems().addAll(sr.Read());

            //Rech avancée +Title
            ServiceRessources sr2 = ServiceRessources.getInstance();
            ObservableList<Ressources> resslist2 = FXCollections.observableArrayList(sr2.Read());

            FilteredList<Ressources> filteredData = new FilteredList<>(resslist2, b -> true);
            tfsearch.textProperty().addListener(((observable, oldValue, newValue) -> {

                filteredData.setPredicate(e -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (e.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));
            SortedList<Ressources> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(resourceslist.comparatorProperty());
            resourceslist.setItems(sortedData);

//            
//            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //acces page wajdi via menu a gche
    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "P2P/P2PFXML.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //acces page cyrine via menu a gche
    @FXML
    private void showRESOURCES(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "ressources/FXMLDocument.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Afficher dans le label
//    private void AfficherRessource(ActionEvent event) {
//        ServiceRessources sr = new ServiceRessources();
//        try {
//            labelaffiche.setText(sr.Read().toString());
//            
//            } 
//        catch (SQLException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    /*
    //Supprimer une ressource version bouton
    private void SupprimerRessource(ActionEvent event) throws SQLException {
        //Verifier si champ id vide, erreur suppression
        if (tfsupp.getText().trim().isEmpty()) {
            Alert fail = new Alert(AlertType.INFORMATION);
            fail.setHeaderText("FAILURE! ");
            fail.setContentText("Please type the id of the resource you would like to delete :) ");
            fail.showAndWait();
        } //sinon, effectuer la suppression:
        else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Deleting a resource ..");
            alert.setHeaderText("Are you sure you would like to delete this resource?");
            alert.setContentText("Type OK to confirm, CANCEL to cancel.");
            Optional<ButtonType> result = alert.showAndWait();
            //verifier si l utilisateur veut reellement supprimer cette ressource.
            if (result.get() == ButtonType.OK) // si confirmé,
            {
                ServiceRessources sr = new ServiceRessources();
                int idsupp = Integer.parseInt(tfsupp.getText());
                Ressources R = new Ressources(idsupp);
                sr.Delete(R);
                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setHeaderText("Success");
                alert2.setContentText("Resource deleted!");
                alert2.showAndWait();
                //this.AfficherRessource(event); // mise à jour de la page
                //resourceslist.getItems().addAll(sr.Read());
                sr = new ServiceRessources();
                //affichage
                resourceslist.getItems().addAll(sr.Read());

            } else // sinon annuler
            {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }
     */
 /*version bouton
    //Redirection vers le formulaire de modification d'une ressource
    private void formulairemodif(ActionEvent event) {
        try {
            Parent page2 = FXMLLoader.load(getClass().getResource("UpdateRESOURCESFXML.fxml"));
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
    // Redirection vers le formulaire d'ajout d'une ressource
    @FXML
    private void formulaireajout(ActionEvent event) {
        try {
            Parent page2 = FXMLLoader.load(getClass().getResource("addRESOURCEFXML.fxml"));
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @FXML
//    private void Tri_id(ActionEvent event) throws SQLException {
////        resourceslist.getItems().clear();
//        resourceslist.getItems().addAll(sr.Tri_id());
//
//    }
    private void Tri_title(ActionEvent event) throws SQLException {
//        resourceslist.getItems().clear();
        resourceslist.getItems().addAll(sr.Tri_title());

    }

    //DELETE L IMAGE ASSOCIATED LL RESOURCE
    @FXML
    private void DeleteSelected(ActionEvent event) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deleting a resource ..");
        alert.setHeaderText("Are you sure you would like to delete this resource?");
        alert.setContentText("Type OK to confirm, CANCEL to cancel.");
        Optional<ButtonType> result = alert.showAndWait();
        //verifier si l utilisateur veut reellement supprimer cette ressource.
        if (result.get() == ButtonType.OK) // si confirmé,
        {
            Ressources R = new Ressources(resourceslist.getSelectionModel().getSelectedItem().getIdR());
            ServiceRessources sr = new ServiceRessources();
            sr.Delete(resourceslist.getSelectionModel().getSelectedItem());
            //  resourceslist.getItems().removeAll(resourceslist.getSelectionModel().getSelectedItem());
            File file = new File ("\\ressources\\"+resourceslist.getSelectionModel().getSelectedItem().getPath());
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            sr.Delete(R); //suppression de la base
            
         
           // resourceslist.refresh();
            
            Alert alert2 = new Alert(AlertType.CONFIRMATION);
            alert2.setHeaderText("Success");
            alert2.setContentText("Resource deleted!");
            alert2.showAndWait(); 
            
            resourceslist.refresh();
                   for (int i = 0; i < sr.Read().size(); i++) {
                resourceslist.getItems().add(sr.Read().get(i));}
            
            //this.AfficherRessource(event); // mise à jour de la page
            //resourceslist.getItems().addAll(sr.Read());
            
//            resourceslist.refresh();
//            resourceslist.getItems().addAll(sr.Read());
            //affichage ( mise a jour)
            //resourceslist.getItems().clear();
            //cleari el list mouch el tableview
            //resourceslist.getItems().addAll(sr.Read());
      
        } else // sinon annuler
        {
            // ... user chose CANCEL or closed the dialog
        }
//        sr.Delete(resourceslist.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void UpdateSelected(ActionEvent event) throws SQLException {

        if (resourceslist.getSelectionModel().getSelectedItem().getIdR() == 0) {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setHeaderText("WARNING");
            alert2.setContentText("Select a resource to Update!");
            alert2.showAndWait();

        } else {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Upddating a resource ..");
            alert.setHeaderText("Are you sure you would like to update this resource?");
            alert.setContentText("Type OK to confirm, CANCEL to cancel.");
            Optional<ButtonType> result = alert.showAndWait();
            //verifier si l utilisateur veut reellement supprimer cette ressource.
            if (result.get() == ButtonType.OK) // si confirmé,
            {
                int id = resourceslist.getSelectionModel().getSelectedItem().getIdR();
//            Ressources R2 = new Ressources(resourceslist.getSelectionModel().getSelectedItem().getIdR(),resourceslist.getSelectionModel().getSelectedItem().getPath()
//            ,resourceslist.getSelectionModel().getSelectedItem().getDescription(),resourceslist.getSelectionModel().getSelectedItem().getTitle());
//            FXMLLoader loader = new FXMLLoader();
//             System.out.println(R2);
//            System.out.println(id);

//            UpdateRESOURCESFXMLController ctrl = loader.getController();
//            ctrl.initRess(id);
//            ctrl.R = R2;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateRESOURCESFXML.fxml"));

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                    stage.setScene(
                            new Scene(loader.load())
                    );

                    //FXMLLoader loader = new FXMLLoader();
                    UpdateRESOURCESFXMLController ctrl = loader.getController();
                    ctrl.initRess(id);

                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else // sinon annuler
            {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }

    @FXML
    private void ToResourceImage(ActionEvent event) {
        int id = resourceslist.getSelectionModel().getSelectedItem().getIdR();
        System.out.println(id);
        try {
            //Charger la page (destination)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ImageViewFXML.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );

            //transport infos
            ImageViewFXMLController ctrl = loader.getController();
            ctrl.R1 = sr.Search(id);

//affichage
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showExams(ActionEvent event) {

        try {
            //Charger la page (destination)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExamsFXML.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
