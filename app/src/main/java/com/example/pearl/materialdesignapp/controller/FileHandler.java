package com.example.pearl.materialdesignapp.controller;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Pearl on 10/8/2015.
 */
public class FileHandler {

    private static FileHandler sInstance = null;

    private void FileHandler(){

    }

    /**
     *
     * @return single Instance
     */
    public static FileHandler getsInstance(){
        synchronized (FileHandler.class){
            if (sInstance == null){
                sInstance = new FileHandler();
            }
        }
        return sInstance;
    }

    /**
     *
     * @param context
     */
    public void writeCache(Context context){
        File file = context.getCacheDir();
        File tempFile = new File(file.getPath()+"/temp.jks");
        try {
            FileWriter fw = new FileWriter(tempFile);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(Singleton.getInstance().getActivityName());
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readCache(Context context){
        File file = context.getCacheDir();
        File tempFile = new File(file.getPath()+ "/temp.jks");
        try {
            FileReader fr = new FileReader(tempFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            Toast.makeText(context, line, Toast.LENGTH_SHORT).show();
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
