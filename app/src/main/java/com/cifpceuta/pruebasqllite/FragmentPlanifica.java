package com.cifpceuta.pruebasqllite;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPlanifica#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlanifica extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Spinner spinnerGrupo, spinnerModulo;
    EditText tarea, fechaInicio, fechaFinal, description;
    Button enviar;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public FragmentPlanifica() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPlanifica newInstance(String param1, String param2) {
        FragmentPlanifica fragment = new FragmentPlanifica();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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


        View rootView = inflater.inflate(R.layout.fragment_planifica, container, false);
        spinnerGrupo = rootView.findViewById(R.id.spGrupo);
        spinnerModulo = rootView.findViewById(R.id.spModulo);
        tarea = rootView.findViewById(R.id.etTarea);
        fechaFinal = rootView.findViewById(R.id.etFechaFin);
        fechaInicio = rootView.findViewById(R.id.etFechaInicio);
        description = rootView.findViewById(R.id.etDescription);
        enviar = rootView.findViewById(R.id.btGuardar);



        String[] grupos = {"1DAM","2DAM"};
        String[] modulos = {"BD","ED","FOL","LM","PR","SI","AD","DI","EMP","PSP","PMDM","SGE",""};

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item,grupos);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item,modulos);
        spinnerGrupo.setAdapter(adaptador);
        spinnerModulo.setAdapter(adaptador2);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirDatos();
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }

    public void subirDatos(){



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String tareaa = tarea.getText().toString();
        String fechaInicioo = fechaInicio.getText().toString();
        String fechaFinaaal = fechaFinal.getText().toString();
        String descriptioon = description.getText().toString();
        String grupoo = spinnerGrupo.getSelectedItem().toString();
        String moduloo = spinnerModulo.getSelectedItem().toString();

        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("user", mAuth.getCurrentUser().getUid().toString());
        data.put("Tarea", tareaa);
        data.put("Fecha Inicio", fechaInicioo);
        data.put("Fecha Fin", fechaFinaaal);
        data.put("Grupo", grupoo);
        data.put("Módulo", moduloo);
        data.put("Descripción", descriptioon);


        db.collection("tareas").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(FragmentPlanifica.this.getContext(),"Datos añadidos correctamente",Toast.LENGTH_LONG).show();

                        tarea.setText("");
                        fechaInicio.setText("");
                        fechaFinal.setText("");
                        description.setText("");
                        spinnerGrupo.setSelection(1);
                        spinnerModulo.setSelection(1);



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {

                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

}