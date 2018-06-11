package com.example.android.wingss.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.wingss.R;


/**
 *
 * Created by Ninaad on 6/6/2018.
 */


public class CreditFragment extends Fragment {
    Button button;
    EditText numberEdit;
    EditText expiryEdit;
    EditText cvvEdit;
    String number;
    String expiry;
    String cvv;
    int pos;
    int amount;
    SharedPreferences creditPreferences;
    public CreditFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //called after onCreate and before onActivityCreated
        Log.i(" cycle", "oncreateView");
        View rootView = inflater.inflate(R.layout.layout_credit_fragment, container, false);
        button= (Button) rootView.findViewById(R.id.pay_btn);
        numberEdit= (EditText) rootView.findViewById(R.id.number_card_edit);
        expiryEdit= (EditText) rootView.findViewById(R.id.date_expiry_edit);
        cvvEdit = (EditText) rootView.findViewById(R.id.cvv_edit);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(" cycle", "attached");
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(" cycle", "oncreate");
        super.onCreate(savedInstanceState);
//called after onAttach and before onCreateView
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(" cycle", "ActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Spinner spinner= (Spinner) getActivity().findViewById(R.id.duration_upgrade);
        pos = spinner.getSelectedItemPosition();
        if(numberEdit.getText()!=null &&cvvEdit.getText()!=null&&expiryEdit.getText()!=null) {
            switch (pos) {
                case 0:
                    button.setText("proceed to pay $10");
                    amount = 10;
                    break;
                case 1:
                    button.setText("proceed to pay $30");
                    amount = 10;
                    break;
                case 2:
                    button.setText("proceed to pay $300");
                    amount = 10;
                    break;
                case 3:
                    button.setText("proceed to pay $600");
                    amount = 10;
                    break;
                default:
                    Toast.makeText(getActivity(), "Please select a plan", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onStop() {
        super.onStop();
        Log.i(" cycle", "stopped");
        creditPreferences = getActivity().getSharedPreferences("wingss", Context.MODE_PRIVATE);

        number = numberEdit.getText().toString();
        cvv = cvvEdit.getText().toString();
        expiry = expiryEdit.getText().toString();

        creditPreferences.edit().putString("numberCard", number).commit();
        creditPreferences.edit().putString("cvvCard", cvv).commit();
        creditPreferences.edit().putString("expiryCard", expiry).commit();
        Log.i("posss", creditPreferences.getInt("position", 0) + "");
        Log.i("pref", creditPreferences.getString("cvvCard", ""));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(" cycle", "started");

        if (!(numberEdit.getText().toString().equals("") && cvvEdit.getText().toString().equals("") && expiryEdit.getText().toString().equals(""))) {
            switch (pos) {

                case 0:
                    button.setText("proceed to pay $10");
                    amount = 10;
                    break;
                case 1:
                    button.setText("proceed to pay $30");
                    amount = 10;
                    break;
                case 2:
                    button.setText("proceed to pay $300");
                    amount = 10;
                    break;
                case 3:
                    button.setText("proceed to pay $600");
                    amount = 10;
                    break;
                default:
                    Toast.makeText(getActivity(), "Please select a plan", Toast.LENGTH_SHORT).show();
            }
        }
        numberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (numberEdit.getText().toString().equals("")) {
                    numberEdit.setError("This field can not be left empty");
                }
            }
        });
        cvvEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cvvEdit.getText().toString().equals("")) {
                    cvvEdit.setError("This field can not be left empty");
                }
            }
        });
        expiryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (expiryEdit.getText().toString().equals("")) {
                    expiryEdit.setError("This field can not be left empty");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(" cycle", "resumed");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("wingss", Context.MODE_PRIVATE);
        numberEdit.setText(sharedPreferences.getString("numberCard", ""));
        cvvEdit.setText(sharedPreferences.getString("cvvCard", ""));
        expiryEdit.setText(sharedPreferences.getString("expiryCard", ""));

    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(amount + "$ will be deducted from your account")
                .setPositiveButton("Procced", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        notifyUser();
                    }
                })
                .setNegativeButton("Go Baack", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void notifyUser() {
        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.cameraa)
                .setContentTitle("WINGSS")
                .setContentText(amount + "$ have been deducted from your card")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(4, mBuilder.build());

    }
}
