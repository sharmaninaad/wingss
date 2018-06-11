package com.example.android.wingss.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.wingss.R;

/**
 *
 * Created by Ninaad on 5/14/2018.
 */

public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] texts;
    private final Integer[] iconId;

    public MyListAdapter(Activity context,
                         String[] texts, Integer[] iconId) {
        super(context, R.layout.list_single, texts);
        this.context = context;
        this.texts = texts;
        this.iconId = iconId;
    }
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(texts[position]);
        imageView.setImageResource(iconId[position]);
        return rowView;
    }
}

 
