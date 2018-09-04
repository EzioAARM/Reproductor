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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private String m_Text = "";
    private List<String> ListaNombresPasar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] Nombres = getIntent().getExtras().getStringArray("Lista");
        for (int i = 0; i < Nombres.length; i++){
            ListaNombresPasar.add(Nombres[i]);
        }
        ListView listado = (ListView) findViewById(R.id.lsPlaylists);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Nombres);
        listado.setAdapter(adapter);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, MainActivity.class);
                String[] nombresPlaylist = new String[ListaNombresPasar.size()];
                for (int i = 0; i < ListaNombresPasar.size(); i++) {
                    nombresPlaylist[i] = ListaNombresPasar.get(i);
                }
                intent.putExtra("ListasDeReproduccion", nombresPlaylist);
                startActivity(intent);
                finish();
            }
        });
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
                        ListaNombresPasar.add(m_Text);
                        mostrarListado(ListaNombresPasar);
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
