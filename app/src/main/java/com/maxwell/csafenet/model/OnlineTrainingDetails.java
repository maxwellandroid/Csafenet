package com.maxwell.csafenet.model;

import java.util.List;

public class OnlineTrainingDetails {

    String id;
    String title;

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    List<String> question;
    List<String> option1;
    List<String> option2;
    List<String> option3;

    public List<String> getOption1() {
        return option1;
    }

    public void setOption1(List<String> option1) {
        this.option1 = option1;
    }

    public List<String> getOption2() {
        return option2;
    }

    public void setOption2(List<String> option2) {
        this.option2 = option2;
    }

    public List<String> getOption3() {
        return option3;
    }

    public void setOption3(List<String> option3) {
        this.option3 = option3;
    }

    public List<String> getQuestionId() {
        return questionId;
    }

    public void setQuestionId(List<String> questionId) {
        this.questionId = questionId;
    }

    List<String> questionId;


    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    List<String> imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





}
