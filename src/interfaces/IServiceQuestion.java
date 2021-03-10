/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Answer;
import entities.Question;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * @author Parsath
 */
public interface IServiceQuestion {
    
//    add a question
    public void AddQuestion(Question q);
    
//    show all questions
    public List<Question> ReadAllQuestions() throws SQLException;
    
//    show all questions
    public List<Question> ReadQuestions(int quizzId) throws SQLException;
    
//    show all questions in an observablelist
    public ObservableList<Question> ObservableAllListQuestions() throws SQLException ;
    
//    show all questions of a quizzin an observablelist
    public ObservableList<Question> ObservableListQuestions(int quizzId) throws SQLException ;
    
//    load all answers
    public ArrayList<Answer> LoadAnswers(int questionId) throws SQLException;
    
//    remove a question by its' id
    public void RemoveQuestion (int id);
    
//    edit a question by its' id
    public void EditQuestion(int id,Question q);
    
//    returns answer is right or wrong
    public boolean IsCorrectAnswer(int indice,int questionId) throws SQLException;
    
//    find question by id
    public Question FindById(int id) throws SQLException;
    
//    count questions in a quizz
    public int CountQuestions(int quizzId) throws SQLException;
    
}
