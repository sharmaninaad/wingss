package com.example.android.wingss.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.wingss.Fragments.AboutFragment;
import com.example.android.wingss.Fragments.AccountFragment;
import com.example.android.wingss.Fragments.LanguageFragment;
import com.example.android.wingss.Fragments.NotificationFragment;
import com.example.android.wingss.R;

public class Settingss extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingss);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout.openDrawer(GravityCompat.START);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_language) {

            LanguageFragment languageFragment = new LanguageFragment();
            replaceFragment(languageFragment);
        } else if (id == R.id.nav_notification) {

            NotificationFragment notificationFragment = new NotificationFragment();
            replaceFragment(notificationFragment);

        } else if (id == R.id.nav_account) {

            AccountFragment accountFragment = new AccountFragment();
            replaceFragment(accountFragment);
        } else if (id == R.id.nav_about) {

            AboutFragment aboutFragment = new AboutFragment();
            replaceFragment(aboutFragment);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
    public void replaceFragment(Fragment destFragment) {

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_settings, destFragment);
       fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }

    }




