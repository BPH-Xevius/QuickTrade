package com.example.xeviu.quicktrade;

public class Bookmark {

    private String user, item;

    public Bookmark(){

    }


    public Bookmark(String user, String item) {
        this.user = user;
        this.item = item;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
