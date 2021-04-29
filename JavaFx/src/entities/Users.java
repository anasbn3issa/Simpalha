/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Base64;
import static java.util.Collections.list;
import java.util.function.Function;


/**
 *
 * @author win10
 */
public class Users {

    public Users(String password, String email, String username, String about, String adresse, String first_name, String last_name, String date_of_birth, int phone) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.about = about;
     
        this.adresse = adresse;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.updated_at = updated_at;
        this.phone = phone;
       
    }
     private String password, code, email, username;
    private String Specialty;
    private String about,activated_at,image_name,adresse;
    private String first_name,last_name,token,date_of_birth,created_at,updated_at;
    
    private int is_active,phone;
    
   // private String encyptedpasswd=getPassword();
   // private String decyptedpasswd=getDecryptedPassword();

    private int id,role;

    public String getActivated_at() {
        return activated_at;
    }

    public void setActivated_at(String activated_at) {
        this.activated_at = activated_at;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
   

    public Users() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Users(int id, int role, String password, String code, String email, String username, String Specialty, String about, String activated_at, String image_name, String adresse, String first_name, String last_name, String token, String date_of_birth, String created_at, String updated_at, int is_active, int phone) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.code = code;
        this.email = email;
        this.username = username;
        this.Specialty = Specialty;
        this.about = about;
        this.activated_at = activated_at;
        this.image_name = image_name;
        this.adresse = adresse;
        this.first_name = first_name;
        this.last_name = last_name;
        this.token = token;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_active = is_active;
        this.phone = phone;
    }

    public Users(String username, String password, String about) {
        this.password = password;
        this.username = username;
        this.about = about;

    }


    public Users(int id, String password, String email, String username, String Specialty,String about,int role) {
        this.id = id;
        this.role = role;
        this.password = password;
        this.email = email;
        this.about = about;
        this.role = role;
        this.username = username;
        this.Specialty = Specialty;
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
        //return Base64.getEncoder().encodeToString(password.getBytes());
        return password;
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

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String Specialty) {
        this.Specialty = Specialty;
    }

    
    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", role=" + role + ", password=" + password + ", code=" + code + ", email=" + email + ", username=" + username + ", Specialty=" + Specialty + ", about=" + about + '}';
    }
    
    

    

   

   

}
