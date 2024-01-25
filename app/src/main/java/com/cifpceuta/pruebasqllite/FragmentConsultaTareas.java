package com.cifpceuta.pruebasqllite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentConsultaTareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConsultaTareas extends Fragment {

    RecyclerView recyclerView;
    MyArrayAdapterPracticas myArrayAdapterPracticas;




    public FragmentConsultaTareas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConsultaTareas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConsultaTareas newInstance(String param1, String param2) {
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

        recyclerView = rootView.findViewById(R.id.rv_elementos);



        return rootView;
    }
}