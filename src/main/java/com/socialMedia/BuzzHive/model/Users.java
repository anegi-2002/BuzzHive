package com.socialMedia.BuzzHive.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Users {
    @Id
    private int id;
    private String username;
    private String type;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
