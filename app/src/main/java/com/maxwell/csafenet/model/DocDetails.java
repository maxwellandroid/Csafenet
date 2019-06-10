package com.maxwell.csafenet.model;

public class DocDetails {

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocRevision() {
        return docRevision;
    }

    public void setDocRevision(String docRevision) {
        this.docRevision = docRevision;
    }

    public String getDocIssueDate() {
        return docIssueDate;
    }

    public void setDocIssueDate(String docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getDocOwner() {
        return docOwner;
    }

    public void setDocOwner(String docOwner) {
        this.docOwner = docOwner;
    }

    String docType;
    String docNo;
    String docTitle;
    String docRevision;
    String docIssueDate;
    String projectTitle;
    String docOwner;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    String filePath;
}
