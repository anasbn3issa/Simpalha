/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

/**
 *
 * @author win10
 */
import com.mycompany.myapp.entities.User;

import com.mycompany.myapp.services.AuthService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;


import java.io.IOException;


public class Register extends Form {

    Form current;
    private Image img;
    private static User User;
    private String imgPath;

    public Register(Form previous, Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Sign Up  ", "WelcomeWhite")
        );

        getTitleArea().setUIID("Container");

        TextField firstName = new TextField(null, "Enter your first name", 20, TextField.ANY);
        TextField lastName = new TextField(null, "Enter your last name", 20, TextField.ANY);
        TextField email = new TextField(null, "Enter your email", 20, TextField.ANY);
        TextField password = new TextField(null, "Enter your password", 20, TextField.PASSWORD);
        TextField phone = new TextField(null, "Enter your phone number ", 20, TextField.NUMERIC);
        TextField address = new TextField(null, "Enter your address", 20, TextField.ANY);
        TextField pseudo = new TextField(null, "Enter your pseudo ", 20, TextField.NUMERIC);
        Picker date = new Picker();
        firstName.getAllStyles().setBgColor(1);
        firstName.getAllStyles().setMargin(LEFT, 0);
        lastName.getAllStyles().setMargin(LEFT, 0);
        email.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        phone.getAllStyles().setMargin(LEFT, 0);
        address.getAllStyles().setMargin(LEFT, 0);
        pseudo.getAllStyles().setMargin(LEFT, 0);
        date.getAllStyles().setMargin(LEFT, 0);
        Image profilePic = res.getImage("20187112601561032514-512.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Button photobutton = new Button("", profilePic);
        photobutton.getIcon();
        photobutton.setUIID("SignUpPhotoButton");

        photobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    imgPath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
                    img = Image.createImage(imgPath);
                } catch (IOException ignored) {
                }
            }

        });
        
        Button RegisterButton = new Button("Register");
        RegisterButton.setUIID("LoginButton");
        Button login = new Button("Sign In");
        login.setUIID("CreateNewAccountButton");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Login(res).show();
            }
        });
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
       /* Container logoC = BoxLayout.encloseY(
                new ImageViewer(res.getImage("Simpalhalogo.png").scaled(700, 300))
        );*/
        RegisterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                AuthService service = new AuthService();
                service.SingUp(firstName.getText(),lastName.getText(),date.getDate(),Integer.parseInt(phone.getText()),address.getText(),pseudo.getText(),password.getText(),email.getText());
                new HomeForm(res).show();
            }

        });
      // add(BorderLayout.OVERLAY, logoC);
        Container by = BoxLayout.encloseY(
                spaceLabel,
                welcome,
                photobutton,
                BorderLayout.center(firstName),
                BorderLayout.center(lastName),
                BorderLayout.center(email),
                BorderLayout.center(phone),
                BorderLayout.center(password),
                BorderLayout.center(address),
                BorderLayout.center(pseudo),
                BorderLayout.center(date),
                RegisterButton,
                login
        );
        add(BorderLayout.CENTER, by);
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

}