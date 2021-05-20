/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.gui.HomeForm;
import com.mycompany.myapp.gui.SideMenu;
import com.mycompany.myapp.services.AuthService;
import com.mycompany.myapp.utils.Session;

/**
 *
 * @author win10
 */
public class EditProfile extends SideMenu {

    public EditProfile(Form previous, Resources res) {
        setTitle("Edit your profile");
        setLayout(BoxLayout.y());

        //  resume.getUser_id() != Session.ConnectedUser.getId()
        TextField pseudo = new TextField(Session.ConnectedUser.getPseudo(), "Ex: Arij01");
        TextField email = new TextField(Session.ConnectedUser.getEmail(), "Ex: test@test.com");
        TextField password = new TextField("", "Ex: ADGDJK");

        Button EditButton = new Button("Edit Profile");
       
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    User usr = new User(password.getText(), pseudo.getText(), email.getText());
                    usr.setId(Session.ConnectedUser.getId());
                    if (AuthService.getInstance().EditProfile(usr)) {
                        Dialog.show("Success", "Updated Successfully !", new Command("OK"));

                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }
            }
        });

        addAll(pseudo, email,password, EditButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                 e -> new HomeForm(res).show());
    }
}
