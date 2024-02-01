package com.cifpceuta.pruebasqllite;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Dialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Dialog extends Fragment {


    FloatingActionButton fabAdd;

    FirebaseFirestore db;

    FirebaseAuth mAuth;

    MyArrayAdapterPracticas myArrayAdapterPracticas;

    String grupo;

    RecyclerView recyclerView;





    public Fragment_Dialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dialog.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dialog newInstance(String param1, String param2) {
        Fragment_Dialog fragment = new Fragment_Dialog();
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

        View rootView =  inflater.inflate(R.layout.fragment__dialog, container, false);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        fabAdd = rootView.findViewById(R.id.fabAñadir);

        String id = mAuth.getCurrentUser().getUid();
        Log.w("IdUser","Id: "+id);

        db.collection("Estudiantes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    Log.w("Exito","Task Succesful: "+id);

                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.exists()){

                        Log.w("Exito2","Document Exists: "+id);

                        grupo = documentSnapshot.getData().get("grupo").toString();

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


                                ArrayList<Practica> practicasFiltradas = new ArrayList<>();

                                Practica p;


                                for (int i = 0; i < practicas.size(); i++) {

                                    try {

                                        p = practicas.get(i);


                                    } catch (ClassCastException error) {


                                        p = new Practica((Map<String, Object>) practicas.get(i));

                                    }

                                    practicasFiltradas.add(p);



                                }


                                recyclerView = rootView.findViewById(R.id.rv_salidas);

                                myArrayAdapterPracticas = new MyArrayAdapterPracticas(practicasFiltradas);
                                recyclerView.setAdapter(myArrayAdapterPracticas);
                                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));


                            }
                        });








                    }else{

                        Log.w("Fail2","Document NO exists: "+id);

                    }


                }else{

                    Log.w("Fail","Task Not succesfully: "+id);

                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(rootView.getContext());

                dialog.setContentView(R.layout.dialog_layout);

                EditText etTextoItem = dialog.findViewById(R.id.etTextoItem);
                Button btnDialogAñadir = dialog.findViewById(R.id.btnDialogAñadir);

                btnDialogAñadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String item = etTextoItem.getText().toString();





                        dialog.dismiss();

                    }
                });

                dialog.show();





            }
        });






        return rootView;
    }
}