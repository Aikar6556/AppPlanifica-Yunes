package com.cifpceuta.pruebasqllite;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Launcher extends AppCompatActivity {

    EditText correo, password;
    Button iniciarSesion;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        correo = findViewById(R.id.etMail);
        password = findViewById(R.id.etPassw);
        iniciarSesion = findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

    }

    public void home(View view) {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void iniciarSesion(){

        String email = correo.getText().toString();
        String passw = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent i = new Intent(Launcher.this, HomeActivity.class);
                    i.putExtra("correo",email);
                    startActivity(i);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(Launcher.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });




    }



}


    



