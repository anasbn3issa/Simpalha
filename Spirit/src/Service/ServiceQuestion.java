/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Question;
import Services.IServiceQuestion;
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
 *
 * @author Parsath
 */
public class ServiceQuestion implements IServiceQuestion{
    
    Connection cnx;
    
    public ServiceQuestion(){
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void AddQuestion(Question q) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `question`(`right_answer`, `question`) VALUES ('"+q.getAnswer()+"','"+q.getQuestion()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Question> ReadQuestions() throws SQLException {
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question`";
            ResultSet rst = stm.executeQuery(query);
            
            List<Question> questions = new ArrayList<>();
            
            while(rst.next())
            {
                Question Q = new Question();
                
                Q.setId(rst.getInt("id"));
                Q.setQuestion(rst.getString("question"));
                Q.setAnswer(rst.getInt("right_answer"));
                questions.add(Q);
            }
            
            return questions;
    }

    @Override
    public ObservableList<Question> ObservableListQuestions() throws SQLException{
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question`";
            ResultSet rst = stm.executeQuery(query);
            
            List<Question> questions = new ArrayList<>();
            
            while(rst.next())
            {
                Question Q = new Question();
                
                Q.setId(rst.getInt("id"));
                Q.setQuestion(rst.getString("question"));
                Q.setAnswer(rst.getInt("right_answer"));
                questions.add(Q);
            }
            
            
            ObservableList<Question> questionsObservable = FXCollections.observableArrayList();
            
            questionsObservable.addAll(questions);
            
            return questionsObservable;
    }

    @Override
    public void RemoveQuestion(int id) {
        
        try{
            Statement stm = cnx.createStatement();
            
//            String query="INSERT INTO `question`(`right_answer`, `question`) VALUES ('"+q.getRightAnswer()+"','"+q.getQuestion()+"')";
            String query="DELETE FROM `question` WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void EditQuestion(int id, Question q) {
        RemoveQuestion(id);
//        AddQuestion(q);
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `question`(`id`,`right_answer`, `question`) VALUES ('"+id+"','"+q.getAnswer()+"','"+q.getQuestion()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
