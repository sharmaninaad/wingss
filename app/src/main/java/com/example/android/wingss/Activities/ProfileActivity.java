package com.example.android.wingss.Activities;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wingss.Adapters.MyListAdapter;
import com.example.android.wingss.CreditFragment;
import com.example.android.wingss.DbPackage.Dbcontract;
import com.example.android.wingss.FormUpgrade;
import com.example.android.wingss.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.android.wingss.Activities.Login.database;

public class ProfileActivity extends AppCompatActivity {
    TextView upgrade;
    TextView name_view;
    TextView phone;
    ImageView profile_img;
    Button button;
    EditText namee;
    EditText number;
    static final int MY_PERMISSIONS_REQUEST_READ_APP = 9;
    static final int MY_PERMISSIONS_REQUEST_READ_FACEBOOK = 10;

    @Override
    protected void onResume() {
        super.onResume();

        if (Login.logged_in_from_app) {

            loadProfilePic();
            name_view.setText(readFromDB().getString(0));

        } else if (Login.logged_in_from_facebook) {
            loadImageFromStorage();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_profile);

        name_view = (TextView) findViewById(R.id.name);
        profile_img = (ImageView) findViewById(R.id.img_pro);
        phone = (TextView) findViewById(R.id.number);
//        radioGroup.addOnAttachStateChangeListener();


        if (Login.logged_in_from_app) {

            loadProfilePic();
            name_view.setText(readFromDB().getString(0));


        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask

        button=(Button)findViewById(R.id.edit);
        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);


            name_view.setText(sharedPref.getString("fb_f_name", "") + " " + sharedPref.getString("fb_l_name", ""));

            loadImageFromStorage();

        } else {
            name_view.setText("");
            profile_img.setImageDrawable(null);
        }

        upgrade=(TextView)findViewById(R.id.Up);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FormUpgrade.class));
            }
        });
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Login.logged_in_from_app)
                    startActivity(new Intent(ProfileActivity.this, MakePhoto.class));


                else
                    Toast.makeText(ProfileActivity.this, "Cannot change profile pic , You are logged in from external sources", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Snackbar snack=new Snackbar(findViewById(R.id.myCoordinatorLayout), "feature currently unavailable",
                        Snackbar.LENGTH_SHORT);
                        snackbar.show();*/
                //startActivity(new Intent(ProfileActivity.this,popup_edit.class));
              /*

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            */
                final Dialog dialog = new Dialog(ProfileActivity.this);

                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Edit Profile");


                namee = (EditText) dialog.findViewById(R.id.newname);
                number = (EditText) dialog.findViewById(R.id.number);




                if (Login.logged_in_from_app) {
                    dialog.show();
                } else {
                    if (Login.logged_in_from_facebook)
                        Toast.makeText(ProfileActivity.this, "Feature unavailable , You are logged in from facebook", Toast.LENGTH_SHORT).show();
                    if (Login.account != null)
                        Toast.makeText(ProfileActivity.this, "Feature unavailable , You are logged in from google", Toast.LENGTH_SHORT).show();
                }

                Button savebtn = (Button) dialog.findViewById(R.id.save);
                Button backbtn = (Button) dialog.findViewById(R.id.back);

                savebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (namee.getText() != null)
                            name_view.setText(namee.getText().toString());
                        if (number.getText().toString().length() == 10)
                            phone.setText(number.getText().toString());

                        dialog.dismiss();

                    }
                });
                backbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

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
    private void loadImageFromStorage() {
        try {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(ProfileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_FACEBOOK);

                }
            } else {
            File filePath = getApplicationContext().getFileStreamPath("profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(filePath));
                profile_img.setImageBitmap(b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadProfilePic() {

        if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_APP);

            }
        } else {
            String photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "image_" + Login.userId + ".png";

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

            profile_img.setImageBitmap(bitmap);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_APP: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadProfilePic();
                    return;
                } else {
                    Toast.makeText(this, "Permission to access camera denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_FACEBOOK: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImageFromStorage();
                    return;
                } else {
                    Toast.makeText(this, "Permission to access camera denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


}
