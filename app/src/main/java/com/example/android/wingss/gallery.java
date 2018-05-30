package com.example.android.wingss;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.android.wingss.Adapters.photo_detail_adapter;



public class gallery extends AppCompatActivity {
    String birthdate_fb;
    String photos_fb;
    ArrayList<String> fb_date_list;
    ArrayList<String> fb_name_list;
    ArrayList<String> fb_id_list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fb_date_list = new ArrayList<>();

        fb_name_list = new ArrayList<>();

        fb_id_list = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);

            birthdate_fb = sharedPref.getString("birthdate", "");

            photos_fb = sharedPref.getString("photos", "");
            try {
                JSONObject json = new JSONObject(photos_fb);
                JSONArray jsonArray = json.getJSONArray("data");
                Log.i("data", jsonArray.toString());
                Log.i("length", jsonArray.length() + "");
                int i = 0;
                try {

                    while (i < jsonArray.length()) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        fb_date_list.add(c.getString("created_time"));
                        fb_name_list.add(c.getString("name"));
                        fb_id_list.add(c.getString("id"));
                        i++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //    View child = getLayoutInflater().inflate(R.layout.content_gallery, null);

                photo_detail_adapter itemsAdapter = new photo_detail_adapter(this, fb_date_list, fb_name_list, fb_id_list);
                listView = (ListView) findViewById(R.id.fb_pic_list_view);


                listView.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
