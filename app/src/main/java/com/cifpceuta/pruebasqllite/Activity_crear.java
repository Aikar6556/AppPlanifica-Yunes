package com.cifpceuta.pruebasqllite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_crear extends AppCompatActivity {

     FirebaseAuth mAuth;

     FirebaseFirestore db;

    EditText nombreUsu, correoUsu, passUsu;
    Spinner spinner;
    RadioButton rbTarde, rbMañana;
    RadioGroup rgSelected;
    Button registrar;


//    MyDbHelper manejador;
//
//    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        nombreUsu = findViewById(R.id.etNombre);
        correoUsu = findViewById(R.id.etCorreo);
        passUsu = findViewById(R.id.etPassw);
        mAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinner);
        rbTarde = findViewById(R.id.rbTarde);
        rbMañana = findViewById(R.id.rbMañana);
        registrar = findViewById(R.id.btRegistro);
        rgSelected = findViewById(R.id.rgTurno);
        db = FirebaseFirestore.getInstance();
//        manejador = new MyDbHelper(this);
//        database = manejador.getWritableDatabase();

        String [] cursos = {"1DAM","2DAM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,cursos);
        spinner.setAdapter(adapter);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro();
            }
        });
    }


    public void home(View view) {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

//    public void registrarse(View view){
//        ContentValues content = new ContentValues();
//        content.put(AreaContract.AreaEntry.COLUMN_USUARIO,nombreUsu.getText().toString());
//        content.put(AreaContract.AreaEntry.COLUMN_PASSW,paswordUsu.getText().toString());
//
//        database.insert(AreaContract.AreaEntry.TABLE_NAME,null,content);
//
//        startActivity(new Intent(this,Launcher.class));
//}


    public void registro() {

        String correo = correoUsu.getText().toString();
        String password = passUsu.getText().toString();
        String nombre = nombreUsu.getText().toString();
        String turno;
            if (rbMañana.isChecked()){

                turno = "Mañana";

            }else{

                turno = "Tarde";
            }
        String curso = spinner.getSelectedItem().toString();

       mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()) {
                   Toast.makeText(getApplicationContext(),
                                   "Registro realizado correctamente!",
                                   Toast.LENGTH_LONG).show();
                   agregarDatos(new Estudiante(nombre,correo,curso,turno) ,mAuth.getCurrentUser().getUid());


                   // si el usuario se ha creado volvemos al Activity Principal para que se pueda logear
                   Intent intent
                           = new Intent(Activity_crear.this,
                           MainActivity.class);
                   startActivity(intent);
               } else {

                   // En este punto algo ha fallado, lo notificaremos
                   Toast.makeText(
                                   getApplicationContext(),
                                   "El registro ha fallado!!"
                                           + " Intente mas tarde...",
                                   Toast.LENGTH_LONG)
                           .show();
               }
           }
       });



    }


    public void agregarDatos(Estudiante estudiante, String userID){


        Map<String, Object> estudianteNuevo = new HashMap<>();
        estudianteNuevo.put("nombre", estudiante.getNombre());
        estudianteNuevo.put("correo", estudiante.getCorreo());
        estudianteNuevo.put("grupo", estudiante.getGrupo());
        estudianteNuevo.put("turno", estudiante.getTurno());

        db.collection("Estudiantes").document(userID)
                .set(estudianteNuevo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Completar", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Completar", "Error writing document", e);
                    }
                });

    }

}