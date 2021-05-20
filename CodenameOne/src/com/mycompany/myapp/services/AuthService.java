/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

/**
 *
 * @author win10
 */
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.User;

import com.mycompany.myapp.utils.Session;
import com.codename1.io.*;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AuthService {
    
    public ArrayList<User> users;
    public static AuthService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    

    private AuthService() {
         req = new ConnectionRequest();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

      public void SingUp(String firstName , String lastName , Date dateOfBirth, int phone , String adresse , String professionalTitle , String password, String email ) {
        ConnectionRequest con=new ConnectionRequest();

       
        con.setUrl(Statics.BASE_URL+ "user/register" + "?firstName=" + firstName + "&lastName=" + lastName + "&dateOfBirth=" + dateOfBirth + "&phone=" + phone + "&adresse=" + adresse + "&professionalTitle=" + professionalTitle + "&password=" + password + "&email=" + email);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              if (con.getResponseCode() == 200) {
                Dialog.show("Success", "Added Successfully", "Ok", null);
            } else {
      

                    Dialog.show("Failed", "Existing User", "Ok", null);
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

   /* public void SingUp(String firstName, String lastName, Date dateOfBirth, int phone, String adresse, String professionalTitle, String password, String email) {

        

       

        MultipartRequest con = new MultipartRequest();
        con.setUrl(Statics.BASE_URL_ARIJ+ "api/register" + "?firstName=" + firstName + "&lastName=" + lastName + "&dateOfBirth=" + dateOfBirth + "&phone=" + phone + "&adresse=" + adresse + "&professionalTitle=" + professionalTitle + "&password=" + password + "&email=" + email);
        con.setPost(true);
        String filePath = FileSystemStorage.getInstance().getAppHomePath() ;
        String mime = "image/jpeg";
        String fichernom = System.currentTimeMillis() + ".jpeg";
        con.setFilename("file", fichernom);
        System.out.println(filePath);
        try {
            con.addData("file", filePath, mime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (con.getResponseCode() == 200) {
                    Dialog.show("Success", "Added Successfully", "Ok", null);
                } else {
                    Dialog.show("Failed", "Existing User", "Ok", null);
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }*/

    public void login(String email, String password) {
    ConnectionRequest con = new ConnectionRequest();
        con.addRequestHeader("Content-Type", "application/json");
        con.setPost(false);
        con.addArgument("email", email);
        con.addArgument("password", password);
       


        con.setUrl(Statics.BASE_URL+"user/login");
        User user = new User();
        con.addResponseListener((NetworkEvent evt) -> {
            if (con.getResponseCode() == 200) {
                try {
                    JSONParser jsonp = new JSONParser();
                    Map<String, Object> mapUser = (Map<String, Object>) jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(mapUser);
                    float id = (int) Float.parseFloat(mapUser.get("id").toString());
                    user.setId((int) id);
                    Session.ConnectedUser.setPassword(password);
                    Session.ConnectedUser.setId((int) Float.parseFloat(mapUser.get("id").toString()));
                    Session.ConnectedUser.setEmail(mapUser.get("email").toString());
                    Session.ConnectedUser.setFirstName(mapUser.get("firstName").toString());
                    Session.ConnectedUser.setLastName(mapUser.get("lastName").toString());
                    Session.ConnectedUser.setPhone((int) Float.parseFloat(mapUser.get("phone").toString()));
                    if (mapUser.get("imageName").toString().equals("")) {
                        Session.ConnectedUser.setImageName("image-blank.jpg");
                    } else Session.ConnectedUser.setImageName(mapUser.get("imageName").toString());
                    Session.ConnectedUser.setRoles(mapUser.get("roles").toString());
                    Session.ConnectedUser.setAdresse(mapUser.get("adresse").toString());
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                   
                    try {
                        Session.ConnectedUser.setDateOfBirth(format.parse(mapUser.get("createdAt").toString()));

                    } catch (ParseException ex) {
                    }
                    System.out.println("connected User : " + Session.ConnectedUser);
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);    }
    
    public ArrayList<User> parseUsers(String jsonText){
        if(jsonText==null)
                return users;
        try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            if(tasksListJson.get("data")==null)
                return users;
            
            
            Map<String,Object> listdata = j.parseJSON(new CharArrayReader(tasksListJson.get("data").toString().toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)listdata.get("root");
            System.out.println(list);
            for(Map<String,Object> obj : list){
                
                User m = new User(
                    (int)Float.parseFloat(obj.get("id").toString()),
                    obj.get("pseudo").toString(),
                    obj.get("specialty").toString()
                );
                users.add(m);
            }
            
            
        } catch (IOException ex) {
            
        }
        return users;
    }
    
    public ArrayList<User> getHelpers(){
        String url = Statics.BASE_URL+"user/helpers";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
     public void sendEmail(String email){
        MultipartRequest con = new MultipartRequest();


        System.out.println(email);

        con.setUrl(Statics.BASE_URL+"user/request-password-api" + "?email="+email);
        con.setPost(true);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (con.getResponseCode() == 200) {
                    Dialog.show("Success", "Reset Password Sent Successfuly", "Ok", null);
                } else {
                    Dialog.show("Failed", "Please enter a valid mail", "Ok", null);
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);

    }
   public boolean EditProfile(User u) {
        String url = Statics.BASE_URL + "user/"+u.getId()+"/edit?pseudo=" +
                u.getPseudo()+ "&password=" + u.getPassword()+
                "&email=" + u.getEmail();
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}