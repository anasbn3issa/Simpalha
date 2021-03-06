/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Question;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Parsath
 */
public interface IServiceQuestion {
    
//    add a question
    public void AddQuestion(Question q);
    
//    show all questions
    public List<Question> ReadQuestions() throws SQLException;
    
//    show all questions in an observablelist
    public ObservableList<Question> ObservableListQuestions() throws SQLException ;
    
//    remove a question by its' id
    public void RemoveQuestion (int id);
    
//    edit a question by its' id
    public void EditQuestion(int id,Question q);
    
}
