package com.ed2.axel.reproductor;

public class Cancion {
    public String Nombre;
    public String Artista;
    public String Duracion;

    public Cancion(String nombre, String artista, String duracion){
        Nombre = nombre;
        Artista = artista;
        Duracion = duracion;
    }

    @Override
    public String toString() {
        return Nombre + ", " + Artista + "\n" + Duracion;
    }
}
