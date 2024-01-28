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

        View rootView = inflater.inflate(R.layout.fragment_consulta_tareas, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("tareas").document("1DAM").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot cs = null;


                if (task.isSuccessful()){
                    System.out.printf("Tareas recibidas");
                    cs = task.getResult();
                    if (cs.exists()){
                        System.out.printf("tareas recibidas");

                    }else{
                        System.out.printf("Documento no encontrado");
                    }
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








        return rootView;
    }
}