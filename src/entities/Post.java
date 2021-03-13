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
enum Status {
    OPEN, SOLVED, PENDING
}

enum Module {
    MATHEMATIQUE_DE_BASE_1, MATHEMATIQUE_DE_BASE_2, IP_ESSENTIALS, TECHNIQUE_DESTIMATION_POUR_LINGENIEUR
}

public class Post {

    private int id;
    private String problem;
    private Timestamp timestamp;
    private Status status = Status.PENDING;
    private String module;

    public Post() {
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

    public int getId() {
        return id;
    }

    public String getProblem() {
        return problem;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
       if (this.status == Status.OPEN) {
            return "OPEN";
        } else if (this.status == Status.SOLVED) {
            return "SOLVED";
        } else {
            return "PENDING";
        }
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

    public void setStatus(String status) {
        if (status.equals("OPEN")) {
            this.status = Status.OPEN;
        } else if (status.equals("SOLVED")) {
            this.status = Status.SOLVED;
        } else {
            this.status = Status.PENDING;
        }
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + "status=" + status + ", problem=" + problem + ", timestamp=" + timestamp + ", module=" + module + '}' + "\n";
    }

}
