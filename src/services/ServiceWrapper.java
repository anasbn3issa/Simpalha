/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Question;
import entities.QuizzWrapper;
import interfaces.IServiceWrapper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author Parsath
 */
public class ServiceWrapper implements IServiceWrapper {
    
    Connection cnx;
    
    public ServiceWrapper(){
        cnx = DataSource.getInstance().getConnection();
    }
    
    @Override
    public ObservableList<QuizzWrapper> ObservableListQuestionsWrapper(int quizzId) throws SQLException{
            
            Statement stm = cnx.createStatement();
            
            String query="SELECT * FROM `question` WHERE `quizz_id`='"+quizzId+"'";

            ResultSet rst = stm.executeQuery(query);
            
            ObservableList<QuizzWrapper> quizzWrapperObservable = FXCollections.observableArrayList();
            
            while(rst.next())
            {
                QuizzWrapper Q = new QuizzWrapper();
                
                Q.getQuestion().setId(rst.getInt("id"));
                Q.getQuestion().setQuestion(rst.getString("question"));
                Q.getQuestion().setAnswer(rst.getInt("right_answer"));
                Q.getQuestion().setQuizz(rst.getInt("quizz_id"));
                Q.setStatus(false);
                
                quizzWrapperObservable.add(Q);
            }
            
            return quizzWrapperObservable;
    }
}
