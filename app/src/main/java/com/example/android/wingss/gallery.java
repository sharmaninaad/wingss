package com.example.android.wingss;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class gallery extends AppCompatActivity {
    String birthdate_fb;
    String photos_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);
        if (Login.logged_in_from_facebook) {
            birthdate_fb = sharedPref.getString("birthday", "");
            photos_fb = sharedPref.getString("photographs", "");
            try {
                JSONObject json = new JSONObject(photos_fb + "");
                JSONArray jsonArray = json.getJSONArray("data");
                String[] arr_pic_name = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    arr_pic_name[i] = c.getString("name");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
