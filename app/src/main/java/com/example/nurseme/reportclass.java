package com.example.nurseme;

public class reportclass {
    String complaint;
    String severity;
    String complaitrecipient, date, status, complaintsender;

    public reportclass(String complaint, String severity, String complaitrecipient, String date, String status, String complaintsender) {
        this.complaint = complaint;
        this.severity = severity;
        this.complaitrecipient = complaitrecipient;
        this.date = date;
        this.status = status;
        this.complaintsender = complaintsender;
    }

    public reportclass() {
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getComplaitrecipient() {
        return complaitrecipient;
    }

    public void setComplaitrecipient(String complaitrecipient) {
        this.complaitrecipient = complaitrecipient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplaintsender() {
        return complaintsender;
    }

    public void setComplaintsender(String complaintsender) {
        this.complaintsender = complaintsender;
    }
}