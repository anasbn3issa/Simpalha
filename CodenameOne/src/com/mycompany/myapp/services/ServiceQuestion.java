/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Question;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Parsath
 */
public class ServiceQuestion {

    public ArrayList<Question> questions;
    
    public static ServiceQuestion instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private ServiceQuestion() {
         req = new ConnectionRequest();
    }

    public static ServiceQuestion getInstance() {
        if (instance == null) {
            instance = new ServiceQuestion();
        }
        return instance;
    }

    public boolean addQuestion (int quizId,Question q) {
        String url = Statics.BASE_URL+"quiz/"+quizId+"/questions/create/"+q.getQuestion(); //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean updateQuestion(Question q) {
        String url = Statics.BASE_URL+"quiz/"+q.getQuizId()+"/questions/"+q.getId()+"/edit/"+q.getQuestion()+"/"; //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean deleteQuestion(Question q){
        String url = Statics.BASE_URL+"quiz/"+q.getQuizId()+"/questions/"+q.getId()+"/delete"; //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Question> parseQuestions(String jsonText){
        try {
            questions=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> quizesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            System.out.println("Questions List : \n" + quizesListJson);
            System.out.println("Questions List Root : \n" + quizesListJson.get("root"));
            System.out.println("Questions List Data : \n" + quizesListJson.get("data"));
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(quizesListJson.get("questions").toString().toCharArray()));
            int quizId = (int)Float.parseFloat(quizesListJson.get("quizzId").toString());
            
            System.out.println("THIS IS QUIZID \n" + quizId );
            
            System.out.println("Questions List Data 2 : \n" + listdata.get("root"));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) listdata.get("root");
            
            System.out.println("This is the MAP \n" + list);
            
            for(Map<String,Object> obj : list){
                Question q = new Question(
                    (int)Float.parseFloat(obj.get("id").toString()),
                    (int)Float.parseFloat(obj.get("rightAnswer").toString()),
                    quizId,
                    obj.get("question").toString()
                );
                
                System.out.println(q);
                questions.add(q);
            }
            
            
        } catch (IOException ex) {
            
        }
        return questions;
    }
    
    public ArrayList<Question> getAllQuestions(int quizId){
//        ONCE SESSION WORKS, CHANGE 11 WITH getUserId()
        String url = Statics.BASE_URL+"quiz/"+quizId+"/questions";
        
        System.out.println("This is the URL: \n" + url);
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("\nThis is the Request: \n "+ req);
                System.out.println("Chay ghrib : \n"+String.valueOf(req.getResponseData())+"\n Toufa lénna");
                questions = parseQuestions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return questions;
    }
    
}
