package com.example.android.wingss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.wingss.Activities.AboutFragment;
import com.example.android.wingss.Activities.AccountFragment;
import com.example.android.wingss.Activities.LanguageFragment;
import com.example.android.wingss.Activities.NotificationFragment;

public class Settings extends AppCompatActivity implements SettingListFragment.OnTabClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    @Override
    public void OnTabSelected(int position) {
        Toast.makeText(this, "item at " + position + "clicked", Toast.LENGTH_SHORT).show();
        if (findViewById(R.id.detail_setting) != null) {
            switch (position) {
                case 0:
                case 1:
                case 2:

                case 3:
                default:
                    break;


            }
        }


    }


}
