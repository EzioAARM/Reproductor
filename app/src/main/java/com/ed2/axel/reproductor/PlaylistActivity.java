package com.ed2.axel.reproductor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private String m_Text = "";
    List<Cancion> Canciones = null;
    GlobalClass globalClass = null;
    Ordenamientos ordenamientos = new Ordenamientos();
    List<Cancion> CancionesOrdenadas = null;
    boolean ordenadoAscendente = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        globalClass = GlobalClass.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] Nombres = new String[globalClass.ListadoPlaylists.size()];
        Enumeration<String> n = globalClass.ListadoPlaylists.keys();
        int i = 0;
        while (n.hasMoreElements()) {
            Nombres[i] = n.nextElement();
            i++;
        }
        Spinner seleccionarPlaylist = findViewById(R.id.seleccionar_playlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seleccionarPlaylist.setAdapter(adapter);
        seleccionarPlaylist.setOnItemSelectedListener(this);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final Switch ordenarPor = (Switch) findViewById(R.id.ordenarPor);
        ordenarPor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ordenadoAscendente){
                    if (ordenarPor.isChecked())
                        CancionesOrdenadas = ordenamientos.Ascendente(Canciones);
                    else
                        CancionesOrdenadas = ordenamientos.AscendenteDuracion(Canciones);
                } else {
                    if (ordenarPor.isChecked())
                        CancionesOrdenadas = ordenamientos.Descendente(Canciones);
                    else
                        CancionesOrdenadas = ordenamientos.DescendenteDuracion(Canciones);
                }
                List<String> lsCa = new ArrayList<>();
                for (int i = 0; i < CancionesOrdenadas.size(); i++) {
                    lsCa.add(CancionesOrdenadas.get(i).toString());
                }
                mostrarListado(lsCa);
            }
        });
        final Switch ordenarMetodoS = (Switch) findViewById(R.id.ordenar_metodo);
        ordenarMetodoS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ordenarMetodoS.isChecked()){
                    if (ordenarPor.isChecked())
                        CancionesOrdenadas = ordenamientos.Ascendente(Canciones);
                    else
                        CancionesOrdenadas = ordenamientos.AscendenteDuracion(Canciones);
                } else {
                    if (ordenarPor.isChecked())
                        CancionesOrdenadas = ordenamientos.Descendente(Canciones);
                    else
                        CancionesOrdenadas = ordenamientos.DescendenteDuracion(Canciones);
                }
                List<String> lsCa = new ArrayList<>();
                for (int i = 0; i < CancionesOrdenadas.size(); i++) {
                    lsCa.add(CancionesOrdenadas.get(i).toString());
                }
                mostrarListado(lsCa);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String seleccionado = parent.getItemAtPosition(position).toString();
        for (int i = 0; i < globalClass.ListadoPlaylists.size(); i++) {
            if (globalClass.ListadoPlaylists.get(seleccionado) != null) {
                mostrarListado(globalClass.ListadoPlaylists.get(seleccionado).listadoCancionesString());
                Canciones = globalClass.ListadoPlaylists.get(seleccionado).Canciones;
                i = globalClass.ListadoPlaylists.size();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void mostrarListado(List<String> lsNombres) {
        ListView listado = (ListView) findViewById(R.id.lsPlaylists);
        String[] nombres = new String[lsNombres.size()];
        for (int i = 0; i < lsNombres.size(); i++){
            nombres[i] = lsNombres.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres);
        listado.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_playlist, menu);
        return true;
    }

    public void ordenar(List<Cancion> Canciones) {
        Switch ordenarPor = (Switch) findViewById(R.id.ordenarPor);
        Switch ordenarMetodo = (Switch) findViewById(R.id.ordenar_metodo);
        if (ordenarPor.isChecked()){
            if (ordenarMetodo.isChecked()) {
                CancionesOrdenadas = ordenamientos.Ascendente(Canciones);
                ordenadoAscendente = true;
            } else {
                CancionesOrdenadas = ordenamientos.Descendente(Canciones);
                ordenadoAscendente = false;
            }
        } else {
            if (ordenarMetodo.isChecked()) {
                CancionesOrdenadas = ordenamientos.AscendenteDuracion(Canciones);
                ordenadoAscendente = true;
            } else {
                CancionesOrdenadas = ordenamientos.DescendenteDuracion(Canciones);
                ordenadoAscendente = false;
            }
        }
        List<String> lsCa = new ArrayList<>();
        for (int i = 0; i < CancionesOrdenadas.size(); i++) {
            lsCa.add(CancionesOrdenadas.get(i).toString());
        }
        mostrarListado(lsCa);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregar_playlist:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Nombre de la playlist");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Crear Lista", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        globalClass.ListadoPlaylists.put(m_Text, new Playlist(m_Text));
                        String[] Nombres = new String[globalClass.ListadoPlaylists.size()];
                        Enumeration<String> n = globalClass.ListadoPlaylists.keys();
                        int i = 0;
                        while (n.hasMoreElements()) {
                            Nombres[i] = n.nextElement();
                            i++;
                        }
                        Spinner seleccionarPlaylist = findViewById(R.id.seleccionar_playlist);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlaylistActivity.this, android.R.layout.simple_list_item_1, Nombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seleccionarPlaylist.setAdapter(adapter);
                        seleccionarPlaylist.setOnItemSelectedListener(PlaylistActivity.this);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
