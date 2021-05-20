/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

/**
 *
 * @author win10
 */
import com.codename1.components.ToastBar;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Session;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Meet;
import com.mycompany.myapp.gui.SideMenu;
import com.mycompany.myapp.services.DispoService;
import com.mycompany.myapp.services.MeetService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMeetForm extends SideMenu {

    ArrayList<String> disps;
    Map<Integer, String> dispMap;
    Map<String, Integer> revdispMap;

    public AddMeetForm(Resources res, User helper) {
        getDisps(helper.getId());
        setupSideMenu(res);
        setTitle("New meet");
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListMeetsForm(res).showBack());

        ComboBox<String> combo = new ComboBox<>();
        for (String dis : disps) {
            combo.addItem(dis);
        }

        Container timecnt = new Container(BoxLayout.x());
        Container helpercnt = new Container(BoxLayout.x());
        Container spccnt = new Container(BoxLayout.x());
        Label timeIcon = new Label("", "TextField");
        timeIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(timeIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);

        Label userIcon = new Label("", "TextField");
        userIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(userIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        Label userhelper = new Label(helper.getPseudo());

        Label spcIcon = new Label("", "TextField");
        spcIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(spcIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        Label lnspc = new Label(helper.getSpecialty());

        Button confirmButton = new Button("Save");
        confirmButton.setUIID("SaveButton");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Meet m = new Meet();

                m.setTime(String.valueOf(revdispMap.get(combo.getSelectedItem())));
                m.setId_student(Session.ConnectedUser.getId());
                m.setId_helper(helper.getId());
                System.out.println(m);
                if (MeetService.getInstance().addMeet(m)){
                    new ListMeetsForm(res).showBack();
                    ToastBar.showInfoMessage("Meet added.");
                }
                else 
                    ToastBar.showErrorMessage("Add error!");
                

            }
        });
        helpercnt.add(userIcon).add(new Label("Helper : "));
        timecnt.add(timeIcon).add(new Label("Time : "));
        spccnt.add(spcIcon).add(new Label("Specialty : "));

        Container by = BoxLayout.encloseY(
                BorderLayout.center(userhelper).
                        add(BorderLayout.WEST, helpercnt),
                BorderLayout.center(lnspc).
                        add(BorderLayout.WEST, spccnt),
                BorderLayout.center(combo).
                        add(BorderLayout.NORTH, timecnt),
                confirmButton
        );
        add(BorderLayout.CENTER, by);
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

    private void getDisps(int id) {
        disps = new ArrayList<>();
        dispMap = new HashMap<>();
        revdispMap = new HashMap<>();
        dispMap = DispoService.getInstance().getAllDispsByHlpr(id);
        for (Map.Entry<Integer, String> pair : dispMap.entrySet()) {
            revdispMap.put(pair.getValue(), pair.getKey());
            disps.add(pair.getValue());
        }
    }
}
