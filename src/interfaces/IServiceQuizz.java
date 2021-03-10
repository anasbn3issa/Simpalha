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
public interface IServiceQuizz {
    
//    add a quizz
    public void AddQuizz(Quizz q);
    
//    show all quizzes
    public List<Quizz> ReadAllQuizzes() throws SQLException;
    
//    show all quizzes that a certain helper Created
    public List<Quizz> ReadQuizzes(int helperId) throws SQLException;
    
//    load all questions of the quizz
    public ArrayList<Question> LoadQuestions(int quizzId) throws SQLException;
    
//    show all quizzes in an observablelist
    public ObservableList<Quizz> ObservableListAllQuizzes() throws SQLException ;
    
//    show all quizzes in an observablelist
    public ObservableList<Quizz> ObservableListQuizzes(int helperId) throws SQLException ;
    
//    remove a quizz by its' id
    public void RemoveQuizz (int id);
    
//    edit a quizz by its' id
    public void EditQuizz(int id,Quizz q);
    
//    find last added Quizz row and take its ID
    public int LastAddedQuizzId() throws SQLException;
    
}
