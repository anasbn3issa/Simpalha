/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Answer;
import entities.Question;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Parsath
 */
public interface IServiceAnswer {
    
//    add a suggestion answer to a question
    public void AddAnswer(Answer a);
    
//    show all answers related to a question
    public List<Answer> ReadAnswers(Question q) throws SQLException;
    
//    show all answer related to a question in an observablelist
    public ObservableList<Answer> ObservableListAnswers(Question q) throws SQLException ;
    
//    remove an answer by its' id
    public void RemoveAnswer (int id);
    
//    edit an answer by its' id
    public void EditAnswer(int id,Answer a);
     
//    counts the number of answers corresponding a question
    public int CountAnswers(int questionId) throws SQLException;
    
}
