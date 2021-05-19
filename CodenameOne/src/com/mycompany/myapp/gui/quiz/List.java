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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Quiz;
import com.mycompany.myapp.gui.HomeForm;
import com.mycompany.myapp.gui.question.QuestionList;
import com.mycompany.myapp.services.ServiceQuestion;
import com.mycompany.myapp.services.ServiceQuiz;
import java.util.ArrayList;

/**
 *
 * @author Parsath
 */
public class List extends Form{
    
    private Form current;
    private Container quizesContainer;
    private Container addContainer;
    private Button addButton;
    
    public List(Resources theme){
        
        current = this;
        addButton = new Button("Add Quiz");
        quizesContainer = new Container(BoxLayout.y());
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new HomeForm(theme).showBack());
        
        setLayout(new BorderLayout());
        setTitle("Quiz List");
        
        
        ArrayList<Quiz> quizes = ServiceQuiz.getInstance().getAllQuizes();
        
        for(Quiz q : quizes){
            Container a = new Container(new FlowLayout(CENTER));
            Container b = new Container(BoxLayout.y());
            Container dmContainer = new Container(new FlowLayout(CENTER));
            
            Label lTitle = new Label(q.getTitle());
            Label lSubject = new Label(q.getSubject());
            Label lId = new Label("Quiz Id : "+String.valueOf(q.getId()));
            Button bDelete = new Button("Delete");
            Button bModify = new Button("Modify");
            Button bListQuestion = new Button("Show");
            
            dmContainer.add(bDelete).add(bModify).add(bListQuestion);
            b.add(lTitle).add(lSubject).add(lId).add(dmContainer);
            a.add(b);
            
            bListQuestion.addActionListener(e->{
                // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                new QuestionList(q.getId(),theme,current).show();
            });
            
            bDelete.addActionListener(e->{
                // ONCE I GET SESSION WORKING, I NEED TO CHANGE 11 with getUserId()
                if( ServiceQuiz.getInstance().deleteQuiz(q))
                    Dialog.show("Success","Quiz Deleted",new Command("OK"));
                else
                    Dialog.show("ERROR", "Server Error", new Command("OK"));
                
                new List(theme).show();
            });
            
            bModify.addActionListener(e->{
                new Update(theme,current,q).show();
            });
            
            
            System.out.println("Quizz NAMBAR WAHED : \n"+q);
            quizesContainer.add(a);
        }
        
        add(BorderLayout.NORTH, quizesContainer).add(BorderLayout.SOUTH, addButton);
        
        addButton.addActionListener(e->{
            new Add(theme,current).show(); 
        });
        
        
        setScrollable(true);
    }
    
}
