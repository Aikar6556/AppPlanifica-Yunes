package com.cifpceuta.pruebasqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    MyDbHelper manejador;

    Button iniciarSesion, registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iniciarSesion = findViewById(R.id.entrar);
        registrar = findViewById(R.id.registro);


    }

    public void registro(View view){

        Intent i = new Intent(this, Activity_crear.class);
        startActivity(i);
    }

    public void iniciar(View view){

        Intent i = new Intent(this, Launcher.class);
        startActivity(i);
    }





}