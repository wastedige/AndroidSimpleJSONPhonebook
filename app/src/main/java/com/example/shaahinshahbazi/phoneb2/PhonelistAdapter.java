package com.example.shaahinshahbazi.phoneb2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shaahinshahbazi on 11/7/15.
 */
public class PhonelistAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;
    private final String[] images;
    private final String[] workphones;

    public PhonelistAdapter(Activity context, String[] names, String[] workphones, String[] images) {
        super(context, R.layout.list_item, names);
        this.context = context;
        this.names = names;
        this.images = images;
        this.workphones = workphones;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)  context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView workphone = (TextView) rowView.findViewById(R.id.workphone);
        ImageView image = (ImageView) rowView.findViewById(R.id.img);

        name.setText(names[pos]);
        workphone.setText(workphones[pos]);

        new DownloadImageTask((ImageView) rowView.findViewById(R.id.img))
                .execute(images[pos]);

        return rowView;
    }
}
