/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

/**
 *
 * @author win10
 */
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Session;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.HomeForm;
import com.mycompany.myapp.services.AuthService;



public class Login extends Form {
    Form current;
    private static User User;
     private Button Forget_Password;
    public Login(Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Sign In  ", "WelcomeWhite")
               // new Label("Spir'IT", "WelcomeBlue")
        );
        getTitleArea().setUIID("Container");
        TextField login = new TextField(null, "Enter your username ! Exp: Admin_Admin", 20, TextField.ANY);
        TextField password = new TextField(null, "Enter tour password", 20, TextField.PASSWORD);
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
   AuthService ser = AuthService.getInstance();
   ser.login(login.getText(), password.getText());
                if (Session.ConnectedUser.getId()>0) {
                    Toolbar.setGlobalToolbar(true);
                    new HomeForm(res).show();
                } else {
                    Dialog.show("Error!", "Login ou mot de passe incorrect!", "Ok", null);
                }

            }
        });
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        Button forgetPassword = new Button("forget Password?");
        forgetPassword.setUIID("forgetPassword");
        forgetPassword.addActionListener(e -> {
            new forgetPassword(res).show();
        });
         new forgetPassword(res).show();
      // });


        createNewAccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Form mainForm = new Form();
                mainForm.setLayout(new BorderLayout());
                mainForm.getToolbar().setHidden(true);
                mainForm.getContentPane().removeAll();
                Register reg = new Register(current,res);
                mainForm.addComponent(BorderLayout.CENTER, reg);
                mainForm.revalidate();
                mainForm.show();
            }
        });

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        ImageViewer img = new ImageViewer(res.getImage("Simpalhalogo.png").scaled(600, 300));
Style loginStyle = img.getAllStyles();
         loginStyle.setMargin(Component.TOP, 70);
         Container logoC = BoxLayout.encloseY(img
        );
         
         add(BorderLayout.OVERLAY, logoC);
        Container by = BoxLayout.encloseY(
                welcome,

                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,forgetPassword,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
