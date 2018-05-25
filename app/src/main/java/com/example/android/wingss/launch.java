package com.example.android.wingss;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.widget.Toast.makeText;
import static com.example.android.wingss.Login.logged_in_from_facebook;
import static com.example.android.wingss.Login.mGoogleSignInClient;
import static com.example.android.wingss.R.drawable.mail;
import static com.example.android.wingss.R.id.fab;
import static java.security.AccessController.getContext;

public class launch extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String first_name = null;
    String last_name = null;
    String pic_uri = null;
    String email_id = null;
    TextView name_text;
    ImageView imageView;
    Uri image_uri;
    View child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent launch_intent = getIntent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        child = getLayoutInflater().inflate(R.layout.activity_main, null);

        name_text = (TextView) headerLayout.findViewById(R.id.nametxt);
        imageView = (ImageView) headerLayout.findViewById(R.id.profile_pic);

        if (Login.logged_in_from_facebook) {
            first_name = launch_intent.getStringExtra("f_name");
            last_name = launch_intent.getStringExtra("l_name");

            Log.i("received name", first_name + " " + last_name);
            name_text.setText("" + first_name + " " + last_name);
            TextView tt_pro = (TextView) child.findViewById(R.id.name);
            tt_pro.setText("" + first_name + " " + last_name);

            pic_uri = launch_intent.getStringExtra("imageUri");

            if (pic_uri != null) {
                Log.i("received uri", pic_uri);
                image_uri = Uri.parse(pic_uri);

                new DownloadImage().execute(image_uri.toString());


            }

        }
        /*first_name = launch_intent.getStringExtra("f_name");
        last_name = launch_intent.getStringExtra("l_name");
        email_id=launch_intent.getStringExtra("mail");
        ;*/

        //Uri image_uri=  launch_intent.getParcelableExtra("imageUri");
/*            Uri img_uri = Uri.parse(image_uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),img_uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
*/


        //name_text.setText("Welcome "+first_name+" "+last_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                    makeText(launch.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_profile) {
            Intent pro_intent = new Intent(launch.this, MainActivity.class);

            startActivity(pro_intent);
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
            LoginManager.getInstance().logOut();
            startActivity(new Intent(launch.this, Login.class));
            finish();
        } else if (Login.account != null)
            Login.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(launch.this, Login.class));
                            finish();
                        }
                    });


    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imageView.setImageBitmap(result);
            ImageView pro_view = (ImageView) child.findViewById(R.id.img_pro);

            pro_view.setImageBitmap(result);

            // Close progressdialog
        }
    }
}