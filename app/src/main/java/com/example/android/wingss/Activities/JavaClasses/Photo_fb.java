package com.example.android.wingss.Activities.JavaClasses;

/**
 * Created by Ninaad on 5/30/2018.
 */


public class Photo_fb {

    String created_time;
    String name;
    String id;

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Photo_fb(String created_time, String name, String id) {
        super();
        this.created_time = created_time;
        this.id = id;
        this.name = name;
    }
}


