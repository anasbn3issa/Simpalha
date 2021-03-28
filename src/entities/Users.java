/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Base64;
import static java.util.Collections.list;

/**
 *
 * @author win10
 */
public class Users {

    private int id, role;
    private String password, code, email, username;
    private String Specialité;
    private String about;
   // private String encyptedpasswd=getPassword();
   // private String decyptedpasswd=getDecryptedPassword();

    public Users() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Users(String username, String password, String about) {
        this.password = password;
        this.username = username;
        this.about = about;

    }

    public Users(int id, String password, String email, String username, String Specialité, int role) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.email = email;
        this.username = username;
        this.Specialité = Specialité;
    }

    public Users(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Users(String password, String email, String username,String about, int role) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.about = about;
        this.role = role;

    }

    public String getPassword() {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
/*public String getDecryptedPassword() {
        return new String (Base64.getMimeDecoder().decode(encyptedpasswd));
    }*/
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

        return (username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSpecialité() {
        return Specialité;
    }

    public void setSpecialité(String Specialité) {
        this.Specialité = Specialité;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", role=" + role + ", password=" + password + ", code=" + code + ", email=" + email + ", username=" + username + ", Specialite" + Specialité + ", about=" + about + '}';
    }
    
    

    

   

}
