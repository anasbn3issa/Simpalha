/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Disponibilite;
import entities.Users;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import services.ServiceDisponibilite;
import services.ServiceUsers;

import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class ProfileController implements Initializable {

    @FXML
    private PasswordField changepassword;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private TextField changeabout;
    @FXML
    private Label erreur;
    @FXML
    private Label erreur1;
      private ServiceUsers service;
      private ServiceDisponibilite serviceDisp;
    @FXML
    private TableView<Disponibilite> dispo;
    
    private int currentid;
    @FXML
    private Button addav;
 Users currentUser;
       ServiceUsers serviceUsers;
    private Text currentusr;
    private int userid;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       service = new ServiceUsers();
         serviceDisp = new ServiceDisponibilite();
         currentid = UserSession.getInstace(0).getUserid();
        /*  userid = UserSession.getInstace(0).getUserid();
        System.out.println(userid);*/
       
       /* serviceUsers= new ServiceUsers();
        currentUser=serviceUsers.findById(userid);
         currentusr.setText(currentUser.getUsername());*/
       /* serviceUsers= new ServiceUsers();
        currentUser=serviceUsers.findById(currentid);
         currentusr.setText(currentUser.getUsername());*/
         
        TableColumn<Disponibilite, String> idCol = new TableColumn<>("Date début");
        idCol.setCellValueFactory(new PropertyValueFactory<>("datedeb"));

        TableColumn<Disponibilite, String> idemailCol = new TableColumn<>("Date fin");
        idemailCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        TableColumn<Disponibilite, String> idNomCol = new TableColumn<>("Etat");
        idNomCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        

       
          
        

        TableColumn delCol = new TableColumn();
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Disponibilite, String>, TableCell<Disponibilite, String>> cellFactoryDelete
                = //
                (final TableColumn<Disponibilite, String> param) -> {
                    final TableCell<Disponibilite, String> cell = new TableCell<Disponibilite, String>() {

                final Button delete = new Button("Delete");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        delete.setOnAction(event -> {
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("confirmation dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to delete?");
                            Optional <ButtonType> action=alert.showAndWait();
                            if(action.get()==ButtonType.OK){
                            
                                Disponibilite disp = getTableView().getItems().get(getIndex());
                                System.out.println(disp);
                                serviceDisp.Delete(disp);
                      
 
                            dispo.getItems().clear();
                            dispo.getItems().addAll(serviceDisp.findAllById(currentid));
                            }

                        });

                        setGraphic(delete);
                        setText(null);
                    }

                }

            ;

                    };
            return cell;

                };

        delCol.setCellFactory(cellFactoryDelete);

        dispo.getColumns().add(idCol);
        dispo.getColumns().add(idemailCol);
        dispo.getColumns().add(idNomCol);

       // dispo.getColumns().add(modCol);
        dispo.getColumns().add(delCol);
        
        //List<Disponibilite> list = serviceDisp.findAllById(currentid);
        List<Disponibilite> list = serviceDisp.findAllById(currentid);
        list.forEach((disp) -> {
            dispo.getItems().add(disp);
        });
        System.out.println(list);
    
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
    private void showP2P(MouseEvent event) {Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/P2P/P2P.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }


   

    @FXML
    private void aboutUpdate(ActionEvent event) {
        ServiceUsers us = new ServiceUsers();
       if( !changeabout.getText().isEmpty())
        {
          us.updateAbout(changeabout.getText());
          erreur.setText("Description modifié !");
        } 
    }

    @FXML
    private void passwordUpdate(ActionEvent event) {
        
         ServiceUsers us = new ServiceUsers();
        UserSession cu= UserSession.getInstace(0);
        
        if( !changepassword.getText().isEmpty() && !confirmpassword.getText().isEmpty())
        {
            if (changepassword.getText().equals(confirmpassword.getText()))
            {
                Users u = us.findById(cu.userid);
                u.setPassword(changepassword.getText());
                us.updatePassword(u); 
                erreur.setText("mot de passe modifié !");  
            }
            else
            {
               erreur.setText("Erreur : Les deux mots de passe ne sont pas identiques !"); 
            }
          
        } 
        
    }

    void initData(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void apply(ActionEvent event) throws IOException{
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/users/CandidatureUser.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void Addavailability(ActionEvent event) {
       Parent loader;
             try {    loader = FXMLLoader.load(getClass().getResource("AddAvailability.fxml"));
                     //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }

    }

    @FXML
    private void goToposts(MouseEvent event) {
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
    private void quizz(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/quizz/FXMLQuizz.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void ressources(MouseEvent event) {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("simpalha/ressources/FXMLDocument.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void reclamtions(MouseEvent event) {
    }

    @FXML
    private void profile(MouseEvent event) {
         Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getClassLoader().getResource("Profile.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
    }

    @FXML
    private void logout(MouseEvent event) {
        UserSession.getInstace(currentid).cleanUserSession(); 
        
        try { Parent loader;
               loader = FXMLLoader.load(getClass().getResource("Login.fxml"));
                     //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) {
        }
       /* FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml")); 
        Parent root = loader.load();
        logout.getScene().setRoot(root);
        }
        catch(IOException e) 
        { }*/
    }
    
}
