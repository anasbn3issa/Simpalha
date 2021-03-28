/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Ressources;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceRessources;

//mailing
import javax.mail.PasswordAuthentication;
import javax.mail.Session;



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
        description.setText(R1.getDescription());
        
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
    @FXML
    private void ZoomIn(ActionEvent event) {
    }

    @FXML
    private void ZoomOut(ActionEvent event) {
    }

    @FXML
    private void Description(ActionEvent event) {
    new animatefx.animation.Shake(description).play();
    }

    @FXML
    private void GeneratePdf(ActionEvent event) {
        
        try {
            //file generated's name
            String dir = System.getProperty("user.dir");//get project source path
            File name = new File(dir + "\\ressources\\PDFS\\" + R1.getTitle()+".pdf");//add the full path /ressources + file name
            
            //Create a document object
            Document document=new Document();
            
            PdfWriter.getInstance(document, new FileOutputStream(name));
        
            //Opening the document
            document.open();
            
            //load content
            Paragraph title = new Paragraph(R1.getTitle());
            Paragraph description = new Paragraph(R1.getDescription());
            Paragraph header1 = new Paragraph("COPYRIGHT : SIMPALHA");
            Paragraph titleheader = new Paragraph("Resource's title:");
            Paragraph descriptionheader = new Paragraph("Resource's Description:");
            Paragraph imageheader = new Paragraph("Resource's image:");

            //add content
           document.addTitle("Simpalha's resource");
          
            document.add(header1);
            document.add(titleheader);
            document.add(title);      
            document.add(descriptionheader);
            document.add(description);
            document.add(imageheader);
            

         //  document.add(Image.getInstance(dir + "\\ressources\\" + R1.getTitle()));
           
 
            
            
            
            //close content
            document.close();
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Generating a pdf..");
            alert.setHeaderText("Success!! ");
            alert.setContentText("PDF has been generated. Would you like to send it via an email?");
            alert.setContentText("Type OK to confirm, CANCEL to cancel.");
            Optional<ButtonType> result = alert.showAndWait();
            alert.showAndWait();
            
                    if (result.get() == ButtonType.OK) // si confirmé,
                    {
                        TextInputDialog dialog = new TextInputDialog("email @");
                        dialog.setTitle("Sending an email to..");
                        dialog.setHeaderText("You chose to send an email containing this resource, ");
                        dialog.setContentText("Please enter destination's @:");

                           // Traditional way to get the response value.
                        Optional<String> result2 = dialog.showAndWait();
                            if (result2.isPresent()){
                                //si @ entrée:
                                	//authentication info
		final String username = "simaplha2021@gmail.com";
		final String password = "simpalha1234";
		String fromEmail = "simaplha2021@gmail.com";
		String toEmail = result2.get();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
                               System.out.println("Your name: " + result2.get());
                               }
                    }
                    else
                    {}
            
               
      
//            
//            
//        //Dropbox API
//        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Resources_Saved").build();
//        
//        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
//        FullAccount account = client.users().getCurrentAccount();
//        
//        System.out.println(account.getName().getDisplayName());
//        
//        try (InputStream in = new FileInputStream(name)) {
//        FileMetadata metadata = client.files().uploadBuilder("/"+name).uploadAndFinish(in);
//        }
//            DbxDownloader<FileMetadata> downloader = client.files().download("/"+name);
//            downloader.download(out);
//            out.close();
        } 
        
        catch (Exception ex) {
            System.out.println(ex);
        }
        
 
    
    }}
