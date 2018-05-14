package com.mike.itesm.Objects;

public class User {
    private String FirstName;
    private String LastName;
    private String Contact;
    private String Photo;
    private int id;

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getAddress() {
        return Contact;
    }

    public void setAddress(String Contact) {
        this.Contact= Contact;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo= Photo;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    private static final User holder = new User();

    public static User getInstance() {
        return holder;
    }
}
