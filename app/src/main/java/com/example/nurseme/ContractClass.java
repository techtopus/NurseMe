package com.example.nurseme;

public class ContractClass {
    String nurseemail,patientemail,startdate,enddate,status,remarks,nurseremarks,relativemessages,nursemessages;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public ContractClass() {
    }

    public String getRelativemessages() {
        return relativemessages;
    }

    public void setRelativemessages(String relativemessages) {
        this.relativemessages = relativemessages;
    }

    public String getNursemessages() {
        return nursemessages;
    }

    public void setNursemessages(String nursemessages) {
        this.nursemessages = nursemessages;
    }

    public String getNurseremarks() {
        return nurseremarks;
    }

    public void setNurseremarks(String nurseremarks) {
        this.nurseremarks = nurseremarks;
    }

    public ContractClass(String nurseemail, String patientemail, String startdate, String enddate, String status, String remarks, String nurseremarks, String relativemessages, String nursemessages) {
        this.nurseemail = nurseemail;
        this.patientemail = patientemail;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.remarks = remarks;
        this.nurseremarks = nurseremarks;
        this.relativemessages = relativemessages;
        this.nursemessages = nursemessages;
    }
}
