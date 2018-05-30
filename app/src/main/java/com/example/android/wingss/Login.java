package com.example.android.wingss;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.Profile.getCurrentProfile;

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
    Button google_btn;
    LinearLayout layout;
    SQLiteDatabase database;
    ProgressBar pb;
    TextView pwd_check_view;

    ImageView view;
    ImageView view_con;
    private static int RC_SIGN_IN = 100;
    static GoogleSignInAccount account;
    int Total;
    public static GoogleSignInClient mGoogleSignInClient;
    public static boolean logged_in_from_facebook;
    public static boolean logged_in_from_app = false;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private CallbackManager callbackManager;
    Button fb;
    View.OnClickListener fbclicklistener = null;
    Intent intent;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        FacebookSdk.sdkInitialize(this);
        intent = new Intent(Login.this, launch.class);
        //google sign in

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null)
        {
            startActivity(intent);
            finish();
        }


        //gsign in part over
        if (logged_in_from_app) {

            startActivity(intent);
            finish();
        }
        //facebook sign in 

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        logged_in_from_facebook = accessToken != null && !accessToken.isExpired();
        if (logged_in_from_facebook) {
            startActivity(intent);
            finish();
        }

        fb = (Button) findViewById(R.id.facebook_sign);
        fbclicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (account != null)
                        Toast.makeText(Login.this, "You are already logged in through google", Toast.LENGTH_SHORT).show();
                    else {
                        LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "user_birthday", "user_photos", "user_videos"));

                    }

            }



        };
        fb.setOnClickListener(fbclicklistener);

        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        logged_in_from_facebook = true;
                        setFacebookData(loginResult);
                        getfromFb(loginResult);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "login failed due to unavoidable reasons", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login.this, "check internet connectivity", Toast.LENGTH_SHORT).show();
                    }
                });
        //fb sign in over
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.

        google_btn=(Button)findViewById(R.id.google);
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
        pwd_check_view=(TextView)dialogs.findViewById(R.id.pwd_view);
        view = (ImageView) dialogs.findViewById(R.id.view_pwd);
        view_con = (ImageView) dialogs.findViewById(R.id.view_con_pwd);


        database = new Dbhelper(this).getReadableDatabase();
        pb=(ProgressBar)dialogs.findViewById(R.id.pbar);
        ver_btn=(Button)findViewById(R.id.versatile1);
        pwd_d.setText(null);
        pwd_con_d.setText(null);
        mail_d.setText(null);
        name_d.setText(null);

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logged_in_from_facebook)
                    Toast.makeText(Login.this, "You are already logged in through facebook", Toast.LENGTH_SHORT).show();
                else
                    signInWithGoogle();
            }
        });
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
                    if (passdb.equals(log.getString(0))){
                        Toast.makeText(Login.this, "Login succesful", Toast.LENGTH_SHORT).show();
                        logged_in_from_app = true;
                        startActivity(intent);
                        finish();

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
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:
                        pwd_d.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        pwd_d.setInputType(InputType.TYPE_CLASS_TEXT);

                        break;

                }
                return true;
            }
        });
        view_con.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:
                        pwd_con_d.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        pwd_con_d.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                        break;

                }
                return true;
            }
        });


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialogs.show();
                Window window = dialogs.getWindow();


                assert window != null;
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sign_d.setBackgroundColor(getResources().getColor(R.color.dull,getTheme()));
        }








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
                }
                else {
                    if(name_d.getText().toString().charAt(0)!=32) {
                        if (pwd_d.getText().toString().length() > 0 && mail_d.getText().toString().length() > 0
                                && name_d.getText().toString().length() > 0 && pwd_con_d.getText().toString().length() > 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                sign_d.setBackgroundColor(getResources().getColor(R.color.btn, getTheme()));
                            }
                        }
                    }
                    else
                    {
                        name_d.setError("Invalid username");
                    }
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

                }
                else {

                    if(!validate(mail_d.getText().toString())) {
                        mail_d.setError("mail is not in proper format");
                    }


                }
                if(pwd_d.getText().toString().length()>0 && mail_d.getText().toString().length()>0
                        && name_d.getText().toString().length()>0 && pwd_con_d.getText().toString().length()>0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sign_d.setBackgroundColor(getResources().getColor(R.color.btn,getTheme()));
                    }
                }
            }
        });
        pwd_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check_pwd(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (pwd_d.getText().toString().length() == 0) {
                    pwd_d.setError("This field is mandatory");
                }
                if(pwd_d.getText().toString().length()>0 && mail_d.getText().toString().length()>0
                        && name_d.getText().toString().length()>0 && pwd_con_d.getText().toString().length()>0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sign_d.setBackgroundColor(getResources().getColor(R.color.btn,getTheme()));
                    }
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
                }
                if(pwd_d.getText().toString().length()>0 && mail_d.getText().toString().length()>0
                        && name_d.getText().toString().length()>0 && pwd_con_d.getText().toString().length()>0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sign_d.setBackgroundColor(getResources().getColor(R.color.btn,getTheme()));
                    }
                }
            }
        });


        sign_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               char mail_first= mail_d.getText().toString().charAt(0);
                char name_first= mail_d.getText().toString().charAt(0);



                if(pwd_d.getText().toString().length()>0
                        && mail_d.getText().toString().length()>0
                        && mail_first!=32 && name_first!=32
                        && name_d.getText().toString().length()>0
                        && pwd_con_d.getText().toString().length()>0) {
                   if(check()) {
                       if (pwd_d.getText().toString().equals(pwd_con_d.getText().toString())) {
                           if (Total >= 56) {
                                long row = saveToDB();
                               Toast.makeText(Login.this, "Successfully signed up as user " + row, Toast.LENGTH_LONG).show();
                               logged_in_from_app = true;
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                pwd_d.setError("password not strong enough");
                                pwd_con_d.setError("password not strong enough");
                            }



                        } else {
                            Toast.makeText(Login.this, "typed passwords do not match ", Toast.LENGTH_SHORT).show();
                            pwd_d.setError("Typed Passwords do not match");
                            pwd_con_d.setError("Typed Passwords do not match");

                        }
                    }
                   else {
                        mail_d.setError("The email-address is already been taken");
                    }
                }
                else
                {
                    mail_d.setError("Invalid mail");
                    name_d.setError("Invalid name");
                }



            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private long saveToDB() {
        SQLiteDatabase database = new Dbhelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Dbcontract.Dbentry.COLUMN_NAME, name_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_PWD, pwd_d.getText().toString());
        values.put(Dbcontract.Dbentry.COLUMN_MAIL, mail_d.getText().toString());

        return database.insert(Dbcontract.Dbentry.TABLE_NAME, null, values);

    }
    private Cursor readFromDB() {
        String mail = mail_edit.getText().toString();




        String[] projection = {

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

        cursor.moveToFirst();

//        cursor.close();
            return cursor;



    }
    private boolean check() {
        String mail_id=mail_d.getText().toString();
        String[] args={mail_id};
        Cursor curse=database.query(Dbcontract.Dbentry.TABLE_NAME,null,Dbcontract.Dbentry.COLUMN_MAIL + " like ? ",args,null,null,null);
        if(curse.getCount()>0)
            return false;
        return true;
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    public void check_pwd(String s) {


        String temp = s;

        int length = 0, uppercase = 0, lowercase = 0, digits = 0, symbols = 0, bonus = 0, requirements = 0;

        int lettersonly = 0, numbersonly = 0, cuc = 0, clc = 0;

        length = temp.length();
        for (int i = 0; i < temp.length(); i++) {
            if (Character.isUpperCase(temp.charAt(i)))
                uppercase++;
            else if (Character.isLowerCase(temp.charAt(i)))
                lowercase++;
            else if (Character.isDigit(temp.charAt(i)))
                digits++;

            symbols = length - uppercase - lowercase - digits;

        }

        for (int j = 1; j < temp.length() - 1; j++) {

            if (Character.isDigit(temp.charAt(j)))
                bonus++;

        }

        for (int k = 0; k < temp.length(); k++) {

            if (Character.isUpperCase(temp.charAt(k))) {
                k++;

                if (k < temp.length()) {

                    if (Character.isUpperCase(temp.charAt(k))) {

                        cuc++;
                        k--;

                    }

                }

            }

        }

        for (int l = 0; l < temp.length(); l++) {

            if (Character.isLowerCase(temp.charAt(l))) {
                l++;

                if (l < temp.length()) {

                    if (Character.isLowerCase(temp.charAt(l))) {

                        clc++;
                        l--;

                    }

                }

            }

        }

        System.out.println("length" + length);
        System.out.println("uppercase" + uppercase);
        System.out.println("lowercase" + lowercase);
        System.out.println("digits" + digits);
        System.out.println("symbols" + symbols);
        System.out.println("bonus" + bonus);
        System.out.println("cuc" + cuc);
        System.out.println("clc" + clc);

        if (length > 7) {
            requirements++;
        }

        if (uppercase > 0) {
            requirements++;
        }

        if (lowercase > 0) {
            requirements++;
        }

        if (digits > 0) {
            requirements++;
        }

        if (symbols > 0) {
            requirements++;
        }

        if (bonus > 0) {
            requirements++;
        }

        if (digits == 0 && symbols == 0) {
            lettersonly = 1;
        }

        if (lowercase == 0 && uppercase == 0 && symbols == 0) {
            numbersonly = 1;
        }

         Total = (length * 4) + ((length - uppercase) * 2)
                + ((length - lowercase) * 2) + (digits * 4) + (symbols * 6)
                + (bonus * 2) + (requirements * 2) - (lettersonly * length * 2)
                - (numbersonly * length * 3) - (cuc * 2) - (clc * 2);

        System.out.println("Total" + Total);

        if (Total < 30) {
            pb.setProgress(Total - 15);
            pwd_check_view.setText("Very Weak");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pwd_check_view.setTextColor(getResources().getColor(R.color.error,getTheme()));
            }
        } else if (Total >= 40 && Total < 50) {
            pb.setProgress(Total - 20);
            pwd_check_view.setText(" Weak");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pwd_check_view.setTextColor(getResources().getColor(R.color.colorAccent,getTheme()));
            }
        } else if (Total >= 56 && Total < 70) {
            pb.setProgress(Total - 25);
            pwd_check_view.setText("Strong");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pwd_check_view.setTextColor(getResources().getColor(R.color.btn,getTheme()));
            }
        } else if (Total >= 76) {
            pb.setProgress(Total - 30);
            pwd_check_view.setText("Very Strong");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pwd_check_view.setTextColor(getResources().getColor(R.color.colorPrimary,getTheme()));
            }
        } else {
            pb.setProgress(Total - 20);
            pwd_check_view.setText(" ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pwd_check_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark,getTheme()));
            }
        }
    }
    private void signInWithGoogle(){

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
           startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                startActivity(intent);
                finish();
            }
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        }
    }
    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            // String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            // String gender = response.getJSONObject().getString("gender");


                            sharedpreferences = getSharedPreferences("wingss", Context.MODE_PRIVATE);
                            editor = sharedpreferences.edit();
                            editor.putString("fb_f_name", firstName);
                            editor.putString("fb_l_name", lastName);
                            //     editor.putString("fb_gender", gender);
                            editor.commit();

                            Profile profile = getCurrentProfile();
                            //String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link", link);
                            if (getCurrentProfile() != null) {
                                Uri uri = getCurrentProfile().getProfilePictureUri(100, 100);

                                new DownloadImage().execute(uri.toString());
                                Log.i("Login", "ProfilePic" + uri);

                            }

                            //     Log.i("Login" + "Email", email);
                            Log.i("Login" + "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            //  Log.i("Login" + "Gender", gender);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        /*   GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday,photos,videos");
        request.setParameters(parameters);
        request.executeAsync();  */
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
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

            boolean is_saved = saveToInternalStorage(result);
            if (!is_saved) {
                Toast.makeText(Login.this, "Image not retreived", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    private boolean saveToInternalStorage(Bitmap bitmapImage) {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("profile.png", Context.MODE_PRIVATE);

            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    private void getfromFb(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),

                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here
                        Log.i("Response", response.toString());
                        try {
                            String birthdate = response.getJSONObject().getString("birthday");
                            String photos = response.getJSONObject().getString("photos");

                            sharedpreferences = getSharedPreferences("wingss", Context.MODE_PRIVATE);
                            editor = sharedpreferences.edit();

                            editor.putString("birthdate", birthdate).commit();
                            editor.putString("photos", photos).commit();
                            Log.i("birthday", birthdate);
                            Log.i("photographs", photos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday,photos,videos");
        request.setParameters(parameters);
        request.executeAsync();
    }


}

