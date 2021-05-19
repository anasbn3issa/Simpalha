/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.quiz;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Quiz;
import com.mycompany.myapp.services.ServiceQuiz;

/**
 *
 * @author Parsath
 */
public class Update extends Form {
    
    private Button confirm;
    private Container formContainer;
    private TextField tfquizTitle, tfquizSubject;
    private Label lQuizTitle, lQuizSubject;
    
    public Update(Resources theme, Form previous, Quiz q){
        setLayout(new BorderLayout());
        setTitle("Update Quiz");
        
        formContainer = new Container(BoxLayout.y());
        confirm = new Button("Confirm Quiz");
        tfquizTitle = new TextField(q.getTitle(),"");
        tfquizSubject = new TextField(q.getSubject(),"");
        
        confirm.addActionListener(e->{
            
            
                if ((tfquizTitle.getText().length()==0)||(tfquizSubject.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    q.setTitle(tfquizTitle.getText());
                    q.setSubject(tfquizSubject.getText());
                    // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                    if( ServiceQuiz.getInstance().updateQuiz(q))
                        Dialog.show("Success","Quiz Updated",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                }
            new List(theme).show();
        });
        
        
        formContainer.add("Quiz Title").add(tfquizTitle).add("Quiz Subject").add(tfquizSubject);
        add(BorderLayout.CENTER, formContainer).add(BorderLayout.SOUTH,confirm);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
    }
}
