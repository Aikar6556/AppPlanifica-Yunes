package com.cifpceuta.pruebasqllite;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class MyArrayAdapterEstudiantes extends RecyclerView.Adapter<MyArrayAdapterEstudiantes.ViewHolder> {


    ArrayList<Estudiante> estudiantes = new ArrayList<>();
    ArrayList<Estudiante> estudiantesBD = new ArrayList<>();



    public MyArrayAdapterEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        estudiantesBD = estudiantes;
    }

    @NonNull
    @Override
    public MyArrayAdapterEstudiantes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_gestion_alumnos,parent,false);
        return new MyArrayAdapterEstudiantes.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyArrayAdapterEstudiantes.ViewHolder holder, int position) {

        try{

            holder.bindData(estudiantes.get(position));


        }catch (ClassCastException castException){

            //holder.bindData(new Estudiante((Map<String,Object>) estudiantes.get(position)));


        }


    }

    @Override
    public int getItemCount() {
        return estudiantes.size();
    }

    public void setFilterList(String grupo){

        estudiantes = estudiantesBD;

        Log.w("contenido lista", "Num: " + estudiantes);

        ArrayList<Estudiante> filtra = new ArrayList<>();

        for (int i =0;i<estudiantes.size();i++) {

            if (estudiantes.get(i).getGrupo().equalsIgnoreCase(grupo)) {
                filtra.add(estudiantes.get(i));
            }
    }

        estudiantes = filtra;
        notifyDataSetChanged();
    }






    class ViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView alumno, correo, inicial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_elemento_mostrar);
            alumno = itemView.findViewById(R.id.tv_nombre_usuario);
            correo = itemView.findViewById(R.id.tv_correo_usuario);
            inicial = itemView.findViewById(R.id.txtInicial);
        }


        void bindData(Estudiante es){

            alumno.setText(es.getNombre().toString());
            correo.setText(es.getCorreo().toString());
            inicial.setText(es.getNombre().substring(0,1));

        }






    }




}
