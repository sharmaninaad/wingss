package com.example.android.wingss;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class Login extends AppCompatActivity {
    EditText pwd_edit;
    EditText mail_edit;
    TextView sign;
    EditText mail_d;
    EditText pwd_d;
    EditText name_d;
    EditText pwd_con_d;
    Button sign_d;
    Button all_btn;
    Button ver_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.


        all_btn=(Button)findViewById(R.id.all);
        mail_edit=(EditText)findViewById(R.id.mail1);
        pwd_edit=(EditText) findViewById(R.id.pwd1);
        sign=(TextView)findViewById(R.id.signup);

        final Dialog dialogs = new Dialog(Login.this);

        dialogs.setContentView(R.layout.dialog_signup);
        dialogs.setTitle("Create an account");

        mail_d= (EditText) dialogs.findViewById(R.id.mail_dialog);
        name_d= (EditText) dialogs.findViewById(R.id.name_dialog);
        pwd_d= (EditText) dialogs.findViewById(R.id.pwd_dialog);
        pwd_con_d= (EditText) dialogs.findViewById(R.id.pwd_con_dialog);
        sign_d= (Button) dialogs.findViewById(R.id.signup_dialog);


        ver_btn=(Button)findViewById(R.id.versatile1);

        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,list_database.class));
            }
        });
        ver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String passdb=pwd_edit.getText().toString();
                    if(passdb.equals(readFromDB().getString(1)))
                    {
                        Toast.makeText(Login.this, "Login succesful", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(Login.this,MainActivity.class));

                    }
                    else
                    {
                        pwd_edit.setText("");
                        Toast.makeText(Login.this, "Please enter the right password", Toast.LENGTH_SHORT).show();
                    }
                    
                }

        });
        sign_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pwd_d.getText().toString().equals(pwd_con_d.getText().toString()))
                {
                    saveToDB();
                    pwd_d.setText("");
                    pwd_con_d.setText("");
                    mail_d.setText("");
                    name_d.setText("");
                }
                else
                {
                    Toast.makeText(Login.this, "typed passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialogs.show();
                Window window = dialogs.getWindow();

                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            }
        });


    }
    private void saveToDB() {
        SQLiteDatabase database = new Dbhelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Dbcontract.Dbentry.COLUMN_NAME, name_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_PWD, pwd_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_MAIL, mail_d.getText().toString());



        long newRowId = database.insert(Dbcontract.Dbentry.TABLE_NAME, null, values);

        Toast.makeText(this, "Succesfully signed up as user " + newRowId, Toast.LENGTH_LONG).show();
    }
    private Cursor readFromDB() {
        String mail = mail_edit.getText().toString();



        SQLiteDatabase database = new Dbhelper(this).getReadableDatabase();

        String[] projection = {
                Dbcontract.Dbentry.COLUMN_NAME,
                Dbcontract.Dbentry.COLUMN_PWD

        };

        String selection =
                Dbcontract.Dbentry.COLUMN_MAIL + " like ? " ;
        String[] selectionArgs = { mail};
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



//        cursor.close();
            return cursor;



    }

}
