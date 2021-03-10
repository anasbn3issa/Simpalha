/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;

/**
 *
 * @author α Ω
 */
public class Users {
    private int id;
    private String email,password, fname, lname,specialites;
    private Timestamp created_at;

    public Users() {
    }

    public Users(String email, String password, String fname, String lname, String specialites) {
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.specialites = specialites;
    }

    public Users(int id, String email, String password, String fname, String lname, String specialites, Timestamp created_at) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.specialites = specialites;
        this.created_at = created_at;
    }
    
    public Users(int id, String email, String fname, String lname, String specialites, Timestamp created_at) {
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.specialites = specialites;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSpecialites() {
        return specialites;
    }

    public void setSpecialites(String specialites) {
        this.specialites = specialites;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", email=" + email + ", password=" + password + ", fname=" + fname + ", lname=" + lname + ", specialites=" + specialites + ", created_at=" + created_at + '}';
    }

    
    
    
    
    
}
