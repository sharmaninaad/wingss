package com.example.android.wingss.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wingss.DbPackage.Dbcontract;
import com.example.android.wingss.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.Toast.makeText;
import static com.example.android.wingss.Activities.Login.database;
import static com.example.android.wingss.R.drawable.mail;

public class Launch extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String mCurrentPhotoPath;

    TextView name_text;
    ImageView imageView;
    Intent pro_fb_intent;
    String first_name;
    String last_name;
    private static final int REQUEST_CODE = 0;
    ImageView captureView;
    static final int REQUEST_TAKE_PHOTO = 1;
    String picUri;

    static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    static final int MY_PERMISSIONS_REQUEST_STORAGE = 4;
    static final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 5;


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
        captureView = (ImageView) findViewById(R.id.captured);
//EAACEdEose0cBAChJ6xImSegYzGUHWeF2BdeE8D1tIWiw07UZCvu7vZAQZCHlpQFDZC5BHfZBYHG6Ct4LZC8BMDU15KGJgDsZBYc8Td9IZAzo0ZCt1nUzpZAuOpoJS0gZB1uiZCbXTMwl0HiXbmsM1ZB8KhQZAq6Dazsc7WsiA7vV0LlZA3BqZABuBAt2YdtnK1ZAg36L7FgtSDob5FNBRtwZDZD
// Here, thisActivity is the current activity


        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);
            first_name = sharedPref.getString("fb_f_name", "");
            last_name = sharedPref.getString("fb_l_name", "");
            //Log.i("first name :",first_name);
            //Log.i("Last name :",last_name);
            name_text.setText(first_name + " " + last_name);
            if (ContextCompat.checkSelfPermission(Launch.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(Launch.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(Launch.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORAGE_READ);

                }
            } else
                loadImageFromStorage();


        } else {
            if (Login.logged_in_from_app) {

                if (ContextCompat.checkSelfPermission(Launch.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Launch.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {
                        ActivityCompat.requestPermissions(Launch.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_STORAGE_READ);

                    }
                } else {
                    loadProfilePic();
                }
                name_text.setText(readFromDB().getString(0));
            }
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
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Launch.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Launch.this,
                            Manifest.permission.CAMERA)) {

                    } else {
                        ActivityCompat.requestPermissions(Launch.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                } else {
                    doTakePictureIntent();
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

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORAGE_READ);

                }
            } else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
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
        } else if (Login.account != null) {
            Login.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Launch.this, Login.class));
                            finish();
                        }
                    });
        } else if (Login.logged_in_from_app) {
            Login.logged_in_from_app = false;
            SharedPreferences pref = getSharedPreferences("wingss", MODE_PRIVATE);
            pref.edit().putBoolean("isLoggedIn", false).commit();
            startActivity(new Intent(Launch.this, Login.class));
            finish();
        }

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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    Toast.makeText(this, "Permission to access camera denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.


            case MY_PERMISSIONS_REQUEST_STORAGE_READ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    return;
                } else {
                    Toast.makeText(this, "Permission to read from storage denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    return;

                } else {
                    Toast.makeText(this, "Permission to read from storage denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream stream = null;
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //   Bundle extras = data.getBundleExtra(EXTRA_OUTPUT);
            picUri = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
            if (picUri != null) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_STORAGE);

                    }
                } else {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(picUri));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                //Bitmap imageBitmap = (Bitmap) extras.get("data");
                ////captureView.setImageBitmap(imageBitmap);
            }
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {


                stream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);

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
    private void doTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.wingss",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }

            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("IOException:", ex.getMessage());
            }

        }
    }
    private void loadProfilePic() {


        String photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "image_" + Login.userId + ".png";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

        imageView.setImageBitmap(bitmap);

    }
    private Cursor readFromDB() {


        String[] projection = {

                Dbcontract.Dbentry.COLUMN_NAME


        };

        String selection =
                Dbcontract.Dbentry.COLUMN_MAIL + " like ? ";
        String[] selectionArgs = {Login.mail_user};
        Cursor cursor;
        cursor = database.query(
                Dbcontract.Dbentry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();

//        cursor.close();
        return cursor;


    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.i("directory", imageFileName);
        File image = null;

        if (ContextCompat.checkSelfPermission(Launch.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Launch.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(Launch.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE_READ);

            }
        } else {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpeg",         /* suffix */
                    storageDir      /* directory */
            );

            mCurrentPhotoPath = image.getAbsolutePath();
        }
        return image;

    }


}

