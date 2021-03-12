/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * This class was created to track the result of a Quiz between the FXMLEvalController and the 
 * FXMLEvalAnswer Controller.
 * @author Parsath
 */
public class QuizzStats {
    
//    meant to track the number of still unresolved questions in a Quizz
    private int unresolvedEval;
//    track the result of the Quiz during the evaluation
    private int totalResultEval;
//    keep the total number of Questions a Quiz has.
    private int nbQuestionsEval;

    public QuizzStats() {
    }

    public int getUnresolvedEval() {
        return unresolvedEval;
    }

    public void setUnresolvedEval(int unresolvedEval) {
        this.unresolvedEval = unresolvedEval;
    }

    public int getTotalResultEval() {
        return totalResultEval;
    }

    public void setTotalResultEval(int totalResultEval) {
        this.totalResultEval = totalResultEval;
    }

    public int getNbQuestionsEval() {
        return nbQuestionsEval;
    }

    public void setNbQuestionsEval(int nbQuestionsEval) {
        this.nbQuestionsEval = nbQuestionsEval;
    }

    @Override
    public String toString() {
        return "QuizzStats{" + "unresolvedEval=" + unresolvedEval + ", totalResultEval=" + totalResultEval + ", nbQuestionsEval=" + nbQuestionsEval + '}';
    }
    
    
}
