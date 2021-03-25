/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Question;
import entities.Quizz;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Parsath
 */
public interface IServiceQuizz extends IService<Quizz> {
    
//    add a quizz
//    public void AddQuizz(Quizz q);
//    public void Create(T variable);
//    TODO
    
//    edit a quizz by its' id
//    public void EditQuizz(int id,Quizz q);
//    public void Update(T variable);
//    TODO
    
//    show all quizzes
//    public List<Quizz> ReadAllQuizzes() throws SQLException;
//    public List<T> Read();
//    TODO
    
//    remove a quizz by its' id
//    public void RemoveQuizz (int id);
//    public void Delete(T variable);
//    TODO
    
//    show all quizzes that a certain helper Created
//    public List<Quizz> ReadQuizzes(int helperId) throws SQLException;
//    public List<T> findAllById(int id);
//    TODO
    
//    find a Quiz by its' id
//    public T findById(int id);
//    TODO
    
//    load all questions of the quizz
    public ArrayList<Question> LoadQuestions(int quizzId) throws SQLException;
    
//    show all quizzes in an observablelist
    public ObservableList<Quizz> ObservableListAllQuizzes() throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Quizz> ObservableListQuizzes(int helperId) throws SQLException ;
    
//    find last added Quizz row and take its ID
    public int LastAddedQuizzId() throws SQLException;
    
}
