package com.ed2.axel.reproductor;

import android.app.Application;

import java.util.Hashtable;

public class GlobalClass extends Application {

    private static GlobalClass instance;

    public Hashtable<String, Playlist> ListadoPlaylists = new Hashtable<>();

    public static synchronized GlobalClass getInstance(){
        if(instance==null){
            instance=new GlobalClass();
        }
        return instance;
    }
}
