package com.example.android.wingss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.wingss.Adapters.MyListAdapter;

/**
 * Created by Ninaad on 6/7/2018.
 */

public class SettingListFragment extends Fragment {
    String[] text_settings = {
            "Languages",
            "Notification Settings",
            "Account Settings",
            "About"
    };
    Integer[] icon_settings = {
            R.drawable.language,
            R.drawable.notiff,
            R.drawable.user,
            R.drawable.about

    };
    OnTabClickListener mCallback;

    interface OnTabClickListener {
        void OnTabSelected(int position);
    }

    public SettingListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnTabClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View setting_view = inflater.inflate(R.layout.layout_setting_list_fragment, container, false);
        ListView listView = (ListView) setting_view.findViewById(R.id.sett_list);
        MyListAdapter myListAdapter = new MyListAdapter(getActivity(), text_settings, icon_settings);
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.OnTabSelected(position);
            }
        });
        return setting_view;
    }

}
