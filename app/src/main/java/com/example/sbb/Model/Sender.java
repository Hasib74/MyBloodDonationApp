package com.example.sbb.Model;

public class Sender {

    private   String to;
    private  Notification notification;


    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }
}