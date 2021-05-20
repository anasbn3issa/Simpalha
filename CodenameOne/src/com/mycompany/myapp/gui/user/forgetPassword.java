/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.AuthService;
import com.mycompany.myapp.utils.Session;
import java.util.Random;


/**
 *
 * @author win10
 */
public class forgetPassword extends Form {
    Form current;
    private static User User;
    public static String Codex;
    private Form f;
    private TextField username;
    private TextField password;
    private Button connecter;
    private Button SignUp;
    private Button Forget_Password;
    public static final String ACCOUNT_SID = "ACf06d8ef396999213b853a5dd057af9e2";
    public static final String AUTH_TOKEN = "654c38e49aa6b0878c74d472b086e277";
    public static Resources theme;

    public forgetPassword(Resources res) {
ImageViewer img = new ImageViewer(res.getImage("Simpalhalogo.png").scaled(600, 300));
       // add(new Label(res.getImage("Simpalhalogo.png"), "LogoLabel"));

        TextField email = new TextField("", "Email", 20, TextField.ANY);
        email.setSingleLineTextArea(false);

        Button signIn = new Button("Send");
        
        Button btnannuler = new Button("Back");
        btnannuler.addActionListener(a -> {
            new Login(res).show();

        });

        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                
                signIn,btnannuler,
                FlowLayout.encloseCenter()
        );
        add(content);

        signIn.addActionListener(e -> {
            if (email.getText().isEmpty()) {
                Dialog.show("Error", "Please enter your email", "Ok", null);

            } else {
            //    Session.setForgetPassMail(email.getText());
              AuthService ser = AuthService.getInstance();
              ser.sendEmail(email.getText());
         //       System.out.println(email.getText());


            }
        });
        show();

    }


    public void code(Resources res) {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        Session.setSaltToken(saltStr);
        f = new Form();

        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        add(new Label(res.getImage("Logo.png"), "LogoLabel"));
        add(new Label("A message has been sent to your phone number"));
        TextField email1 = new TextField("", "Code", 20, TextField.ANY);
        email1.setSingleLineTextArea(false);
        Button signIn1 = new Button("Send !");
        Container content1 = BoxLayout.encloseY(
                new FloatingHint(email1),
             
                signIn1,
                FlowLayout.encloseCenter()
        );
        add(content1);
        show();
    }}