/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.codename1.components.FloatingActionButton;
import com.mycompany.myapp.gui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.mycompany.myapp.entities.Disponibilite;
import com.mycompany.myapp.services.DispoService;
import com.mycompany.myapp.utils.Session;

/**
 *
 * @author bhk
 */
public class ListHelperDisponibiliteForm extends SideMenu {

    public ListHelperDisponibiliteForm(Resources res) {
        setTitle("Disponibilie");
        setupSideMenu(res);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e
                -> new HomeForm(res).showBack());
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        setScrollableY(true);
        setScrollableX(true);
        setScrollVisible(false);
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        //fab.addActionListener(e -> ToastBar.showErrorMessage("Not implemented yet..."));
        fab.addActionListener(e -> new AddDispoForm(res).show());

        fab.bindFabToContainer(getContentPane());

        ArrayList<Disponibilite> dispos = DispoService.getInstance().getListAllDispsByHlpr(Session.ConnectedUser.getId());

        if (!dispos.isEmpty()) {

            Container cnty = new Container(BoxLayout.y());

            Container start = new Container(BoxLayout.y());
            Label lbstarttimes = new Label("Start");
            start.add(lbstarttimes);

            Container finish = new Container(BoxLayout.y());
            Label lbfinishtimes = new Label("Finish");
            finish.add(lbfinishtimes);
            
            Container etat = new Container(BoxLayout.y());
            Label lbetat = new Label("Etat");
            etat.add(lbetat);

            Container actions = new Container(BoxLayout.y());
            Label lbsel = new Label("Action");
            actions.add(lbsel);

            for (Disponibilite disp : dispos) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                Date datestart = null;
                Date datefinish = null;
                try {
                    datestart = format.parse(disp.getDatedeb());
                    datefinish = format.parse(disp.getDateFin());
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }

                String pattern = "dd MMM yy HH:mm a";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String dstart = simpleDateFormat.format(datestart);
                String dfinish = simpleDateFormat.format(datefinish);

                Container cntbtns = new Container(BoxLayout.x());
                    Button btnedt = new Button("Edit");
                    btnedt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new EditDispoForm(res, disp).show();
                        }
                    });
                    if(disp.getEtat()==1)
                        btnedt.setEnabled(false);
                    cntbtns.add(btnedt);
                    if(disp.getEtat()==0)
                        etat.add(new Label("Free"));
                    else
                        etat.add(new Label("Reserved"));
                start.add(new Label(dstart));
                finish.add(new Label(dfinish));
                actions.add(cntbtns);
            }

            Container table = new Container(BoxLayout.x());
            table.addAll(etat, start, finish, actions);
            table.setScrollableX(true);
            add(BorderLayout.OVERLAY, table);
        } else {
            add(BorderLayout.CENTER, new Label("No scheduled meetings"));
        }

    }

}
