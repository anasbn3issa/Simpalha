/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import com.convertapi.Config;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.scenario.effect.ImageData;
import entities.Ressources;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//alerts
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

//fxml
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceRessources;

//mailing
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author α Ω
 */
public class ImageViewFXMLController implements Initializable {

    int idR;
    private static final String ACCESS_TOKEN = "sl.At2Pzu1b_gEpiGh839MAXdCqliUYI00ciqI8xd_CiKnUirmvaQrn7HZd9tnKap8Uo6TB3UI-2dUEprlKU963UdgKe2RSu5gGlWVkVsLroHalUyQCA_Y4znif-fMod4p-k1kZ8Pw";

    public Ressources R1;
    private ServiceRessources sr;
    @FXML
    private TextField tfsearch;

    @FXML
    private ImageView resource;
    @FXML
    private Text title;
    @FXML
    private Text description;

//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            String dir = System.getProperty("user.dir");//get project source path
            File dest = new File(dir + "\\ressources\\" + R1.getPath());//add the full path /ressources + file name
            System.out.println(dest.getAbsolutePath());

            title.setText(R1.getTitle());

            try {
                resource.setImage(new javafx.scene.image.Image(dest.toURI().toURL().toString()));
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImageViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    public void initRess(int id) {
        idR = id;
    }

    @FXML //retour à l'affichage des ressources
    private void GoBack(ActionEvent event) {
        try {
            Parent page2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UpdateRESOURCESFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    
//    private BufferedImage ZoomImage (int w,int h,Image img)
//    {
//        BufferedImage buf = new BufferedImage (w, h, BufferedImage.TYPE_INT_RGB);
//        Graphics2D grf= buf.createGraphics();
//        grf.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        grf.drawImage(img, 0, 0, w, h,null);
//        grf.dispose();
//        return buf;
//    }
//    @FXML
//    private void ZoomIn(ActionEvent event) {
//    }
//
//    @FXML
//    private void ZoomOut(ActionEvent event) {
//    }
    @FXML
    private void Description(ActionEvent event) {

        description.setText(R1.getDescription());
        new animatefx.animation.Shake(description).play();
    }

    @FXML
    private void GeneratePdf(ActionEvent event) {

    
            GeneratingPDF GPDF= new GeneratingPDF();
            File file= GPDF.GetGeneratePdf(R1);
            
            DropBoxApi Drpbx = new DropBoxApi();
        try {
            Drpbx.uploadDropBox(file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(ImageViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}