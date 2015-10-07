package com.example.pearl.materialdesignapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.pearl.materialdesignapp.activity.MainActivity;
import com.example.pearl.materialdesignapp.activity.ThemesActivity;

/**
 * Created by Pearl on 10/1/2015.
 */
public class ChangeThemes {

    public static final String THEME_KEY = "theme";
    private static ChangeThemes sInstance = null;

    public enum SelectedTheme {
        THEME_BLUE,
        THEME_RED,
        THEME_PNK,
        THEME_YELLOW,
        THEME_ORANGE,
        THEME_DARKBROWB,
        THEME_MIXED,
        THEME_VIOLET
    }

    private SelectedTheme selectedTheme = SelectedTheme.THEME_PNK;


    public String getThemeColor(){
        if (selectedTheme == SelectedTheme.THEME_DARKBROWB){
            return "#663300";
        }else if (selectedTheme == SelectedTheme.THEME_RED){
            return "#800000";
        }else if (selectedTheme == SelectedTheme.THEME_BLUE){
            return "#3366FF";
        }else if (selectedTheme == SelectedTheme.THEME_YELLOW){
            return "#FFCC00";
        }else if (selectedTheme == SelectedTheme.THEME_ORANGE){
            return "#FF6600";
        } else if (selectedTheme == SelectedTheme.THEME_MIXED){
            return "#00CCCC";
        } else if (selectedTheme == SelectedTheme.THEME_VIOLET){
            return "#CC00CC";
        }
        else return "#FF0066";
    }

    public SelectedTheme getSelectedTheme(){
        return selectedTheme;
    }

    /**
     *
     * @param selectedTheme
     */
    public void setSelectedTheme(SelectedTheme selectedTheme){
        this.selectedTheme = selectedTheme;

            saveTheme(getThemeColor());

    }

    /**
     *
     * @param value
     */
    private void saveTheme(final String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(THEME_KEY, value);
        editor.commit();
    }

    /**
     *
     * @return themses
     */
    public String loadTheme(){
        String s = null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.activity);
        s = prefs.getString(THEME_KEY, "#FF0066");
        return s;
    }

    public static ChangeThemes getsInstance(){
        synchronized (ChangeThemes.class){
            if (sInstance == null){
                sInstance = new ChangeThemes();
            }
        }
        return sInstance;
    }

    public static ChangeThemes newInstance(){
        return new ChangeThemes();
    }

    public static void  resetInstance(){
        sInstance = null;
    }
}  //+** END OF CLASS **//
