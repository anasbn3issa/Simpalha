/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Answer;
import entities.Question;
import interfaces.IServiceAnswer;
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
import utils.Maconnexion;

/**
 * @author Parsath
 */
public class ServiceAnswer implements IServiceAnswer {
    
    Connection cnx;
    
    public ServiceAnswer(){
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Answer a) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `answer`(`suggestion`, `question_id`) VALUES ('"+a.getSuggestion()+"','"+a.getQ().getId()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(Answer a) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="UPDATE `answer` SET `suggestion`='"+a.getSuggestion()+"',`question_id`='"+a.getQ().getId()+"' WHERE `id`='"+a.getId()+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public List<Answer> Read(){
        List<Answer> answers = new ArrayList<>();
        String query="SELECT * FROM `answer`";

        try{
            Statement stm = cnx.createStatement();

            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                Answer A = new Answer();

                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("question"));

                answers.add(A);
            }
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answers;
    }

    @Override
    public void Delete(Answer a) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="DELETE FROM `answer` WHERE `id`='"+a.getId()+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Answer> findAllById(int id) {
        ServiceQuestion sq = new ServiceQuestion();
               
        List<Answer> answers = new ArrayList<>();
        String query="SELECT * FROM `answer` WHERE `question_id`='"+id+"'";

        try{
            Statement stm = cnx.createStatement();

            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                Answer A = new Answer();
                Question q;
                q = sq.findById(id);

                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("question"));
                A.setQ(q);

                answers.add(A);
            }
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answers;
    }
    
    @Override
    public Answer findById(int id){
        
        Answer A = new Answer();
        String query="SELECT * FROM `answer` WHERE `id`='"+id+"'";

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while(rst.next())
            {
                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("question"));
            }
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceAnswer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return A;
        
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
    public int CountAnswers(int questionId) throws SQLException{
            
            Statement stm = cnx.createStatement();
            
            String query="SELECT COUNT(*) FROM `answer` WHERE `question_id`='"+questionId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            rst.next();
            int answers = rst.getInt(1);
            
            return answers;
    }
    
    public boolean userExists(int userId) throws SQLException{
    
                Statement stm = cnx.createStatement();
            
            String query="SELECT COUNT(*) FROM `user` WHERE `user_id`='"+userId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            rst.next();
            int userCount = rst.getInt(1);
            
            boolean userExists = (userCount == 1);
            
            return userExists;
    }
    
}
