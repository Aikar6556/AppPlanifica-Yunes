package com.cifpceuta.pruebasqllite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView mensaje;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Toolbar toolbar;

    String nombre, correo, turno, grupo;

    FirebaseAuth mAuth;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mensaje = findViewById(R.id.saludo);
        toolbar = findViewById(R.id.toolbar);



        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        String correoUsuario;

        Intent i = this.getIntent();
        if (i != null) {

            correoUsuario = i.getStringExtra("correo");
            mensaje.setText("Bienvenido! "+correoUsuario);


        }

        DefaultFragment defaultFragment = DefaultFragment.newInstance(mensaje.getText().toString());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String userID = mAuth.getCurrentUser().getUid();

        DocumentReference docRef = db.collection("Estudiantes").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){

                        correo = (String) document.get("correo");
                        nombre = (String) document.get("nombre");
                        grupo = (String) document.get("grupo");
                        turno = (String) document.get("turno");


                    }
                } else {
                    Log.d("get failed with ", String.valueOf(task.getException()));
                }
            }

        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragPerfilEst, defaultFragment)
                    .commit();
        }

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int itemId = item.getItemId();

        if (itemId == R.id.nav_account) {

            InicioCuenta inicioCuenta = InicioCuenta.newInstance(nombre,correo,turno,grupo);




            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragPerfilEst, inicioCuenta)
                    .commit();
        } else if (itemId == R.id.nav_logout) {
            Toast.makeText(HomeActivity.this, "Entra en salir", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(HomeActivity.this, MainActivity.class));

        } else if (itemId == R.id.plan_practica) {

            FragmentPlanifica fragmentPlanifica = FragmentPlanifica.newInstance("hola","adios");

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragPerfilEst, fragmentPlanifica)
                    .commit();


        } else if (itemId == R.id.plan_exam) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragPerfilEst, new DefaultFragment())
                    .commit();


        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragPerfilEst, new DefaultFragment())
                    .commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}