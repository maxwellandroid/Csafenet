package com.maxwell.csafenet.model;

import java.io.Serializable;

public class AnswersDetailsModel implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getObservationCode() {
        return observationCode;
    }

    public void setObservationCode(String observationCode) {
        this.observationCode = observationCode;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    String id;
    String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    String questionId;
    String fieldId;
    String answer;
    String observationCode;
    String enterDate;
    String regDate;
    String regTime;
}
