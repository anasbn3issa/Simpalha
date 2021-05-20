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
import com.mycompany.myapp.entities.Feedback;
import com.mycompany.myapp.entities.Meet;
import com.mycompany.myapp.utils.Session;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class FeedbackService {

    

    public static FeedbackService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private FeedbackService() {
        req = new ConnectionRequest();
    }

    public static FeedbackService getInstance() {
        if (instance == null) {
            instance = new FeedbackService();
        }
        return instance;
    }

    public boolean addFeedback(Feedback f) {
        String url = Statics.BASE_URL + "feedback/new/" + f.getMeetId(); //création de l'URL
        req.setUrl(url);
        req.addArgument("description", f.getFeedback());
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
