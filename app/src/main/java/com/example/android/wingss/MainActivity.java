package com.example.android.wingss;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import static android.R.attr.allContactsName;
import static android.R.attr.start;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity {
   // ListView list;
    TextView upgrade;




    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "welcome to the activity , activity starts", Toast.LENGTH_SHORT).show();
        Log.i("activity state","started");}

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("activity state","stopped");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.i("activity state","destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("activity state","paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Toast.makeText(this, "activity has resumed", Toast.LENGTH_SHORT).show();
        Log.i("activity state","resumed");

    }
    Button button;


    String[] texts = {
        "Weekly plan",
            "Monthly Plan",
            "Annual Plan",
            "Lifetime Plan"
    } ;
    Integer[] iconId = {
        R.drawable.week,
            R.drawable.month,
            R.drawable.year,
            R.drawable.lifetime
    };
    DialogInterface.OnClickListener listener;
    AlertDialog.Builder alertDialogBuilder;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        final mylist listAdapter = new
                mylist(MainActivity.this, texts, iconId);
        button=(Button)findViewById(R.id.edit);
    //alertDialogBuilder= new AlertDialog.Builder(this);
       /* listener=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==BUTTON_POSITIVE)
                {
                    Intent editIntent=new Intent(MainActivity.this,Edit_profile.class);
                startActivity(editIntent);
                }
                if(which==BUTTON_NEGATIVE)
                {
                    dialog.dismiss();

                }
            }
        };
        alertDialogBuilder.setPositiveButton("Sure",
                listener);
        alertDialogBuilder.setNegativeButton("No Please",
                listener);
        alertDialogBuilder.setCancelable(true);
        //alertDialogBuilder.setMessage("Are you sure?");
        LayoutInflater inflater = getLayoutInflater();
        view=inflater.inflate(R.layout.dialog, null);
*/
        upgrade=(TextView)findViewById(R.id.Up);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialoglist);
                dialog.setTitle("Select from the above plans");
                ListView listView=(ListView)dialog.findViewById(R.id.thelist);
                listView.setAdapter(listAdapter);
                dialog.show();

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
}
