/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.mycompany.myapp.gui.*;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableModel;
import com.mycompany.myapp.entities.Meet;
import com.mycompany.myapp.services.MeetTask;
import com.mycompany.myapp.services.ServiceTask;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ListMeetsForm extends Form {

    public ListMeetsForm(Form previous) {
        setTitle("List meets");
        setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));


        String[] headers = new String[]{"Student", "Helper", "Feedback", "Specialite", "Time","Etat","Action"};
        
        ArrayList<Meet> meets = MeetTask.getInstance().getAllMeets();
        System.out.println(meets);
        
        Object[][] rows = new Object[meets.size()][];

        for (int iter = 0; iter < rows.length; iter++) {
            rows[iter] = new Object[]{
                meets.get(iter).getId_student(), meets.get(iter).getId_helper(), meets.get(iter).getFeedback_id(),
                meets.get(iter).getSpecialite(),meets.get(iter).getTime(),meets.get(iter).getEtat(),
            };
        }

        MeetTableModel meetTableModel = new MeetTableModel(headers, rows);
        Table dataTable = new Table(meetTableModel) {

            @Override
            protected Component createCell(Object value, final int row, final int column, boolean editable) {
                if (row != -1 && column == headers.length - 1) {
                    Button cell = new Button("test");
                    cell.setUIID(getUIID() + "Cell");
                    cell.getUnselectedStyle().setAlignment(Component.CENTER);
                    cell.getSelectedStyle().setAlignment(Component.CENTER);
                    cell.setFlatten(true);
                    cell.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {

                        }
                    });
                    return cell;
                }
                return super.createCell(value, row, column, editable); //To change body of generated methods, choose Tools | Templates.
            }
        };
        add(BorderLayout.NORTH, dataTable);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
