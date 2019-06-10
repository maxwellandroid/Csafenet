package com.maxwell.csafenet.model;

import java.io.Serializable;

public class BulletinModules implements Serializable {

    String title;
    String issuedate;
    String issuer;
    String shortdescription;
    String category;
    String filePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
