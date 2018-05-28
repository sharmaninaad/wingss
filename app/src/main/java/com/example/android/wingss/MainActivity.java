package com.example.android.wingss;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.android.wingss.Login.account;
import static com.example.android.wingss.Login.logged_in_from_app;

public class MainActivity extends AppCompatActivity {
   // ListView list;
    TextView upgrade;
    TextView name_view;
    ImageView profile_img;
    Button button;

    String[] texts = {
        "Weekly plan",
            "Monthly Plan",
            "Annual Plan",
            "Lifetime Plan"
    } ;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        name_view = (TextView) findViewById(R.id.name);
        profile_img = (ImageView) findViewById(R.id.img_pro);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        final mylist listAdapter = new
                mylist(MainActivity.this, texts, iconId);
        button=(Button)findViewById(R.id.edit);
        if (Login.logged_in_from_facebook) {

            SharedPreferences sharedPref = getSharedPreferences("wingss", Context.MODE_PRIVATE);


            name_view.setText(sharedPref.getString("fb_f_name", "") + " " + sharedPref.getString("fb_l_name", ""));
            loadImageFromStorage(Login.path_fb_image);

        } else {
            name_view.setText("");
            profile_img.setImageDrawable(null);
        }

        upgrade=(TextView)findViewById(R.id.Up);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialoglist);
                dialog.setTitle("Select from the above plans");
                ListView listView=(ListView)dialog.findViewById(R.id.thelist);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, "item at "+position+" clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                if (Login.logged_in_from_app) {
                    dialog.show();
                } else {
                    if (Login.logged_in_from_facebook)
                        Toast.makeText(MainActivity.this, "Feature unavailable , You are logged in from facebook", Toast.LENGTH_SHORT).show();
                    if (Login.account != null)
                        Toast.makeText(MainActivity.this, "Feature unavailable , You are logged in from google", Toast.LENGTH_SHORT).show();
                }


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Snackbar snack=new Sncakbar(findViewById(R.id.myCoordinatorLayout), "feature currently unavailable",
                        Snackbar.LENGTH_SHORT);
                        snackbar.show();*/
            //startActivity(new Intent(MainActivity.this,popup_edit.class));
              /*

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            */
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Edit Profile");


                EditText namee = (EditText) dialog.findViewById(R.id.newname);
                EditText numb=(EditText)dialog.findViewById(R.id.number);

                ImageView image = (ImageView) dialog.findViewById(R.id.circle);


                dialog.show();

                Button savebtn = (Button) dialog.findViewById(R.id.save);
                Button backbtn = (Button) dialog.findViewById(R.id.back);
                // if decline button is clicked, close the custom dialog
                savebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
        //list=(ListView)findViewById(R.id.mmlist);
        //list.setAdapter(listAdapter);
    }

    private void loadImageFromStorage(String path) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            profile_img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
