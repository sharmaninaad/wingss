package com.example.android.wingss.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.wingss.Activities.JavaClasses.Photo_fb;
import com.example.android.wingss.Adapters.photoDetailAdapter;
import com.example.android.wingss.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Gallery extends AppCompatActivity {
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


        fb_date_list = new ArrayList<>();

        fb_name_list = new ArrayList<>();

        fb_id_list = new ArrayList<>();


        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);

            birthdate_fb = sharedPref.getString("birthdate", "");

            photos_fb = sharedPref.getString("photos", "");

            JSONObject json ;
            try {
                json = new JSONObject(photos_fb);
                JSONArray jsonArray = json.getJSONArray("data");

                String json_data = jsonArray.toString();
                Type phot_list = new TypeToken<ArrayList<Photo_fb>>() {
                }.getType();
                List<Photo_fb> photos = new Gson().fromJson(json_data, phot_list);
                photoDetailAdapter itemsAdapter = new photoDetailAdapter(this, photos);
                listView = (ListView) findViewById(R.id.fb_pic_list_view);


                listView.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }






          /*  try {
                          JSONObject json = new JSONObject(photos_fb);

                JSONArray jsonArray = json.getJSONArray("data");
                Log.i("data", jsonArray.toString());
                Log.i("length", jsonArray.length() + "");
                int i = 0;
                try {

                    while (i < jsonArray.length()) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        fb_date_list.add("created time: "+c.getString("created_time"));

                    // fb_name_list.add(c.getString("name"));
                        fb_id_list.add("id: "+c.getString("id"));
                        i++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                //    View child = getLayoutInflater().inflate(R.layout.content_gallery, null);

                photoDetailAdapter itemsAdapter = new photoDetailAdapter(this, fb_date_list, fb_id_list);
                listView = (ListView) findViewById(R.id.fb_pic_list_view);


                listView.setAdapter(itemsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

    }
}
