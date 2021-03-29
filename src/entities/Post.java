/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;

/**
 *
 * @author anaso
 */


enum Module {
    MATHEMATIQUE_DE_BASE_1, MATHEMATIQUE_DE_BASE_2, IP_ESSENTIALS, TECHNIQUE_DESTIMATION_POUR_LINGENIEUR
}

public class Post {

    private int id,ownerId,solution_id;
    private String problem;
    private Timestamp timestamp;
    private String status;
    private String module;
    private String imageName;

    public Post() {
        if (status==null)
            status="OPEN";
    }

    public Post(String problem, String module) {
        this.problem = problem;
        this.module = module;
    }

    public Post(int id, String problem, String module, Timestamp timestamp) {
        this.id = id;
        this.problem = problem;
        this.module = module;
        this.timestamp = timestamp;
    }

    public Post(int id, String problem, String imageName) {
        this.id = id;
        this.problem = problem;
        this.imageName = imageName;
    }

    public Post(String problem, String module, String imageName) {
        this.problem = problem;
        this.module = module;
        this.imageName = imageName;
    }

    
    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    
    public int getId() {
        return id;
    }

    public String getProblem() {
        return problem;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }



    public String getModule() {
        return module;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public void setModule(String module) {
        this.module = module;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getSolution_id() {
        return solution_id;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setSolution_id(int solution_id) {
        this.solution_id = solution_id;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", ownerId=" + ownerId + ", solution_id=" + solution_id + ", problem=" + problem + ", timestamp=" + timestamp + ", status=" + status + ", module=" + module + ", imageName=" + imageName + '}';
    }


    
    
    
    

}
