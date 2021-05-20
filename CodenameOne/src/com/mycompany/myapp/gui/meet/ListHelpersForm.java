/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.gui.SideMenu;
import com.mycompany.myapp.services.AuthService;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ListHelpersForm extends SideMenu {

    public ListHelpersForm(Resources res) {
        setTitle("Helpers List");
        setupSideMenu(res);
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListMeetsForm(res).showBack());
        setScrollableY(true);
        setScrollVisible(false);
        
        ArrayList<User> helpers = AuthService.getInstance().getHelpers();
        if (!helpers.isEmpty()) {
            Container cnty = new Container(BoxLayout.y());

            Container usernames = new Container(BoxLayout.y());
            Label lbusername = new Label("Username");
            usernames.add(lbusername);

            Container speclts = new Container(BoxLayout.y());
            Label lbspec = new Label("Specialty");
            speclts.add(lbspec);

            Container actions = new Container(BoxLayout.y());
            Label lbsel = new Label("Action");
            actions.add(lbsel);

            for (User usr : helpers) {
                //Label lbimg = new Label(res.getImage("user.png"));
                lbusername = new Label(usr.getPseudo());
                lbspec = new Label(usr.getSpecialty());
                Button btnsel = new Button("Select");
                btnsel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new AddMeetForm(res, usr).show();
                    }
                });
                usernames.add(lbusername);
                speclts.add(lbspec);
                actions.add(btnsel);

            }
            Container table = new Container(BoxLayout.x());
            table.addAll(usernames, speclts, actions);

            add(BorderLayout.CENTER, table);
        } else {
            add(BorderLayout.CENTER, new Label("No scheduled meetings"));
        }

    }
}
