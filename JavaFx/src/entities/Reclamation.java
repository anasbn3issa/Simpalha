/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 *
 * @author PC
 */
public class Reclamation {
    int Id;
    String Idreportee,Idreported,description, FileSelected ,Record;
    LocalDate dateRec,dateResolution;
    int ValidStudent, ValidHelper,Status;

    public Reclamation() {
    }

    public Reclamation(int Id, String Idreportee, String Idreported, String description, String image, String Record, LocalDate dateRec, LocalDate dateResolution, int Status) {
        this.Id = Id;
        this.Idreportee = Idreportee;
        this.Idreported = Idreported;
        this.description = description;
        this.FileSelected =image;
        this.Record=Record;
        this.dateRec = dateRec;
        this.dateResolution = dateResolution;
        this.ValidStudent=ValidStudent;
        this.ValidHelper=ValidHelper;
        this.Status= Status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String Record) {
        this.Record = Record;
    }

    public String getFileSelected() {
        return FileSelected;
    }

    public void setFileSelected(String FileSelected) {
        this.FileSelected = FileSelected;
    }


    public int getValidStudent() {
        return ValidStudent;
    }

    public void setValidStudent(int ValidStudent) {
        this.ValidStudent = ValidStudent;
    }

    public int getValidHelper() {
        return ValidHelper;
    }

    public void setValidHelper(int ValidHelper) {
        this.ValidHelper = ValidHelper;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getIdreportee() {
        return Idreportee;
    }

    public void setIdreportee(String Idreportee) {
        this.Idreportee = Idreportee;
    }

    public String getIdreported() {
        return Idreported;
    }

    public void setIdreported(String Idreported) {
        this.Idreported = Idreported;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateRec() {
        return dateRec;
    }

    public void setDateRec(LocalDate dateRec) {
        this.dateRec = dateRec;
    }

    public LocalDate getDateResolution() {
        return dateResolution;
    }

    public void setDateResolution(LocalDate dateResolution) {
        this.dateResolution = dateResolution;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "Id=" + Id + ", Idreportee=" + Idreportee + ", Idreported=" + Idreported + ", description=" + description + ", FileSelected=" + FileSelected+ ", Record=" + Record + ", dateRec=" + dateRec + ", dateResolution=" + dateResolution + ", ValidStudent=" + ValidStudent + ", ValidHelper=" + ValidHelper + ", Status=" + Status+ '}';
    }

    
}
