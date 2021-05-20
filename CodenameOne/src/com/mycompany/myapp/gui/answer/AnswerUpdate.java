/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.answer;

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
import com.mycompany.myapp.entities.Answer;
import com.mycompany.myapp.gui.answer.AnswerList;
import com.mycompany.myapp.services.ServiceAnswer;

/**
 *
 * @author Parsath
 */
public class AnswerUpdate extends Form {
    
    private Button confirm;
    private Container formContainer;
    private TextField tfSuggestion;
    private Label lSuggestion;
    
    public AnswerUpdate(int quizId,int questionId, Resources theme, Form previous, Form beforePrevious, Answer q){
        setLayout(new BorderLayout());
        setTitle("Update Answer");
        
        formContainer = new Container(BoxLayout.y());
        confirm = new Button("Confirm Answer");
        tfSuggestion = new TextField(q.getSuggestion(),"");
        
        confirm.addActionListener(e->{
            
            
                if ((tfSuggestion.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    q.setSuggestion(tfSuggestion.getText());
                    // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                    if( ServiceAnswer.getInstance().updateAnswer(quizId,q))
                        Dialog.show("Success","Answer Updated",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                }
                
            new AnswerList(quizId,questionId,theme,beforePrevious).show();
        });
        
        
        formContainer.add("Suggestion").add(tfSuggestion);
        add(BorderLayout.CENTER, formContainer).add(BorderLayout.SOUTH,confirm);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
    }
    
}
