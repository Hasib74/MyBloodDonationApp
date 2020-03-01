package com.example.sbb;

public class Onlinegetset {

    String profile_imageLink;
    String name;

    public Onlinegetset(){

    }

    public Onlinegetset(String profile_imageLink, String name) {
        this.profile_imageLink = profile_imageLink;
        this.name = name;
    }

    public String getProfile_imageLink() {
        return profile_imageLink;
    }

    public void setProfile_imageLink(String profile_imageLink) {
        this.profile_imageLink = profile_imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
