package com.ed2.axel.reproductor;

import java.util.List;

public class Ordenamientos {
    public List<Cancion> Descendente(List<Cancion> canciones){
        Cancion temp = null;
        for (int i = 0; i < canciones.size(); i++) {
            for (int j = 0; j < canciones.size(); j++) {
                if (canciones.get(i).Nombre.compareTo(canciones.get(j).Nombre)  >= 0){
                    temp = canciones.get(i);
                    canciones.set(i, canciones.get(j));
                    canciones.set(j, temp);
                }
            }
        }
        return canciones;
    }

    public List<Cancion> Ascendente(List<Cancion> canciones){
        Cancion temp = null;
        for (int i = 0; i < canciones.size(); i++) {
            for (int j = 0; j < canciones.size(); j++) {
                if (canciones.get(i).Nombre.compareTo(canciones.get(j).Nombre)  <= 0){
                    temp = canciones.get(i);
                    canciones.set(i, canciones.get(j));
                    canciones.set(j, temp);
                }
            }
        }
        return canciones;
    }
}
