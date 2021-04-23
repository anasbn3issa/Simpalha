/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.QuizzResult;
import interfaces.IServiceQuizzResult;
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
 *
 * @author Parsath
 */
public class ServiceQuizzResult implements IServiceQuizzResult{
    
    Connection cnx;
    
    public ServiceQuizzResult(){
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(QuizzResult q) {
        
        try{
            Statement stm = cnx.createStatement();
            
            String query="INSERT INTO `quizz_result`(`result`, `result_date`, `student_id`, `quizz_id`) VALUES ('"+q.getResult()+"','"+q.getDate()+"','"+q.getStudent()+"','"+q.getQuizz()+"')";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(QuizzResult q) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="UPDATE `quizz_result` SET `result`='"+q.getResult()+"',`result_date`='"+q.getDate()+"',`student_id`='"+q.getStudent()+"' WHERE `quizz_id`='"+q.getQuizz()+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<QuizzResult> Read() {
        List<QuizzResult> quizzResults = new ArrayList<>();
        String query="SELECT * FROM `quizz_result`";
        
        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);

            while(rst.next())
            {
                QuizzResult qr = new QuizzResult();

                qr.setId(rst.getInt("id"));
                qr.setDate(rst.getTimestamp("result_date").toLocalDateTime());
                qr.setStudent(rst.getInt("student_id"));
                qr.setQuizz(rst.getInt("quizz_id"));
                qr.setResult(rst.getInt("result"));
                
                quizzResults.add(qr);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizzResults;
    }

    @Override
    public void Delete(QuizzResult q) {
        try{
            Statement stm = cnx.createStatement();
            
            String query="DELETE FROM `quizz_result` WHERE `id`='"+q.getId()+"'";
            stm.executeUpdate(query);
        }
        catch (SQLException ex) { 
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<QuizzResult> findAllById(int quizzId) {
        
        List<QuizzResult> quizzResults = new ArrayList<>();
        String query="SELECT * FROM `quizz_result` WHERE `quizz_id`='"+quizzId+"'";

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                QuizzResult qr = new QuizzResult();

                qr.setId(rst.getInt("id"));
                qr.setDate(rst.getTimestamp("result_date").toLocalDateTime());
                qr.setStudent(rst.getInt("student_id"));
                qr.setQuizz(rst.getInt("quizz_id"));
                qr.setResult(rst.getInt("result"));
                
                quizzResults.add(qr);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizzResults;
    }

    @Override
    public QuizzResult findById(int id) {
        
        QuizzResult qr = new QuizzResult();
        String query="SELECT * FROM `quizz_result` WHERE `id`='"+id+"'";

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                qr.setId(rst.getInt("id"));
                qr.setDate(rst.getTimestamp("result_date").toLocalDateTime());
                qr.setStudent(rst.getInt("student_id"));
                qr.setQuizz(rst.getInt("quizz_id"));
                qr.setResult(rst.getInt("result"));
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }

        return qr;
    }

    @Override
    public ObservableList<QuizzResult> ObservableListAllQuizzResults(){
        
        List<QuizzResult> quizzResults = new ArrayList<>();
        String query="SELECT * FROM `quizz_result`";

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                QuizzResult qr = new QuizzResult();

                qr.setId(rst.getInt("id"));
                qr.setDate(rst.getTimestamp("result_date").toLocalDateTime());
                qr.setStudent(rst.getInt("student_id"));
                qr.setQuizz(rst.getInt("quizz_id"));
                qr.setResult(rst.getInt("result"));
                
                quizzResults.add(qr);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }


        ObservableList<QuizzResult> quizzResultsObservable = FXCollections.observableArrayList();

        quizzResultsObservable.addAll(quizzResults);

        return quizzResultsObservable;
    }

    @Override
    public ObservableList<QuizzResult> ObservableListQuizzResults(int quizzId){

        String query="SELECT * FROM `quizz_result` WHERE `quizz_id`='"+quizzId+"'";
        ObservableList<QuizzResult> quizzResultsObservable = FXCollections.observableArrayList();

        try{
            Statement stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery(query);


            while(rst.next())
            {
                QuizzResult qr = new QuizzResult();

                qr.setId(rst.getInt("id"));
                qr.setDate(rst.getTimestamp("result_date").toLocalDateTime());
                qr.setStudent(rst.getInt("student_id"));
                qr.setQuizz(rst.getInt("quizz_id"));
                qr.setResult(rst.getInt("result"));
                
                quizzResultsObservable.add(qr);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ServiceQuizzResult.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizzResultsObservable;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
