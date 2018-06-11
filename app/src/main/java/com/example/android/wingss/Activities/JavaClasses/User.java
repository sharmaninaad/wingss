package com.example.android.wingss.Activities.JavaClasses;

/**
 * Created by Ninaad on 6/11/2018.
 */

public class User {
    private String mail_id;
    private String name;
    private int no_books;
    private int rank;//-5 to +5

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", no_books=" + no_books +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mail_id != null ? mail_id.equals(user.mail_id) : user.mail_id == null;

    }

    @Override
    public int hashCode() {
        return mail_id != null ? mail_id.hashCode() : 0;
    }

    public User(String mail_id, String name) {
        this.mail_id = mail_id;

        this.name = name;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo_books() {
        return no_books;
    }

    public void setNo_books(int no_books) {
        this.no_books = no_books;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
