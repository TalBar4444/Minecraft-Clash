package com.example.myapp1;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapp1.logic.Player;
import com.example.myapp1.logic.TableScore;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MSPV {
    private static final String SP_FILE_NAME = "SP_FILE_NAME";
    private SharedPreferences prefs = null;
    private static MSPV me;
    private  String TOP_TEN = "TOP_TEN";
    private MSPV(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }
    public static void init(Context context) {
        if (me == null) {
            me = new MSPV(context);
        }
    }
    public static MSPV getMe() {
        return me;
    }
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String readString(String key, String def) {
        String value = prefs.getString(key, def);
        return value;
    }
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int readInt(String key, int def) {
        int value = prefs.getInt(key, def);
        return value;
    }
    public void saveTopTenPlayers(TableScore tableScore){
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(tableScore);
        this.getMe().saveString(TOP_TEN,json);
        editor.apply();
    }
    public TableScore readTopTenPlayers(){
        String json = this.getMe().readString(TOP_TEN,null);
        if(json == null) {
            saveTopTenPlayers(new TableScore().setPlayers(new ArrayList<Player>()));
            return readTopTenPlayers();
        }
        return new Gson().fromJson(json, TableScore.class);
    }
}

