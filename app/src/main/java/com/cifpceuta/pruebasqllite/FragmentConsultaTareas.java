package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentConsultaTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConsultaTareas extends Fragment {

    RecyclerView recyclerView;
    MyArrayAdapterPracticas myArrayAdapterPracticas;

    SearchView searchView;


    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String grupo;

    public FragmentConsultaTareas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FragmentConsultaTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConsultaTareas newInstance() {
        FragmentConsultaTareas fragment = new FragmentConsultaTareas();
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

        View rootView = inflater.inflate(R.layout.fragment_consulta_tareas, container, false);

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
                                recyclerView = rootView.findViewById(R.id.rv_elementos);

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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("AAA","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+e.getMessage());
            }
        });




        Log.w("Grupo","Grupo: "+grupo);

        searchView = rootView.findViewById(R.id.svBusqueda);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filtrado(newText,myArrayAdapterPracticas);


                return false;
            }
        });









        return rootView;
    }


    private void filtrado(String texto, MyArrayAdapterPracticas myArrayAdapterPracticas){
        ArrayList<Practica> filteredList_items = new ArrayList<>();

        Practica p;
        ArrayList<Practica> practicas = new ArrayList<>();

        for (int i=0;i<myArrayAdapterPracticas.practicas.size();i++) {
            try {

                p = myArrayAdapterPracticas.practicas.get(i);


            } catch (ClassCastException castException) {

                p = new Practica((Map<String, Object>) myArrayAdapterPracticas.practicas.get(i));

            }

            practicas.add(p);

        }
        for(Practica item : practicas){
            if(item.getDescrtiption().toLowerCase().contains(texto.toLowerCase())){
                filteredList_items.add(item);
            }
        }
        myArrayAdapterPracticas.setFilterList(filteredList_items);
    }


}