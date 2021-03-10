/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Answer;
import entities.Question;
import entities.Quizz;
import interfaces.IServiceQuestion;
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
            
            String query="INSERT INTO `question`(`right_answer`, `question`, `quizz_id`) VALUES ('"+q.getAnswer()+"','"+q.getQuestion()+"','"+q.getQuizz()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Question> ReadAllQuestions() throws SQLException {
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
                Q.setQuizz(rst.getInt("quizz_id"));
                
                questions.add(Q);
            }
            
            return questions;
    }
    
    public List<Question> ReadQuestions(int quizzId) throws SQLException{
        
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question` WHERE `quizz_id`='"+quizzId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            List<Question> questions = new ArrayList<>();
            
            while(rst.next())
            {
                Question Q = new Question();
                
                Q.setId(rst.getInt("id"));
                Q.setQuestion(rst.getString("question"));
                Q.setAnswer(rst.getInt("right_answer"));
                Q.setQuizz(quizzId);
                
                questions.add(Q);
            }
            
            return questions;
    }

    @Override
    public ObservableList<Question> ObservableAllListQuestions() throws SQLException{
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
                Q.setQuizz(rst.getInt("quizz_id"));
                
                questions.add(Q);
            }
            
            
            ObservableList<Question> questionsObservable = FXCollections.observableArrayList();
            
            questionsObservable.addAll(questions);
            
            return questionsObservable;
    }

    @Override
    public ObservableList<Question> ObservableListQuestions(int quizzId) throws SQLException {
        
            
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question` WHERE `quizz_id`='"+quizzId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            ObservableList<Question> questionsObservable = FXCollections.observableArrayList();
            
            while(rst.next())
            {
                Question Q = new Question();
                
                Q.setId(rst.getInt("id"));
                Q.setQuestion(rst.getString("question"));
                Q.setAnswer(rst.getInt("right_answer"));
                Q.setQuizz(rst.getInt("quizz_id"));
                
                questionsObservable.add(Q);
            }
            
            return questionsObservable;
    }

    @Override
    public void RemoveQuestion(int id) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="DELETE FROM `question` WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void EditQuestion(int id, Question q) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="UPDATE `question` SET `right_answer`='"+q.getAnswer()+"',`question`='"+q.getQuestion()+"' WHERE `id`='"+id+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Answer> LoadAnswers(int questionId) throws SQLException {
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `answer` WHERE `question_id`='"+questionId+"'";
            ResultSet rst = stm.executeQuery(query);
            
            ArrayList<Answer> answers = new ArrayList<>();
            
            while(rst.next())
            {
                Answer A = new Answer();
                
                A.setId(rst.getInt("id"));
                A.setSuggestion(rst.getString("suggestion"));
                
                answers.add(A);
            }
            
            return answers;
    }
    
    @Override
    public Question FindById(int id) throws SQLException{
        
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question` WHERE `id`='"+id+"'";
            ResultSet rst = stm.executeQuery(query);
            
            Question Q = new Question();
            
            while(rst.next())
            {
                Q.setId(rst.getInt("id"));
                Q.setQuestion(rst.getString("question"));
                Q.setAnswer(rst.getInt("right_answer"));
                Q.setQuizz(rst.getInt("quizz_id"));
            }
            
            
            return Q;
    }
    
    @Override
    public boolean IsCorrectAnswer(int indice,int questionId) throws SQLException{
        
        boolean answer=false;
        
        Question q = new Question();
        
        q = FindById(questionId);
        
        if(q.getAnswer() == indice)
            answer = true;
        
        return answer;
    }
    
    @Override
    public int CountQuestions(int quizzId) throws SQLException{
            
            Statement stm = cnx.createStatement();
            
            String query="SELECT COUNT(*) FROM `question` WHERE `quizz_id`='"+quizzId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            rst.next();
            int questionCount = rst.getInt(1);
            
            return questionCount;
    }
}
