/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.quiz;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Parsath
 */
public class Home extends Form {
    
    private Form current;
    private Container c;
    private Button list;
    
    public Home(Resources theme, Form previous){
        current = this;
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        setLayout(new BorderLayout());
        setTitle("Whatevs'");
        
//        c = new Container(new FlowLayout(CENTER));
        list = new Button("List Quizes");
        
//        c.add(list);
        
        add(BorderLayout.CENTER, list);
        
        list.addActionListener(e->{
            new List(theme).show();
        });
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
        
    }
    
}
