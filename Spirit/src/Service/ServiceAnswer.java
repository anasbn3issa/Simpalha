/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Answer;
import Entities.Question;
import Services.IServiceAnswer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 * @author Parsath
 */
public class ServiceAnswer implements IServiceAnswer {
    
    Connection cnx;
    
    public ServiceAnswer(){
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void AddAnswer(Answer a) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `answer`(`suggestion`, `question_id`) VALUES ('"+a.getSuggestion()+"','"+a.getQ().getId()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Answer> ReadAnswers(Question q) throws SQLException {
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `answer` WHERE `question_id`='"+q.getId()+"'";
            ResultSet rst = stm.executeQuery(query);
            
            List<Answer> answers = new ArrayList<>();
            
            while(rst.next())
            {
                Answer A = new Answer();
                
                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("question"));
                A.setQ(q);
                
                answers.add(A);
            }
            
            return answers;
    }

    @Override
    public ObservableList<Answer> ObservableListAnswers(Question q) throws SQLException {
            
            Statement stm = cnx.createStatement();
            
            System.out.println(q);
            String query="SELECT * FROM `answer` WHERE `question_id`='"+q.getId()+"'";

            ResultSet rst = stm.executeQuery(query);
            
            ObservableList<Answer> answersObservable = FXCollections.observableArrayList();
            
            while(rst.next())
            {
                Answer A = new Answer(q);
                
                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("suggestion"));
                
                answersObservable.add(A);
            }
            
            return answersObservable;
        
    }

    @Override
    public void RemoveAnswer(int id) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="DELETE FROM `answer` WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void EditAnswer(int id, Answer a) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="UPDATE `answer` SET `suggestion`='"+a.getSuggestion()+"',`question_id`='"+a.getQ().getId()+"' WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
