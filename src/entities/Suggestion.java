/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class Suggestion {
    String Sujet,Record;
    String Suggestion;
    int Id_Sugg;
    
    
    public Suggestion ()
    {}
    public Suggestion(int ID_Sugg,String suggestion,String Record )
      {this.Id_Sugg = ID_Sugg;
        this.Suggestion = Suggestion;
        this.Sujet = Sujet;
        this.Record = Record;
       }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String Record) {
        this.Record = Record;
    }

    public String getSujet() {
        return Sujet;
    }

    public void setSujet(String Sujet) {
        this.Sujet = Sujet;
    }

    public int getId_Sugg() {
        return Id_Sugg;
    }

    public void setId_Sugg(int Id_Sugg) {
        this.Id_Sugg = Id_Sugg;
    }

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String text) {
        this.Suggestion=text;
    }

    @Override
    public String toString() {
        return "Suggestion{" + "Sujet=" + Sujet + ", suggestion=" + Suggestion+ ", Record=" + Record + ", Id_Sugg=" + Id_Sugg + '}';
    }

    
    
    
    
}
