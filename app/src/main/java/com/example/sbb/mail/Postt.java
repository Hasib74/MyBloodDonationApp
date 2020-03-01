package com.example.sbb.mail;

public class Postt {

   String userEmail;
   String userPassword;
   String friendEmail;
   String subject;
   String body;



    public Postt(String userEmail, String userPassword, String friendEmail, String subject, String body) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.friendEmail = friendEmail;
        this.subject = subject;
        this.body = body;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
