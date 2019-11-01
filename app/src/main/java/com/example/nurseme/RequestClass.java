package com.example.nurseme;

public class RequestClass {
    String patientname;
    String patientemail;
    String nurseemail;
String status;

    public RequestClass() {
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }



    public String getNurseemail() {
        return nurseemail;
    }

    public void setNurseemail(String nurseemail) {
        this.nurseemail = nurseemail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RequestClass(String patientname, String patientemail, String nurseemail, String status) {
        this.patientname = patientname;
        this.patientemail = patientemail;

        this.nurseemail = nurseemail;
        this.status = status;
    }
}