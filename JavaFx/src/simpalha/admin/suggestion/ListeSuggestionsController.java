/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.admin.suggestion;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import entities.Suggestion;
import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.IServiceSuggestion;
import services.ServiceNotification;
import services.ServiceUsers;
import simpalha.admin.FXMLDocumentController;
import simpalha.admin.quizz.FXMLAdminQuizzTableController;
import simpalha.notification.FXMLNotificationController;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ListeSuggestionsController implements Initializable {

    @FXML
    private FontAwesomeIcon btNotificationShow;
    @FXML
    private Button annuler;
    @FXML
    private TableView<Suggestion> listeSugg;
    private IServiceSuggestion service;
    private UserSession usr;
    @FXML
    private Text currentUserNameLabel;
    ServiceUsers serviceUsers;
    UserSession userSession;
    Users currentUser;
    int userId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new IServiceSuggestion();
        
        serviceUsers = new ServiceUsers();
        userSession = UserSession.getInstace(0);
        userId = userSession.getUserid();
        currentUser = serviceUsers.findById(userId);

        //currentUserNameLabel.setText(currentUser.getUsername());

        usr = UserSession.getInstace(0);
        System.out.println(usr);
        launchServiceNotification();

        
        TableColumn<Suggestion, String> Id = new TableColumn<>("Id");
        Id.setCellValueFactory(new PropertyValueFactory<>("Id_Sugg"));

        TableColumn<Suggestion, String> topic = new TableColumn<>("topic");
        topic.setCellValueFactory(new PropertyValueFactory<>("Sujet"));

        TableColumn<Suggestion, String> description = new TableColumn<>("description");
        description.setCellValueFactory(new PropertyValueFactory<>("Suggestion"));

        TableColumn modCol = new TableColumn("Details");
        modCol.setCellValueFactory(new PropertyValueFactory<>("Details"));
        Callback<TableColumn<Suggestion, String>, TableCell<Suggestion, String>> cellFactoryDetails
                = //
                new Callback<TableColumn<Suggestion, String>, TableCell<Suggestion, String>>() {
            @Override
            public TableCell call(final TableColumn<Suggestion, String> param) {
                final TableCell<Suggestion, String> cell = new TableCell<Suggestion, String>() {

                    final Button Details = new Button("Details");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Suggestion Suggestion = getTableView().getItems().get(getIndex());
                            Details.setOnAction(event -> {
                              try {
                                    FXMLLoader loader = new FXMLLoader(
                                            getClass().getResource(
                                                    "/simpalha/admin/suggestion/SuggestionAdmin.fxml"
                                            )
                                    );

                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );

                                    SuggestionAdminController controller = loader.getController();
                                    controller.initData(String.valueOf(Suggestion.getId_Sugg()));

                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(ListeSuggestionsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });

                            setGraphic(Details);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        modCol.setCellFactory(cellFactoryDetails);

        listeSugg.getColumns().add(Id);
        listeSugg.getColumns().add(topic);
        listeSugg.getColumns().add(description);
        listeSugg.getColumns().add(modCol);

        List<Suggestion> list = service.Read();
        System.out.println(list);
        for (Suggestion suggestion : list) {
            listeSugg.getItems().add(suggestion);
        }
        // TODO
    }

    @FXML
    private void annuler(ActionEvent event) {
              Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/admin/reclamation/ReclamationSuggestionAdmin.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
     
    }

    public void launchServiceNotification() {
        ServiceNotification sn = new ServiceNotification(usr.getUserid());

        Thread t = new Thread(sn);
        t.setDaemon(true);
        t.start();
    }
    
    @FXML
    private void notificationsShow(MouseEvent event) {
        FXMLLoader modal = new FXMLLoader(getClass().getResource("simpalha/notification/FXMLNotification.fxml"));
        Parent root = null;
        try {
            root = modal.load();
        } catch (IOException io) {
        };

        FXMLNotificationController editModal = modal.getController();

        editModal.reloadAllNotificationsList(usr.getUserid());

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notifications");
        stage.showAndWait();
    }

    // f hethy lezm n3awd nredefini l bouton eli bsh yhezni lel page ViewPosts (contact wajdi)
    @FXML
    private void goToViewPosts(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/ViewPosts.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    private void AddNewPost(ActionEvent event) {

        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("/simpalha/post/AddNewPost.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void showP2P(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/P2P/P2PFXML.fxml"
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

    private void candidatures(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/CandidatureUser.fxml"
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

    @FXML
    private void profile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/users/Profile.fxml"
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

    @FXML
    private void showQuizz(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/simpalha/admin/quizz/FXMLQuizzTable.fxml"
                    )
            );

            Parent root = loader.load();

            FXMLAdminQuizzTableController tableController = loader.getController();

            tableController.initializeUserId(usr.getUserid());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(root)
            );
            stage.setTitle("Quiz Section");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
