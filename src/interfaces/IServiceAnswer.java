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
public interface IServiceAnswer extends IService<Answer> {
    
//    add a suggestion answer to a question
//    old : public void AddAnswer(Answer a);
//    public void Create(T variable);
    
//    show all answers related to a question
//    old : public List<Answer> ReadAnswers(int questionId) throws SQLException;
//    public List<T> findAllById(int id);
    
//    remove an answer by its' id
//    old : public void RemoveAnswer (int id);
//    public void Delete(T variable);
    
//    edit an answer by its' id
//    old : public void EditAnswer(int id,Answer a);
//    public void Update(T variable);
    
//    find answer by its' id
//    public T findById(int id);
    
//    show all answers
//    public List<T> Read();
    
//    show all answer related to a question in an observablelist
    public ObservableList<Answer> ObservableListAnswers(Question q) throws SQLException ;
     
//    counts the number of answers corresponding a question
    public int CountAnswers(int questionId) throws SQLException;
    
}
