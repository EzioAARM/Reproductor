package com.ed2.axel.reproductor;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public List<Cancion> Canciones = null;

    public Playlist(String nombre){
        Canciones = new ArrayList<>();
    }

    public void agregarCancion(Cancion cancion){
        Canciones.add(cancion);
    }

    public void eliminarCancion(int posicion){
        Canciones.remove(posicion);
    }

    public List<String> listadoCancionesString() {
        List<String> lsCa = new ArrayList<>();
        for (int i = 0; i < Canciones.size(); i++) {
            lsCa.add(Canciones.get(i).toString());
        }
        return lsCa;
    }
}
