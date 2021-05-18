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
import com.mycompany.myapp.entities.Quiz;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Parsath
 */
public class ServiceQuiz {

    public ArrayList<Quiz> quizes;
    
    public static ServiceQuiz instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private ServiceQuiz() {
         req = new ConnectionRequest();
    }

    public static ServiceQuiz getInstance() {
        if (instance == null) {
            instance = new ServiceQuiz();
        }
        return instance;
    }

    public boolean addQuiz(Quiz q) {
        String url = Statics.BASE_URL+"quiz/create"+"/"+q.getUserId()+"/"+q.getTitle()+"/"+q.getSubject(); //création de l'URL
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

    public boolean updateQuiz(Quiz q) {
        String url = Statics.BASE_URL+"quiz/"+q.getId()+"/edit/"+q.getTitle()+"/"+q.getSubject(); //création de l'URL
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
    
    public boolean deleteQuiz(Quiz q){
        String url = Statics.BASE_URL+"quiz/"+q.getId()+"/delete"; //création de l'URL
        System.out.println("Ahna baad l url");
        req.setUrl(url);
        System.out.println("Ahna baad l reqseturl");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("lenna riquét sahbé \n"+req);
                System.out.println(req.getResponseCode());
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Quiz> parseQuizes(String jsonText){
        try {
            quizes=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> quizesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            System.out.println("Quizes List : \n" + quizesListJson);
            System.out.println("Quizes List Root : \n" + quizesListJson.get("root"));
            System.out.println("Quizes List Data : \n" + quizesListJson.get("data"));
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(quizesListJson.get("quizes").toString().toCharArray()));
            
            System.out.println("Quizes List Data 2 : \n" + listdata.get("root"));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>) listdata.get("root");
            
            System.out.println("This is the MAP \n" + list);
            
            for(Map<String,Object> obj : list){
                Quiz q = new Quiz(
                    (int)Float.parseFloat(obj.get("id").toString()),
//                        Once session getUserId()
                    11,
                    obj.get("title").toString(),
                    obj.get("subject").toString()
                );
                
                System.out.println(q);
                quizes.add(q);
            }
            
            
        } catch (IOException ex) {
            
        }
        return quizes;
    }
    
    public ArrayList<Quiz> getAllQuizes(){
//        ONCE SESSION WORKS, CHANGE 11 WITH getUserId()
        String url = Statics.BASE_URL+"quiz/list/11";
        
        System.out.println("This is the URL: \n" + url);
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("\nThis is the Request: \n "+ req);
                System.out.println("Chay ghrib : \n"+String.valueOf(req.getResponseData())+"\n Toufa lénna");
                quizes = parseQuizes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return quizes;
    }
}
