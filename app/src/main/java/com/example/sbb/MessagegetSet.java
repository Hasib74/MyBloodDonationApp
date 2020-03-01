package com.example.sbb;

public class MessagegetSet {

    String date, message, senderprofileimage, time,username, send_imageUrl;

    public MessagegetSet(){

    }

    public MessagegetSet(String date, String message, String senderprofileimage, String time, String username, String send_imageUrl) {
        this.date = date;
        this.message = message;
        this.senderprofileimage = senderprofileimage;
        this.time = time;
        this.username = username;
        this.send_imageUrl = send_imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderprofileimage() {
        return senderprofileimage;
    }

    public void setSenderprofileimage(String senderprofileimage) {
        this.senderprofileimage = senderprofileimage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSend_imageUrl() {
        return send_imageUrl;
    }

    public void setSend_imageUrl(String send_imageUrl) {
        this.send_imageUrl = send_imageUrl;
    }
}
