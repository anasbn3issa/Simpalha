/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Question;
import entities.Quizz;
import interfaces.IServiceQuizz;
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
public class ServiceQuizz implements IServiceQuizz {
    
    Connection cnx;
    
    public ServiceQuizz(){
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void AddQuizz(Quizz q) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `quizz`(`title`, `subject`, `helper_id`) VALUES ('"+q.getTitle()+"','"+q.getSubject()+"','"+q.getHelper()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Quizz> ReadAllQuizzes() throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `quizz`";
            ResultSet rst = stm.executeQuery(query);
            
            List<Quizz> quizzes = new ArrayList<>();
            
            while(rst.next())
            {
                Quizz Q = new Quizz();
                
                Q.setId(rst.getInt("id"));
                Q.setTitle(rst.getString("title"));
                Q.setSubject(rst.getString("subject"));
                Q.setHelper(rst.getInt("helper_id"));
                quizzes.add(Q);
            }
            
            return quizzes;
    }

    @Override
    public List<Quizz> ReadQuizzes(int helperId) throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `quizz` WHERE `helper_id`='"+helperId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            List<Quizz> quizzes = new ArrayList<>();
            
            while(rst.next())
            {
                Quizz Q = new Quizz();
                
                Q.setId(rst.getInt("id"));
                Q.setTitle(rst.getString("title"));
                Q.setSubject(rst.getString("subject"));
                Q.setHelper(helperId);
                
                quizzes.add(Q);
            }
            
            return quizzes;
    }

    @Override
    public ArrayList<Question> LoadQuestions(int quizzId) throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question` WHERE `quizz_id`='"+quizzId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            ArrayList<Question> questions = new ArrayList<>();
            
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
    public ObservableList<Quizz> ObservableListAllQuizzes() throws SQLException {
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `quizz`";
            ResultSet rst = stm.executeQuery(query);
            
            List<Quizz> quizzes = new ArrayList<>();
            
            while(rst.next())
            {
                Quizz Q = new Quizz();
                
                Q.setId(rst.getInt("id"));
                Q.setTitle(rst.getString("title"));
                Q.setSubject(rst.getString("subject"));
                Q.setHelper(rst.getInt("helper_id"));
                quizzes.add(Q);
            }
            
            
            ObservableList<Quizz> quizzesObservable = FXCollections.observableArrayList();
            
            quizzesObservable.addAll(quizzes);
            
            return quizzesObservable;
    }

    @Override
    public ObservableList<Quizz> ObservableListQuizzes(int helperId) throws SQLException {
        
            
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `quizz` WHERE `helper_id`='"+helperId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            ObservableList<Quizz> quizzesObservable = FXCollections.observableArrayList();
            
            while(rst.next())
            {
                Quizz Q = new Quizz();
                
                Q.setId(rst.getInt("id"));
                Q.setTitle(rst.getString("title"));
                Q.setSubject(rst.getString("subject"));
                Q.setHelper(rst.getInt("helper_id"));
                
                quizzesObservable.add(Q);
            }
            
            return quizzesObservable;
    }

    @Override
    public void RemoveQuizz(int id) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="DELETE FROM `quizz` WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void EditQuizz(int id, Quizz q) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="UPDATE `quizz` SET `title`='"+q.getTitle()+"',`subject`='"+q.getSubject()+"' WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int LastAddedQuizzId() throws SQLException{
        
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `quizz` ORDER BY `id` DESC LIMIT 1";
            ResultSet rst = stm.executeQuery(query);
            
            Quizz Q = new Quizz();
            
            while(rst.next())
            {
                
                Q.setId(rst.getInt("id"));
                Q.setTitle(rst.getString("title"));
                Q.setSubject(rst.getString("subject"));
                Q.setHelper(rst.getInt("helper_id"));
            }
            
            return Q.getId();
    }
    
}
