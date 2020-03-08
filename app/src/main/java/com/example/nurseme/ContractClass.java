package com.example.nurseme;

public class ContractClass {
    String nurseemail, patientemail, startdate, enddate, status, reason, rating, review, Salary;


    public ContractClass() {
    }

    public String getNurseemail() {
        return nurseemail;
    }

    public void setNurseemail(String nurseemail) {
        this.nurseemail = nurseemail;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public ContractClass(String nurseemail, String patientemail, String startdate, String enddate, String status, String reason, String rating, String review, String salary) {
        this.nurseemail = nurseemail;
        this.patientemail = patientemail;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.reason = reason;
        this.rating = rating;
        this.review = review;
        Salary = salary;
    }
}