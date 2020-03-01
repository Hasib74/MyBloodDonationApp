package com.example.sbb;

public class card_modal {

    String address,blood, gender, hight, name, phone, profile_imageLink;

    public card_modal(){

    }

    public card_modal(String address, String blood, String gender, String hight, String name, String phone, String profile_imageLink) {
        this.address = address;
        this.blood = blood;
        this.gender = gender;
        this.hight = hight;
        this.name = name;
        this.phone = phone;
        this.profile_imageLink = profile_imageLink;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_imageLink() {
        return profile_imageLink;
    }

    public void setProfile_imageLink(String profile_imageLink) {
        this.profile_imageLink = profile_imageLink;
    }
}
