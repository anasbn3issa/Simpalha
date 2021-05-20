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
import com.mycompany.myapp.entities.Meet;
import java.util.ArrayList;

import com.mycompany.myapp.entities.Post;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author anaso
 */
public class ServicePost {

    public ArrayList<Post> posts;
    public static ServicePost instance = null;
    private ConnectionRequest req;
    public boolean resultOK;

    private ServicePost() {
        req = new ConnectionRequest();
    }

    public static ServicePost getInstance() {
        if (instance == null) {
            instance = new ServicePost();
        }
        return instance;
    }

    public boolean addPost(Post m) {

        String url = Statics.BASE_URL + "post/new/" + m.getProblem() + "/" + m.getModule(); //création de l'URL
        req.setPost(true);

        // req.setRequestBody("{'problem':'"+m.getProblem()+"','module':'"+m.getModule()+"'}");
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(req.getResponseData());
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Post> parsePosts(String jsonText) {
        try {
            posts = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            Map<String, Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) listdata.get("root");

            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données

                Post m = new Post();
                System.out.println(obj);
                if ((obj.get("owner")) != null) {
                    m.setOwnerUserName(((Map<String, Object>) obj.get("owner")).get("pseudo").toString());
                } else {
                    m.setOwnerUserName("daly_nahdi");
                }
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int) id);
                m.setStatus("OPEN");
                m.setProblem(obj.get("problem").toString());
                m.setModule(obj.get("module").toString());
                posts.add(m);
            }

        } catch (IOException ex) {

        }
        return posts;
    }

    public ArrayList<Post> getAllPosts() {
        String url = Statics.BASE_URL + "post/getposts";
        req.setPost(false);
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                posts = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }
    
    public ArrayList<Post> getAllPostsSorted() {
        String url = Statics.BASE_URL + "post/getpostssorted";
        req.setPost(false);
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                posts = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }

    public boolean deletePost(Post p) {
        String url = Statics.BASE_URL + "post/" + "delete/" + p.getId(); //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(req.getResponseCode());
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean updatePost(Post p) {
        String url = Statics.BASE_URL + "post/" + p.getId() + "/edit/" + p.getProblem(); //création de l'URL
        req.setPost(true);

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

}
