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
public interface IServiceQuestion extends IService<Question> {
    
//    add a question
//    old : public void Create(T variable);
//    public void AddQuestion(Question q);
    
    
//    show all questions
//    old : public List<Question> ReadAllQuestions() throws SQLException;
//    public List<T> Read();
    
    
//    show all Questions in a certain Quizz
//    old : public List<Question> ReadQuestions(int quizzId) throws SQLException;
//    public List<T> findAllById(int id);
    
    
//    update a question by its' id
//    old : public void EditQuestion(int id,Question q);
//    public void Update(T variable);
    
    
//    remove a question by its' id
//    old : public void RemoveQuestion (int id);
//    public void Delete(T variable);
    
//    find question by its' id
//    old : public Question FindById(int id) throws SQLException;
//    public T findById(int id);
    
//    load all answers
    public ArrayList<Answer> LoadAnswers(int questionId) throws SQLException;
    
//    returns answer is right or wrong
    public boolean IsCorrectAnswer(int indice,int questionId) throws SQLException;
    
//    show all questions in an observablelist
    public ObservableList<Question> ObservableAllListQuestions() throws SQLException ;
    
//    show all questions of a quizzin an observablelist
    public ObservableList<Question> ObservableListQuestions(int quizzId) throws SQLException ;
    
//    count questions in a quizz
    public int CountQuestions(int quizzId) throws SQLException;
    
}
