package com.example.android.wingss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements SettingListFragment.OnTabClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    @Override
    public void OnTabSelected(int position) {
        Toast.makeText(this, "item at " + position + "clicked", Toast.LENGTH_SHORT).show();

    }
}
