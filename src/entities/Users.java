/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static java.util.Collections.list;

/**
 *
 * @author win10
 */
public class Users {

            private int id;
           private String password,code,email,username;
           private String Specialité ;

    public Users() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Users(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public Users(int id, String password, String email, String username, String Specialité) {
        this.id = id;
        this.password = password;
        this.email = email;

        this.username = username;
        this.Specialité = Specialité;
    }
           

   

    public Users(String password, String email, String username) {
        this.password = password;
        this.email = email;
        this.username = username;
      
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

   

    public String  getSpecialité() {
        return Specialité;
    }

    public void setSpecialité(String Specialité) {
        this.Specialité = Specialité;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", password=" + password + ", email=" + email + ", username=" + username + ", Specialit\u00e9=" + Specialité + '}';
    }

    

   
    
 
           
    
}
     
       
    



  

