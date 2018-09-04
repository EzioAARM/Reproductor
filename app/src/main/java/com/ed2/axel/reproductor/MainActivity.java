package com.ed2.axel.reproductor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    List<Cancion> Canciones = null;
    List<Cancion> CancionesOrdenadas = null;
    Hashtable<String, Playlist> Playlists = null;
    Ordenamientos ordenar = new Ordenamientos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Canciones = new ArrayList<>();
        Playlists = new Hashtable<>();

        // Agregar canciones a la lista de canciones
        Canciones.add(new Cancion("Stairway to heaven", "Led Zeppelin", "8:02"));
        Canciones.add(new Cancion("Since i've been loving you", "Led Zeppelin", "7:24"));
        Canciones.add(new Cancion("The real slim shady", "Eminem", "4:44"));
        Canciones.add(new Cancion("Rap God", "Eminem", "6:03"));
        Canciones.add(new Cancion("Don", "Miranda!", "3:03"));
        Canciones.add(new Cancion("The devil in I", "Slipknot", "5:42"));
        Canciones.add(new Cancion("I kissed a girl", "Katy Perry", "2:59"));
        Canciones.add(new Cancion("Perfecta", "Miranda!", "3:45"));
        Canciones.add(new Cancion("Counting stars", "OneRepublic", "4:17"));
        Canciones.add(new Cancion("Eres para mí", "Julieta Venegas Ana Tijoux", "3:12"));
        Canciones.add(new Cancion("New Faith", "Slayer", "3:05"));
        Canciones.add(new Cancion("Painkiller", "Judas Priest", "6:05"));
        Canciones.add(new Cancion("We made you", "Eminem", "4:29"));
        Canciones.add(new Cancion("Kids (Ain't all right)", "Grace Mitchell", "2:29"));
        Canciones.add(new Cancion("Nemesis", "Arch Enemy", "4:12"));
        Canciones.add(new Cancion("The beautiful people", "Marilyn Manson", "3:38"));
        Canciones.add(new Cancion("Sticken", "Disturbed", "4:05"));
        Canciones.add(new Cancion("Nothing else matters", "Metallica", "6:28"));
        Canciones.add(new Cancion("Enter sandman", "Metallica", "5:31"));
        Canciones.add(new Cancion("Still loving you", "Scorpions", "6:43"));
        Canciones.add(new Cancion("South of heaven", "Slayer", "4:58"));
        Canciones.add(new Cancion("Jekyll and Hyde", "Five finger death punch", "3:26"));
        Canciones.add(new Cancion("Rainbow in the dark", "Dio", "4:12"));
        Canciones.add(new Cancion("Symphony of destruction", "Megadeth", "4:06"));
        Canciones.add(new Cancion("Highway to hell", "AC/DC", "3:28"));

        CancionesOrdenadas = Canciones;

        Spinner ordenmientos = findViewById(R.id.spOrdenamientos);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.ordenamientos, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ordenmientos.setAdapter(adaptador);
        ordenmientos.setOnItemSelectedListener(this);

        mostrarListado(Canciones);

        Button btnBuscra = (Button)findViewById(R.id.btnBuscar);
        btnBuscra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editBusqueda = (EditText)findViewById(R.id.etBusqueda);
                buscar(editBusqueda.getText().toString());
            }
        });
        EditText editBusqueda = (EditText) findViewById(R.id.etBusqueda);
        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText editable = (EditText)findViewById(R.id.etBusqueda);
                buscar(editable.getText().toString());
                return false;
            }
        });
    }

    public void buscar(String texto){
        List<Cancion> encontradas = new ArrayList<>();
        for (int i = 0; i < Canciones.size(); i++){
            if (Canciones.get(i).Nombre.toUpperCase().indexOf(texto.toUpperCase()) >= 0){
                encontradas.add(Canciones.get(i));
            }
        }
        if (texto.equals("")){
            encontradas = Canciones;
        }
        mostrarListado(encontradas);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_playlist:
                Intent Playlists = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(Playlists);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarListado(List<Cancion> canciones){
        ListView listado = (ListView) findViewById(R.id.lsCanciones);
        String[] listadoCanciones = new String[canciones.size()];
        for (int i = 0; i < listadoCanciones.length; i++){
            listadoCanciones[i] = canciones.get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadoCanciones);
        listado.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String seleccionado = parent.getItemAtPosition(position).toString();
        CharSequence texto = "";
        if (seleccionado.equals("Ascendente")) {
            texto = "Ordena de forma ascendente";
            CancionesOrdenadas = ordenar.Ascendente(Canciones);
        } else if (seleccionado.equals("Descendente")) {
            texto = "Ordena de forma descendente";
            CancionesOrdenadas = ordenar.Descendente(Canciones);
        }
        mostrarListado(CancionesOrdenadas);
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
