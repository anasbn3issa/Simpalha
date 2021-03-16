/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.users;

import entities.Candidature;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import services.ServiceCandidature;

/**
 * FXML Controller class
 *
 * @author win10
 */
public class CandidatureUserController implements Initializable {

    @FXML
    private TextField tfemail;
    @FXML
    private ImageView tfimg;
     @FXML
    private TextField screen;
      @FXML
    private ComboBox comb;
    private File selectedFile;
    @FXML
    private ListView listview;
private ServiceCandidature srv;
ImageView imageView = new ImageView();
    @FXML
    private Button upload;
    @FXML
    private Button confirm;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            

    }    

        @FXML
    private void UploadFichier(ActionEvent event) {
   Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    FileChooser fc=new FileChooser();
    
    selectedFile =fc.showOpenDialog(app_stage);
   if(selectedFile != null)
    {
         Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        
        listview.getItems().add(selectedFile.getName());
        
    }else{
        System.out.println("\"File is not valid");
    }
    }

    @FXML
    private void Confirmer(ActionEvent event) {
        Candidature cdr ;
        
        
        Image image = imageView.getImage();

        if (image != null ) {
                            // write data to in-memory stream
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(bi, "jpg", bos);
                 Blob blob = new SerialBlob(bos.toByteArray());
                  cdr = new Candidature(tfemail.getText(), (String) comb.getValue(), blob);
                  srv.Create(cdr);
                  Candidature can =srv.findby(tfemail.getText());
                   Blob data = can.getFichier();
                    tfimg.setImage(new Image(data.getBinaryStream()));
            } catch (IOException ex) {
                Logger.getLogger(CandidatureUserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CandidatureUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

               

            
        }
    
    }

    @FXML
    private void selectspec(ActionEvent event) {
        
        String s=comb.getSelectionModel().getSelectedItem().toString();
        screen.setText(s);
    }
    @FXML
    private void goToViewPosts(MouseEvent event) {
    }

    @FXML
    private void showP2P(MouseEvent event) {
    }

  
    
}
