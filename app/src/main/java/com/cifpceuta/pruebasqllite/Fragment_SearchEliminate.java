package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class Fragment_SearchEliminate extends Fragment {

    RecyclerView recyclerView;

    MyArrayAdapterBorrar myArrayAdapterBorrar;

    SearchView searchView;

    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String grupo;



    public Fragment_SearchEliminate() {
        // Required empty public constructor
    }


    public static Fragment_SearchEliminate newInstance(String param1, String param2) {
        Fragment_SearchEliminate fragment = new Fragment_SearchEliminate();
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
        View rootView =  inflater.inflate(R.layout.fragment__search_eliminate, container, false);

        String id = mAuth.getCurrentUser().getUid();

        db.collection("Estudiantes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                  @Override
                                                                                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                      if (task.isSuccessful()) {
                                                                                          Log.w("Grupo", "Entra");

                                                                                          DocumentSnapshot documentSnapshot = task.getResult();
                                                                                          if (documentSnapshot.exists()) {
                                                                                              grupo = documentSnapshot.getData().get("grupo").toString();
                                                                                              Log.w("GrupoDespues", "Grupo: " + grupo);

                                                                                              db.collection("tareas").document(grupo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                  @Override
                                                                                                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                      DocumentSnapshot cs = null;


                                                                                                      if (task.isSuccessful()) {
                                                                                                          Log.d("Estado tarea", "tarea completada");
                                                                                                          cs = task.getResult();
                                                                                                          if (cs.exists()) {
                                                                                                              Log.d("Estado tarea", "Documento encontrado");
                                                                                                          } else {
                                                                                                              Log.d("Estado tarea", "Documento no encontrado");
                                                                                                          }
                                                                                                      } else {
                                                                                                          Log.d("PeticiónTareas", "Error " + task.getException());
                                                                                                      }

                                                                                                      ArrayList<Practica> practicas = (ArrayList<Practica>) cs.get("tareas");
                                                                                                      recyclerView = rootView.findViewById(R.id.rv_elementos_new);

                                                                                                      myArrayAdapterBorrar = new MyArrayAdapterBorrar(practicas);
                                                                                                      recyclerView.setAdapter(myArrayAdapterBorrar);
                                                                                                      recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));


                                                                                                  }
                                                                                              });

                                                                                          } else {
                                                                                              Log.w("Grupo", "No Entra");
                                                                                              Log.w("Documento vacío", "Documento no enocontrado ");

                                                                                          }
                                                                                      } else {
                                                                                          Log.w("Grupo", "NoEntra");
                                                                                          Log.w("Error, no es succesful", "Error: " + task.getException());

                                                                                      }
                                                                                  }

                                                                              });


            searchView =rootView.findViewById(R.id.sv_buscar_elemento);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

            {
                @Override
                public boolean onQueryTextSubmit (String query){
                return false;
            }

                @Override
                public boolean onQueryTextChange (String newText){

                filtrado(newText, myArrayAdapterBorrar);


                return false;
            }
            });









        return rootView;
        }
    private void filtrado(String texto, MyArrayAdapterBorrar myArrayAdapterBorrar){
        ArrayList<Practica> filteredList_items = new ArrayList<>();

        Practica p;
        ArrayList<Practica> practicas = new ArrayList<>();

        for (int i=0;i<myArrayAdapterBorrar.practicas.size();i++) {
            try {

                p = myArrayAdapterBorrar.practicas.get(i);


            } catch (ClassCastException castException) {

                p = new Practica((Map<String, Object>) myArrayAdapterBorrar.practicas.get(i));

            }

            practicas.add(p);

        }
        for(Practica item : practicas){
            if(item.getDescrtiption().toLowerCase().contains(texto.toLowerCase())){
                filteredList_items.add(item);
            }
        }
        myArrayAdapterBorrar.setFilterList(filteredList_items);
    }

    }





