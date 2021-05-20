/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.question;

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
import com.mycompany.myapp.entities.Question;
import com.mycompany.myapp.gui.answer.AnswerList;
import com.mycompany.myapp.services.ServiceQuestion;
import java.util.ArrayList;

/**
 *
 * @author Parsath
 */
public class QuestionList extends Form {
    
    private Form current;
    private Container questionsContainer;
    private Container addContainer;
    private Button addButton;
    
    public QuestionList(int quizId,Resources theme,Form previous){
        
        current = this;
        addButton = new Button("Add Question");
        questionsContainer = new Container(BoxLayout.y());
        
        setLayout(new BorderLayout());
        setTitle("Question List");
        
        
        ArrayList<Question> questions = ServiceQuestion.getInstance().getAllQuestions(quizId);
        
        for(Question q : questions){
            Container a = new Container(new FlowLayout(CENTER));
            Container b = new Container(BoxLayout.y());
            Container dmContainer = new Container(new FlowLayout(CENTER));
            
            Label lTitle = new Label(q.getQuestion());
            Label lSubject = new Label("Right Answer : "+String.valueOf(q.getRightAnswer()));
            Label lId = new Label("Question Id : "+String.valueOf(q.getId()));
            
            Button bDelete = new Button("Delete");
            Button bModify = new Button("Modify");
            Button bListAnswer = new Button("Show");
            
            dmContainer.add(bDelete).add(bModify).add(bListAnswer);
            b.add(lTitle).add(lSubject).add(lId).add(dmContainer);
            a.add(b);
            
            bListAnswer.addActionListener(e->{
                new AnswerList(quizId,q.getId(),theme,current).show();
            });
            
            bDelete.addActionListener(e->{
                    if( ServiceQuestion.getInstance().deleteQuestion(q))
                        Dialog.show("Success","Question Deleted",new Command("OK"));
                    else
                        Dialog.show("ERROR", "Server Error", new Command("OK"));
                    
                    new QuestionList(quizId,theme,previous).show();
            });
            
            bModify.addActionListener(e->{
                new QuestionUpdate(quizId,theme,current,previous,q).show();
            });
            
            
            System.out.println("Quizz NAMBAR WAHED : \n"+q);
            questionsContainer.add(a);
        }
        
        add(BorderLayout.NORTH, questionsContainer).add(BorderLayout.SOUTH, addButton);
        
        addButton.addActionListener(e->{
            new QuestionAdd(quizId,theme,current,previous).show(); 
        });
        
        
        setScrollable(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                        , e->previous.showBack()); // Revenir vers l'interface précédente
        revalidate();
    }
    
}
