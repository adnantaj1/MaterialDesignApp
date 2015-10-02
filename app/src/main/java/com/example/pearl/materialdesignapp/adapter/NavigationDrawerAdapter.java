package com.example.pearl.materialdesignapp.adapter;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pearl.materialdesignapp.R;
import com.example.pearl.materialdesignapp.activity.MainActivity;
import com.example.pearl.materialdesignapp.activity.ThemesActivity;
import com.example.pearl.materialdesignapp.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Pearl on 9/21/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    public static final int __TYPE_HEADER__ = 0x00;
    public static final int __TYPE_ITEM__ = 0x01;

    List<NavDrawerItem> data = Collections.EMPTY_LIST;
    private String home[];

    public NavigationDrawerAdapter(String home[]){

        this.home = home;
    }



    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType == __TYPE_ITEM__){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            MyViewHolder vhItem = new MyViewHolder(view, viewType);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.activity.closeDrawer();
                    if (v == parent.getChildAt(1)) {
                        MainActivity.activity.closeDrawer();
                        MainActivity.activity.homeScreen();
                    } else if (v == parent.getChildAt(2)) {
                        MainActivity.activity.closeDrawer();

                    } else if (v == parent.getChildAt(3)){
                        MainActivity.activity.launchThemeActivity();

                    }
                }
            });
            return vhItem;
        } else if (viewType == __TYPE_HEADER__){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            RelativeLayout r = (RelativeLayout) view.findViewById(R.id.header_relative_layout);
            r.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
            MyViewHolder vhHeader = new MyViewHolder(view,viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        if (viewHolder.holderId == 0x01) {
            viewHolder.textView.setText(home[position - 1]);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return __TYPE_HEADER__;
        return __TYPE_ITEM__;
    }

    private boolean isPositionHeader(int position) {
        return position == 0x00;
    }



    @Override
    public int getItemCount() {

        return home.length+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        int holderId;
        ImageView profile;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == __TYPE_ITEM__){
                textView = (TextView) itemView.findViewById(R.id.rowText);
                holderId = 0x01;
            } else {

                holderId = 0x00;
            }
        }
    }
}
