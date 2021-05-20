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
import com.mycompany.myapp.entities.Answer;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Parsath
 */
public class ServiceAnswer {

    public ArrayList<Answer> answers;
    
    public static ServiceAnswer instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private ServiceAnswer() {
         req = new ConnectionRequest();
    }

    public static ServiceAnswer getInstance() {
        if (instance == null) {
            instance = new ServiceAnswer();
        }
        return instance;
    }

    public boolean rightAnswer (int quizId, int questionId,Answer a) {
        String url = Statics.BASE_URL+"quiz/"+quizId +"/questions/+"+questionId+"/answers/"+a.getId()+"/right"; //création de l'URL
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


    public boolean addAnswer (int quizId, int questionId ,Answer a) {
        String url = Statics.BASE_URL+"quiz/"+quizId +"/questions/+"+questionId+"/answers/create/"+a.getSuggestion(); //création de l'URL
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

    public boolean updateAnswer(int quizId,Answer a) {
        String url = Statics.BASE_URL+"quiz/"+quizId+"/questions/"+a.getQuestionId()+"/answers/"+a.getId()+"/edit/"+a.getSuggestion(); //création de l'URL
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
    
    public boolean deleteAnswer(int quizId,Answer a){
        String url = Statics.BASE_URL+"quiz/"+quizId+"/questions/"+a.getQuestionId()+"/answers/"+a.getId()+"/delete"; //création de l'URL
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

    public ArrayList<Answer> parseAnswers(String jsonText){
        try {
            answers=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> quizesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            System.out.println("Answers List : \n" + quizesListJson);
            System.out.println("Answers List Root : \n" + quizesListJson.get("root"));
            System.out.println("Answers List Data : \n" + quizesListJson.get("data"));
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(quizesListJson.get("answers").toString().toCharArray()));
            int quizId = (int)Float.parseFloat(quizesListJson.get("quizzId").toString());
            int questionId = (int)Float.parseFloat(quizesListJson.get("questionId").toString());
            
            System.out.println("THIS IS QUIZID \n" + quizId );
            System.out.println("THIS IS QUESTIONID \n" + questionId );
            
            System.out.println("Answers List Data 2 : \n" + listdata.get("root"));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) listdata.get("root");
            
            System.out.println("This is the MAP \n" + list);
            
            for(Map<String,Object> obj : list){
                Answer a = new Answer(
                    (int)Float.parseFloat(obj.get("id").toString()),
                    questionId,
                    obj.get("suggestion").toString()
                );
                
                System.out.println(a);
                answers.add(a);
            }
            
            
        } catch (IOException ex) {
            
        }
        return answers;
    }
    
    public ArrayList<Answer> getAllAnswers(int quizId, int questionId){
//        ONCE SESSION WORKS, CHANGE 11 WITH getUserId()
        String url = Statics.BASE_URL+"quiz/"+quizId +"/questions/+"+questionId+"/answers/";
        
        System.out.println("This is the URL: \n" + url);
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("\nThis is the Request: \n "+ req);
                System.out.println("Chay ghrib : \n"+String.valueOf(req.getResponseData())+"\n Toufa lénna");
                answers = parseAnswers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return answers;
    }
    
}
