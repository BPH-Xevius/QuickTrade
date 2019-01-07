package com.example.xeviu.quicktrade;

public class Users  {

    String id,usrnme,name,lastname,address;

    public Users()
    {

    }

    public Users(String id, String usrnme, String name, String lastname, String address) {
        this.id = id;
        this.usrnme = usrnme;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsrnme() {
        return usrnme;
    }

    public void setUsrnme(String usrnme) {
        this.usrnme = usrnme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
