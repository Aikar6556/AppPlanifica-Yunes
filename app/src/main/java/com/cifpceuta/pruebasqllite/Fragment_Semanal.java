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
import android.widget.TableLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;


public class Fragment_Semanal extends Fragment {

    RecyclerView recyclerView;
    MyArrayAdapterPracticas myArrayAdapterPracticas;


    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String grupo;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public Fragment_Semanal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Semanal.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Semanal newInstance(String param1, String param2) {
        Fragment_Semanal fragment = new Fragment_Semanal();
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

        View rootView = inflater.inflate(R.layout.fragment__semanal, container, false);
        TabLayout tableLayout = rootView.findViewById(R.id.table);

        tableLayout.addTab(tableLayout.newTab().setText("SEM. 1"));
        tableLayout.addTab(tableLayout.newTab().setText("SEM. 2"));
        tableLayout.addTab(tableLayout.newTab().setText("SEM. 3"));
        tableLayout.addTab(tableLayout.newTab().setText("SEM. 4"));
        tableLayout.addTab(tableLayout.newTab().setText("SEM. 5"));

        String id = mAuth.getCurrentUser().getUid();
        Log.w("IdUser","Id: "+id);
        db.collection("Estudiantes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    Log.w("Grupo","Entra");

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        grupo = documentSnapshot.getData().get("grupo").toString();
                        Log.w("GrupoDespues","Grupo: "+grupo);

                        db.collection("tareas").document(grupo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot cs = null;


                                if (task.isSuccessful()){
                                    Log.d("Estado tarea","tarea completada");
                                    cs = task.getResult();
                                    if (cs.exists()){
                                        Log.d("Estado tarea","Documento encontrado");
                                    }else{
                                        Log.d("Estado tarea","Documento no encontrado");                    }
                                }else{
                                    Log.d("PeticiónTareas","Error "+task.getException());
                                }



                                ArrayList<Practica> practicas = (ArrayList<Practica>) cs.get("tareas");


                                for (int i=0;i<practicas.size();i++){

                                    System.out.printf(practicas.get(i).getFechaFinal().toString());
                                    //Log.d("Fecha final",dato);

                                }

                                tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });





                                recyclerView = rootView.findViewById(R.id.rv_semanal);

                                myArrayAdapterPracticas = new MyArrayAdapterPracticas(practicas);
                                recyclerView.setAdapter(myArrayAdapterPracticas);
                                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));





                            }
                        });



                    }else {
                        Log.w("Grupo","No Entra");
                        Log.w("Documento vacío","Documento no enocontrado ");

                    }
                }else{
                    Log.w("Grupo","NoEntra");
                    Log.w("Error, no es succesful","Error: "+task.getException());

                }
            }
        });




        Log.w("Grupo","Grupo: "+grupo);









        return rootView;
    }

    private static int obtenerSemanaDelMes(LocalDate fecha) {



        int diaDelMes = fecha.getDayOfMonth();
        int diaDeLaSemana = fecha.getDayOfWeek().getValue();

        int semanaDelMes = (diaDelMes - 1) / 7 + 1;

        if (diaDelMes <= 7 && diaDeLaSemana > diaDelMes) {
            semanaDelMes++;
        }

        return semanaDelMes;
    }




}