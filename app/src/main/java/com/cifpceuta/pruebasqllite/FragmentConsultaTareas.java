package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
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

    ArrayList<Practica> listaPracticas;

    FirebaseFirestore db;
    FirebaseAuth mAuth;







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
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_consulta_tareas, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("tareas").document("1DAM").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot cs =task.getResult();

                ArrayList<HashMap<String, Object>> p = (ArrayList<HashMap<String, Object>>) cs.get("tareas");
              //  System.out.println(p.get(0).get("modulo"));

                Practica newPracticas = new Practica();
                ArrayList<Practica> practicasToAdd = new ArrayList<>();

                for (int i=0;i<p.size();i++){
                    System.out.println(p.get(i));
                    newPracticas.setTarea(p.get(i).get("tarea").toString());
                    newPracticas.setTarea(p.get(i).get("modulo").toString());
                    newPracticas.setTarea(p.get(i).get("fechaInicio").toString());
                    newPracticas.setTarea(p.get(i).get("fechaFinal").toString());
                    newPracticas.setTarea(p.get(i).get("descrtiption").toString());
                    practicasToAdd.add(newPracticas);

                }

                for (int i=0;i<practicasToAdd.size();i++){

                    System.out.println(practicasToAdd.get(i));
                }
                recyclerView = rootView.findViewById(R.id.rv_elementos);

                myArrayAdapterPracticas = new MyArrayAdapterPracticas(practicasToAdd);
                recyclerView.setAdapter(myArrayAdapterPracticas);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));



            }
        });








        return rootView;
    }
}