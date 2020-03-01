package com.example.sbb;

public class post_getset {

    String application;
    String bloodgroup;
    String date;
    String time;
    String name;
    String profileimage;
    String hospitalname;
    String locationname;

    public post_getset() {

    }

    public post_getset(String application, String bloodgroup, String date, String time, String name, String profileimage, String hospitalname, String locationname) {
        this.application = application;
        this.bloodgroup = bloodgroup;
        this.date = date;
        this.time = time;
        this.name = name;
        this.profileimage = profileimage;
        this.hospitalname = hospitalname;
        this.locationname = locationname;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }
}

