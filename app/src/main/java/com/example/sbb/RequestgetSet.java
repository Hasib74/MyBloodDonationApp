package com.example.sbb;

public class RequestgetSet {

    private String profile_imageLink;
    private String name;
    private String blood;

    public RequestgetSet(){

    }

    public RequestgetSet(String profile_imageLink, String name, String blood) {
        this.profile_imageLink = profile_imageLink;
        this.name = name;
        this.blood = blood;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
