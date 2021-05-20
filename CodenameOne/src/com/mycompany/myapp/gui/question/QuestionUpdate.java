/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.question;

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
import com.mycompany.myapp.entities.Question;
import com.mycompany.myapp.services.ServiceQuestion;

/**
 *
 * @author Parsath
 */
public class QuestionUpdate extends Form {
    
    private Button confirm;
    private Container formContainer;
    private TextField tfQuestion;
    private Label lQuestionr;
    
    public QuestionUpdate(int quizId, Resources theme, Form previous, Form beforePrevious, Question q){
        setLayout(new BorderLayout());
        setTitle("Update Question");
        
        formContainer = new Container(BoxLayout.y());
        confirm = new Button("Confirm Question");
        tfQuestion = new TextField(q.getQuestion(),"");
        
        confirm.addActionListener(e->{
            
            
                if ((tfQuestion.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    q.setQuestion(tfQuestion.getText());
                    // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                    if( ServiceQuestion.getInstance().updateQuestion(q))
                        Dialog.show("Success","Question Updated",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                }
                
            new QuestionList(quizId,theme,beforePrevious).show();
        });
        
        
        formContainer.add("Question").add(tfQuestion);
        add(BorderLayout.CENTER, formContainer).add(BorderLayout.SOUTH,confirm);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
    }
    
}
