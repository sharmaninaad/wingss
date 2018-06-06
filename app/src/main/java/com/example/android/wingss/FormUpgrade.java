package com.example.android.wingss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.android.wingss.Activities.ProfileActivity;
import com.example.android.wingss.Adapters.MyListAdapter;

public class FormUpgrade extends AppCompatActivity {
    MyListAdapter listAdapter;
    Spinner spinner;
    String[] texts = {
            "Weekly plan",
            "Monthly Plan",
            "Annual Plan",
            "Lifetime Plan"
    };
    Integer[] iconId =
            {
                    R.drawable.week,
                    R.drawable.month,
                    R.drawable.year,
                    R.drawable.lifetime
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_upgrade);
        spinner = (Spinner) findViewById(R.id.duration_upgrade);

        listAdapter = new
                MyListAdapter(FormUpgrade.this, texts, iconId);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(listAdapter);

    }

}
