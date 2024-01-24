package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioCuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioCuenta extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_CORREO = "correo";
    private static final String ARG_TURNO = "turno";
    private static final String ARG_GRUPO = "grupo";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    String nombre;
    String correo;
    String turno;
    String grupo;


    public InicioCuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioCuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioCuenta newInstance(String nombre, String correo, String turno, String grupo) {
        InicioCuenta fragment = new InicioCuenta();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_CORREO, correo);
        args.putString(ARG_TURNO, turno);
        args.putString(ARG_GRUPO, grupo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString(ARG_NOMBRE);
            correo = getArguments().getString(ARG_CORREO);
            turno = getArguments().getString(ARG_TURNO);
            grupo = getArguments().getString(ARG_GRUPO);

        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView userNombre;
        TextView userGrupo;
        TextView userEmail;
        TextView userTurno;
        View rootView =  inflater.inflate(R.layout.fragment_inicio_cuenta, container, false);

        // Obtener el correo electrónico de los argumentos
        if (getArguments() != null) {
            correo = getArguments().getString(ARG_CORREO);
            nombre = getArguments().getString(ARG_NOMBRE);
            turno = getArguments().getString(ARG_TURNO);
            grupo = getArguments().getString(ARG_GRUPO);
        }

        // Vincular el correo electrónico al TextView en el layout
        userNombre = rootView.findViewById(R.id.tvNombre);
        userNombre.setText(nombre);

        userEmail = rootView.findViewById(R.id.tvCorreo);
        userEmail.setText(correo);

        userGrupo = rootView.findViewById(R.id.tvGrupo);
        userGrupo.setText(grupo);

        userTurno = rootView.findViewById(R.id.tvTurno);
        userTurno.setText(turno);

        return rootView;
    }
}