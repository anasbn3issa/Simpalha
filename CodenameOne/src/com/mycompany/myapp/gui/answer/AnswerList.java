/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.answer;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Answer;
import com.mycompany.myapp.gui.question.QuestionList;
import com.mycompany.myapp.services.ServiceAnswer;
import java.util.ArrayList;

/**
 *
 * @author Parsath
 */
public class AnswerList  extends Form {
    
    private Form current;
    private Container answersContainer;
    private Container addContainer;
    private Button addButton;
    
    public AnswerList(int quizId,int questionId,Resources theme,Form previous){
        
        current = this;
        addButton = new Button("Add Answer");
        answersContainer = new Container(BoxLayout.y());
        
        setLayout(new BorderLayout());
        setTitle("Answer List");
        
        
        ArrayList<Answer> answers = ServiceAnswer.getInstance().getAllAnswers(quizId,questionId);
        
        for(Answer q : answers){
            Container a = new Container(new FlowLayout(CENTER));
            Container b = new Container(BoxLayout.y());
            Container dmContainer = new Container(new FlowLayout(CENTER));
            
            Label lSuggestion = new Label(q.getSuggestion());
            Label lId = new Label("Answer Id : "+String.valueOf(q.getId()));
            
            Button bDelete = new Button("Delete");
            Button bModify = new Button("Modify");
            Button bRight = new Button("Set Right Answer");
            
            dmContainer.add(bDelete).add(bModify).add(bRight);
            b.add(lSuggestion).add(lId).add(dmContainer);
            a.add(b);
            
            bDelete.addActionListener(e->{
                    // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                    if( ServiceAnswer.getInstance().deleteAnswer(quizId,q))
                        Dialog.show("Success","Answer Deleted",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                    new AnswerList(quizId,questionId,theme,previous).show();
            });
            
            bModify.addActionListener(e->{
//                HERE
                new AnswerUpdate(quizId,questionId,theme,current,previous,q).show();
            });
            
            bRight.addActionListener(e->{
                    if( ServiceAnswer.getInstance().rightAnswer(quizId,questionId,q))
                        Dialog.show("Success","Set as Right Answer",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                    new QuestionList(quizId,theme,previous).show();
            });
            
            
            System.out.println("Answer NAMBAR WAHED : \n"+q);
            answersContainer.add(a);
        }
        
        add(BorderLayout.NORTH, answersContainer).add(BorderLayout.SOUTH, addButton);
        
        addButton.addActionListener(e->{
            new AnswerAdd(quizId,questionId,theme,current,previous).show(); 
        });
        
        
        setScrollable(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
        revalidate();
    }
    
}
