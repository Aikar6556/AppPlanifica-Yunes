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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
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

    MyArrayAdapterViajes myArrayAdapterViajes;
    String grupo;

    RecyclerView recyclerView;

    Spinner spinnerGrupo;

    EditText tituloActividad;

    EditText fecha;

    Button botonEnviar;

    Button btnDialogAñadir;

    ArrayList<Viaje> viajes = new ArrayList<>();



    public Fragment_Dialog() {
        // Required empty public constructor
    }


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

        View rootView = inflater.inflate(R.layout.fragment__dialog, container, false);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        fabAdd = rootView.findViewById(R.id.fabAñadir);

        recyclerView = rootView.findViewById(R.id.rv_salidas);


        String id = mAuth.getCurrentUser().getUid();
        Log.w("IdUser", "Id: " + id);

        myArrayAdapterViajes = new MyArrayAdapterViajes(viajes);
        recyclerView.setAdapter(myArrayAdapterViajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        int num = myArrayAdapterViajes.getItemCount();

        Log.w("número de elementos", "num: " + num);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(rootView.getContext());

                dialog.setContentView(R.layout.dialog_layout);

                tituloActividad = dialog.findViewById(R.id.etTituloActividad);
                fecha = dialog.findViewById(R.id.etFecha);
                spinnerGrupo = dialog.findViewById(R.id.spinner);

                String[] grupos = {"1DAM", "2DAM"};


                ArrayAdapter<String> adaptador = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_dropdown_item, grupos);
                spinnerGrupo.setAdapter(adaptador);


                btnDialogAñadir = dialog.findViewById(R.id.btnDialogAñadir);

                btnDialogAñadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        subirDatos(rootView);


                        dialog.dismiss();

                    }
                });

                dialog.show();


            }
        });




        return rootView;
    }

    public void subirDatos(View rootView) {


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String tituloo = tituloActividad.getText().toString();
        String fechaa = fecha.getText().toString();
        String grupoo = spinnerGrupo.getSelectedItem().toString();

        Viaje viaje = new Viaje(tituloo, grupoo, fechaa);


        viajes.add(viaje);
        myArrayAdapterViajes.notifyDataSetChanged();

        Log.d("viajes:",viajes.toString());




        String id = mAuth.getCurrentUser().getUid();

        db.collection("actextra").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, ArrayList<Viaje>> datos = new HashMap<>();

                if (task.isSuccessful()) {

                    // Recupera el documento asociado a la tarea.
                    DocumentSnapshot document = task.getResult();

                    ArrayList<Viaje> listaTareas;


                    if (document.exists()) {

                        listaTareas = (ArrayList<Viaje>) document.get("actextra");
                        listaTareas.add(viaje);


                    } else {
                        listaTareas = new ArrayList<>();
                        listaTareas.add(viaje);
                    }

                    datos.put("actextra", listaTareas);


                    db.collection("actextra").document(id).set(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(Fragment_Dialog.this.getContext(), "Datos añadidos correctamente", Toast.LENGTH_LONG).show();

                                tituloActividad.setText("");
                                fecha.setText("");
                                spinnerGrupo.setSelection(1);


                            } else {


                            }


                        }
                    });


                }


            }
        });


    }

}
