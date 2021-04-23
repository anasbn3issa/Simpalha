/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author win10
 */
public class UserSession {
    private static UserSession instance;
    public int userid;
    
    
    
     private UserSession(int userid) {
        this.userid = userid;
    }

    public static UserSession getInstace(int userid) {
        if(instance == null) {
            instance = new UserSession(userid);
        }
        return instance;
    }

    public int getUserid() {
        return userid;
    }
    
    public void cleanUserSession() {
        userid = 0;// or null
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{" + "userid=" + userid + '}';
    }
    
}
