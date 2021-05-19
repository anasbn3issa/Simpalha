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
import com.mycompany.myapp.services.DispoService;
import com.mycompany.myapp.services.MeetService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditMeetForm extends Form {

    ArrayList<String> disps;
    Map<Integer, String> dispMap;
    Map<String, Integer> revdispMap;

    public EditMeetForm(Resources res, Meet meet) {
        getDisps(meet.getId_helper());
        setTitle("Edit meet");
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
        Label userhelper = new Label(meet.getUnameHlp());

        Label spcIcon = new Label("", "TextField");
        spcIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(spcIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        Label lnspc = new Label(meet.getSpecialite());

        Button confirmButton = new Button("Save");
        confirmButton.setUIID("SaveButton");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                meet.setTime(String.valueOf(revdispMap.get(combo.getSelectedItem())));
                if (MeetService.getInstance().editMeet(meet))
                {
                    new ListMeetsForm(res).showBack();
                    ToastBar.showInfoMessage("Meet updated.");
                }
                else
                    ToastBar.showErrorMessage("Update error!");
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
