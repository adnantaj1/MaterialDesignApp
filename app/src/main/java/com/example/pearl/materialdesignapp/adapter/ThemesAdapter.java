package com.example.pearl.materialdesignapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pearl.materialdesignapp.activity.R;
import com.example.pearl.materialdesignapp.activity.ThemesActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pearl on 9/30/2015.
 */
public class ThemesAdapter extends ArrayAdapter {

    String arr[];
    public ThemesAdapter(Context context, int resource, String tit[]) {
        super(context, resource, tit);
        this.arr = tit;
    }
    static class ViewHolder{
        TextView title;
        TextView color;
    }

    @Override
    public void add(Object object) {
        super.add(object);

    }

    @Override
    public int getCount() {
        return this.arr.length;
    }

    @Override
    public Object getItem(int position) {
        return this.arr[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.theme_listitem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) row.findViewById(R.id.textView2);
            viewHolder.color = (TextView) row.findViewById(R.id.textView1);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) row.getTag();
        }

        String t = getItem(position).toString();
        viewHolder.title.setText(t);
        viewHolder.color.setBackgroundColor(Color.parseColor(ThemesActivity.activity.getColors(position)));
        //viewHolder.color.setText(t);
        return row;
    }
}
