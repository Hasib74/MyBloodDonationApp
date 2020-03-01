package com.example.sbb.Helper;

public class Notification {


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Notification(String body, String title) {

        this.body = body;
        this.title = title;
    }

    public Notification(){

    }

    private String body;
    private String title;
}
