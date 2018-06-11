package com.example.android.wingss.Activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.android.wingss.Adapters.MyListAdapter;
import com.example.android.wingss.Fragments.BankFragment;
import com.example.android.wingss.Fragments.CreditFragment;
import com.example.android.wingss.R;

public class FormUpgrade extends AppCompatActivity {
    MyListAdapter listAdapter;
    Spinner spinner;
    String[] texts = {
            "Weekly plan   ($10)",
            "Monthly Plan  ($30)",
            "Annual Plan   ($300)",
            "Lifetime Plan ($600)"
    };
    Integer[] iconId =
            {
                    R.drawable.week,
                    R.drawable.month,
                    R.drawable.year,
                    R.drawable.lifetime
            };
    RadioButton credit_radio;
    RadioButton bank_radio;
    RadioGroup radioGroup;
    CreditFragment fragment;
    BankFragment bankFragment;


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preff = getSharedPreferences("wingss", MODE_PRIVATE);

        preff.edit().putInt("position", spinner.getSelectedItemPosition()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefff = getSharedPreferences("wingss", MODE_PRIVATE);

        spinner.setSelection(prefff.getInt("position", 0));
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefff = getSharedPreferences("wingss", MODE_PRIVATE);

        spinner.setSelection(prefff.getInt("position", 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_upgrade);
        spinner = (Spinner) findViewById(R.id.duration_upgrade);

        listAdapter = new
                MyListAdapter(FormUpgrade.this, texts, iconId);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(listAdapter);


        SharedPreferences prefff = getSharedPreferences("wingss", MODE_PRIVATE);

        spinner.setSelection(prefff.getInt("position", 0));
        radioGroup = (RadioGroup) findViewById(R.id.pay_group);
        credit_radio = (RadioButton) findViewById(R.id.credit);
        bank_radio = (RadioButton) findViewById(R.id.bank);
        fragment = new CreditFragment();
        bankFragment = new BankFragment();

        credit_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragment);
            }
        });

        bank_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(bankFragment);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.credit) {

                    replaceFragment(fragment);

                } else if (checkedId == R.id.bank) {
                    replaceFragment(bankFragment);
                }
            }
        });

    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, destFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }

}
