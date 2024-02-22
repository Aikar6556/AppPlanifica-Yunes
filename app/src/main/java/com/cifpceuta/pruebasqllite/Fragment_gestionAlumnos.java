package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Fragment_gestionAlumnos extends Fragment {

    RecyclerView recyclerView;

    MyArrayAdapterEstudiantes myArrayAdapterEstudiantes;

    ArrayList<Estudiante> estudiantes = new ArrayList<>();


    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String grupo, alumno, correo, turno, grupoDos;

    Spinner spinner;


    public Fragment_gestionAlumnos() {
        // Required empty public constructor
    }


    public static Fragment_gestionAlumnos newInstance(String param1, String param2) {
        Fragment_gestionAlumnos fragment = new Fragment_gestionAlumnos();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gestion_alumnos, container, false);

        spinner = rootView.findViewById(R.id.spinner);

        String[] grupos = {"1DAM", "2DAM"};



        ArrayAdapter<String> adaptador = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, grupos);
        spinner.setAdapter(adaptador);

        grupoDos = "1DAM";


        String id = mAuth.getCurrentUser().getUid();

        db.collection("Estudiantes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.w("Grupo", "Entra");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 grupo = document.getString("grupo");
                                 alumno = document.getString("nombre");
                                 correo = document.getString("correo");


                                Estudiante estudiante = new Estudiante(alumno, correo, grupo,turno);


                                        estudiantes.add(estudiante);
                                        Log.w("Numero objetos en array List", "Num: " + estudiantes.size());


                                Log.w("GrupoDespues", "Grupo: " + grupo);
                                Log.w("GrupoDespues", "alumno: " + alumno);
                                Log.w("GrupoDespues", "correo: " + correo);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }

                        recyclerView = rootView.findViewById(R.id.rv_elementos_alumnos);

                        Log.w("Numero objetos en array List", "Num: " + estudiantes.size());

                        myArrayAdapterEstudiantes = new MyArrayAdapterEstudiantes(estudiantes);
                        recyclerView.setAdapter(myArrayAdapterEstudiantes);
                        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selected = spinner.getItemAtPosition(position).toString();
                                Log.w("Selected", "Num: " + selected);


                                myArrayAdapterEstudiantes.setFilterList(selected);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });






                    }
                });

        return rootView;

    }
}
