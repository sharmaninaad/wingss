package com.example.android.wingss;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




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
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
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
        layout=(LinearLayout)dialogs.findViewById(R.id.lays);

        ver_btn=(Button)findViewById(R.id.versatile1);
        pwd_d.setText(null);
        pwd_con_d.setText(null);
        mail_d.setText(null);
        name_d.setText(null);

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
                    Cursor log=readFromDB();
                if(log!=null && log.getCount()>0) {
                    if (passdb.equals(log.getString(1))) {
                        Toast.makeText(Login.this, "Login succesful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));

                    } else {
                        pwd_edit.setText("");
                        Toast.makeText(Login.this, "Please enter the right password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "No such username exists", Toast.LENGTH_SHORT).show();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sign_d.setBackgroundColor(getResources().getColor(R.color.dull,getTheme()));
        }



        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd_d.getText().toString().length()>0 && mail_d.getText().toString().length()>0
                        && name_d.getText().toString().length()>0 && pwd_con_d.getText().toString().length()>0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sign_d.setBackgroundColor(getResources().getColor(R.color.btn,getTheme()));
                    }
                }
            }
        });



        sign_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pwd_d.getText().toString().length()>0 && mail_d.getText().toString().length()>0
                        && name_d.getText().toString().length()>0 && pwd_con_d.getText().toString().length()>0) {
                    if (pwd_d.getText().toString().equals(pwd_con_d.getText().toString())) {
                        long row=saveToDB();
                        pwd_d.setText(null);
                        pwd_con_d.setText(null);
                        mail_d.setText(null);
                        name_d.setText(null);
                        Toast.makeText(Login.this, "Succesfully signed up as user " + row, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Login.this, "typed passwords do not match", Toast.LENGTH_SHORT).show();
                        pwd_d.setError("Typed Passwords do not match");
                        pwd_con_d.setError("Typed Passwords do not match");

                    }
                }



                }

        });

        name_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (name_d.getText().toString().length() == 0) {
                    name_d.setError("This field is mandatory");
                    Toast.makeText(Login.this, "Please enter the complete information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mail_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mail_d.getText().toString().length() == 0) {
                    mail_d.setError("This field is mandatory");
                    Toast.makeText(Login.this, "Please enter the complete information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pwd_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (pwd_d.getText().toString().length() == 0) {
                    pwd_d.setError("This field is mandatory");
                    Toast.makeText(Login.this, "Please enter the complete information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pwd_con_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (pwd_con_d.getText().toString().length() == 0) {
                    pwd_con_d.setError("This field is mandatory");
                    Toast.makeText(Login.this, "Please enter the complete information", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private long saveToDB() {
        SQLiteDatabase database = new Dbhelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Dbcontract.Dbentry.COLUMN_NAME, name_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_PWD, pwd_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_MAIL, mail_d.getText().toString());



        long newRowId = database.insert(Dbcontract.Dbentry.TABLE_NAME, null, values);
        return newRowId;
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
