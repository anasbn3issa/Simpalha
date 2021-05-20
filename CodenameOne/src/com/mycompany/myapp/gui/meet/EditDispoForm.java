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
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Disponibilite;
import com.mycompany.myapp.gui.SideMenu;
import com.mycompany.myapp.services.DispoService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDispoForm extends SideMenu {

    public EditDispoForm(Resources res, Disponibilite disp) {
        try {
            setTitle("Edit Disponibilie");
            setupSideMenu(res);
            setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListHelperDisponibiliteForm(res).showBack());

            Container timecnt1 = new Container(BoxLayout.x());
            Label timeIcon = new Label("", "TextField");
            timeIcon.getAllStyles().setMargin(RIGHT, 0);
            FontImage.setMaterialIcon(timeIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);

            Picker startPicker = new Picker();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date startdate = format.parse(disp.getDatedeb());

            String pattern = "MM/dd/yy hh:mm aa";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String startstr = simpleDateFormat.format(startdate);

            startPicker.setText(startstr);
            startPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);

            Picker finishPicker = new Picker();
            Date finishdate = format.parse(disp.getDateFin());
            String endstr = simpleDateFormat.format(finishdate);
            finishPicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
            finishPicker.setText(endstr);
            Container timecnt2 = new Container(BoxLayout.x());
            Label timeIcon2 = new Label("", "TextField");
            timeIcon2.getAllStyles().setMargin(RIGHT, 0);
            FontImage.setMaterialIcon(timeIcon2, FontImage.MATERIAL_PERSON_OUTLINE, 3);

            Button confirmButton = new Button("Save");
            confirmButton.setUIID("SaveButton");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    try {
                        System.out.println(startPicker.getText());
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mm aa");
                        Date datestart = format.parse(startPicker.getText());
                        Date datefinish = format.parse(finishPicker.getText());

                        String pattern = "yyyy-MM-dd HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        disp.setDatedeb(simpleDateFormat.format(datestart));
                        disp.setDateFin(simpleDateFormat.format(datefinish));
                        if (DispoService.getInstance().editDispo(disp)) {
                            new ListHelperDisponibiliteForm(res).showBack();
                            ToastBar.showInfoMessage("Meet added.");
                        } else {
                            ToastBar.showErrorMessage("Add error!");
                        }

                    } catch (ParseException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
            timecnt1.add(timeIcon).add(new Label("Start : "));
            timecnt2.add(timeIcon2).add(new Label("Finish : "));

            Container by = BoxLayout.encloseY(BorderLayout.center(startPicker).
                    add(BorderLayout.WEST, timecnt1),
                    BorderLayout.center(finishPicker).
                            add(BorderLayout.WEST, timecnt2),
                    confirmButton
            );
            add(BorderLayout.CENTER, by);
            by.setScrollableY(true);
            by.setScrollVisible(false);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
