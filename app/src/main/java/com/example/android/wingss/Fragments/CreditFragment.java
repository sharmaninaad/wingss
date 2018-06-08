package com.example.android.wingss.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public CreditFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_credit_fragment, container, false);
        button= (Button) rootView.findViewById(R.id.pay_btn);
        numberEdit= (EditText) rootView.findViewById(R.id.number_card_edit);
        expiryEdit= (EditText) rootView.findViewById(R.id.date_expiry_edit);
        cvvEdit=(EditText)rootView.findViewById(R.id.cvv_edit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Spinner spinner= (Spinner) getActivity().findViewById(R.id.duration_upgrade);
        int pos=spinner.getSelectedItemPosition();
        if(numberEdit.getText()!=null &&cvvEdit.getText()!=null&&expiryEdit.getText()!=null) {
            switch (pos) {
                case 0:
                    button.setText("proceed to pay $10");
                    break;
                case 1:
                    button.setText("proceed to pay $30");
                    break;
                case 2:
                    button.setText("proceed to pay $300");
                    break;
                case 3:
                    button.setText("proceed to pay $600");
                    break;
                default:
                    Toast.makeText(getActivity(), "Please select a plan", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if (numberEdit.getText() == null)
            {
                numberEdit.setError("can not be left empty");
            }
            if (cvvEdit.getText() == null)
            {
                cvvEdit.setError("can not be left empty");
            }
            if (expiryEdit.getText() == null)
            {
                expiryEdit.setError("can not be left empty");
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
