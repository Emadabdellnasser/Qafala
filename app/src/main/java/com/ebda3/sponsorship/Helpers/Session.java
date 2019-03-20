package com.ebda3.sponsorship.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Session
{
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;



    public Session(Context context)
    {
        this.context = context ;
        prefs = context.getSharedPreferences("AttendenceListener", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setlogin(boolean login)
    {
        editor.putBoolean("loginmode",login);
        editor.commit();

    }

    public boolean loggin()
    {
        return prefs.getBoolean("loginmode",false);
    }
}
