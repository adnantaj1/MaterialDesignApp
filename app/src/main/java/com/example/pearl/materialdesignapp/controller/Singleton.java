package com.example.pearl.materialdesignapp.controller;

/**
 * Created by Pearl on 10/7/2015.
 */
public class Singleton {

    private static Singleton sInstance = null;
    public static final int VALUE0 = 0;
    public static final int VALUE1 = 1;
    private int selectedValue = VALUE0;

    public static final String ActivityMain = null;
    public static final String ActivityThemes = null;
    public static final String ActivityProfile = null;
    public static final boolean Drawer = false;
    private  boolean drawerState = Drawer;
    private String activityName = ActivityMain;

    public Singleton(){

    }

    /**
     *
     * @return single Instance
     */
    public static Singleton getInstance(){
        synchronized (Singleton.class){
            if (sInstance == null){
                sInstance = new Singleton();
            }
            return sInstance;
        }
    }

    public void setSelectedValue(int selectedValue){
        this.selectedValue = selectedValue;
    }

    public int getSelectedValue(){
        return selectedValue;
    }

    public String getActivityName(){
        return activityName;
    }
    public void setActivityName(String activityName){
        this.activityName = activityName;
    }
    public boolean getDrawerState(){
        return drawerState;
    }
    public void setDrawerState(boolean drawerState){
        this.drawerState = drawerState;
    }

}//** end of class
