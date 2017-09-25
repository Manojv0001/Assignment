package com.example.pankaj.assignment.model;

/**
 * Created by Pankaj on 21-06-2017.
 */

public class UserInfo {
    int id;
    String firstname;
    String lastname;
    String photourl;
    String email;
    String gender;

    public UserInfo(){}

    public UserInfo(String firstname,String lastname,String photourl,String email,String gender){
        this.firstname = firstname;
        this.lastname = lastname;
        this.photourl = photourl;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
