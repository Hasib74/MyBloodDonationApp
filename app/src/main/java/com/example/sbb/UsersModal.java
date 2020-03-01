package com.example.sbb;

public class UsersModal {

    String address, blood, profile_imageLink, name;

    public UsersModal(){


    }

    public UsersModal(String address, String blood, String profile_imageLink, String name) {
        this.address = address;
        this.blood = blood;
        this.profile_imageLink = profile_imageLink;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
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
}
