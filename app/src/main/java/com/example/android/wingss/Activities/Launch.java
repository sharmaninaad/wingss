package com.example.android.wingss.Activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wingss.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.widget.Toast.makeText;

public class Launch extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name_text;
    ImageView imageView;
    Intent pro_fb_intent;
    String first_name;
    String last_name;
    String gender;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        name_text = (TextView) headerLayout.findViewById(R.id.nametxt);
        imageView = (ImageView) headerLayout.findViewById(R.id.profile_pic);
        pro_fb_intent = new Intent(Launch.this, ProfileActivity.class);
//EAACEdEose0cBAChJ6xImSegYzGUHWeF2BdeE8D1tIWiw07UZCvu7vZAQZCHlpQFDZC5BHfZBYHG6Ct4LZC8BMDU15KGJgDsZBYc8Td9IZAzo0ZCt1nUzpZAuOpoJS0gZB1uiZCbXTMwl0HiXbmsM1ZB8KhQZAq6Dazsc7WsiA7vV0LlZA3BqZABuBAt2YdtnK1ZAg36L7FgtSDob5FNBRtwZDZD

        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);
            first_name = sharedPref.getString("fb_f_name", "");
            last_name = sharedPref.getString("fb_l_name", "");
            //Log.i("first name :",first_name);
            //Log.i("Last name :",last_name);
            name_text.setText(first_name + " " + last_name);
            loadImageFromStorage();


        } else {
            name_text.setText("");
            imageView.setImageDrawable(null);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton capture = (FloatingActionButton) findViewById(R.id.camera);

        // capture.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //     public void onClick(View v) {
        //       startActivity(new Intent(Launch.this, MakePhoto.class));
        //    }
        //  });
        fab.setImageResource(R.drawable.refers);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viefaw) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey ! I amusing this app , it has awesome features , you also try it");
                try {
                    startActivity(whatsappIntent);
                    sendCustomNotification();
                    sendCustomNotification1();
                } catch (android.content.ActivityNotFoundException ex) {
                    makeText(Launch.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launch, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(Launch.this, Gallery.class));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_profile) {


            startActivity(pro_fb_intent);


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sendNotification(View view) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        //Create the intent that’ll fire when the user taps the notification//


        mBuilder.setSmallIcon(R.drawable.cameraa);
        mBuilder.setContentTitle("Wings");
        mBuilder.setContentText("Whatsapp is active !! send your referal");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }
    public void sendCustomNotification() {
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setImageViewResource(R.id.image, R.drawable.cameraa);
        contentView.setTextViewText(R.id.title, "Whats app in use");
        contentView.setTextViewText(R.id.text, "Wingss is currently using whats app");
        NotificationCompat.Builder mbuild = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContent(contentView);
        Notification notify = mbuild.build();
        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, notify);


    }
    public void sendCustomNotification1() {
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setImageViewResource(R.id.image, R.drawable.cameraa);
        contentView.setTextViewText(R.id.title, "Whats app in use");
        contentView.setTextViewText(R.id.text, "Wingss is currently using whats app");
        NotificationCompat.Builder mbuild = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContent(contentView);
        Notification notify = mbuild.build();
        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(002, notify);


    }
    private void signOut() {
        if (Login.logged_in_from_facebook) {
            name_text.setText("");
            imageView.setImageBitmap(null);
            Login.logged_in_from_facebook = false;
            LoginManager.getInstance().logOut();
            startActivity(new Intent(Launch.this, Login.class));
            finish();
        } else if (Login.account != null)
            Login.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Launch.this, Login.class));
                            finish();
                        }
                    });


    }

    private void loadImageFromStorage() {

        try {
            File filePath = getApplicationContext().getFileStreamPath("profile.png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(filePath));
            imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                // recyle unused bitmaps

                stream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                // imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {

                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

}