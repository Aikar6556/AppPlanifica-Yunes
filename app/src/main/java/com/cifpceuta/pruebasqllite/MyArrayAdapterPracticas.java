package com.cifpceuta.pruebasqllite;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

    public class MyArrayAdapterPracticas extends RecyclerView.Adapter<MyArrayAdapterPracticas.ViewHolder> {


    ArrayList<Practica> practicas;


        public MyArrayAdapterPracticas(ArrayList<Practica> practicas) {
            this.practicas = practicas;
        }

        @NonNull
        @Override
        public MyArrayAdapterPracticas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_de_cada_tarea,parent,false);
            return new MyArrayAdapterPracticas.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyArrayAdapterPracticas.ViewHolder holder, int position) {
            holder.bindData(practicas.get(position));

        }

        @Override
        public int getItemCount() {
            return practicas.size();
        }





        class ViewHolder extends RecyclerView.ViewHolder {

            TextView grupo,materia,contenido,fechainicio,fechafinal;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                grupo = itemView.findViewById(R.id.textViewGrupo);
                materia = itemView.findViewById(R.id.textViewAsignatura);
                contenido = itemView.findViewById(R.id.textViewContenido);
                fechainicio = itemView.findViewById(R.id.textViewFechaInicio);
                fechafinal = itemView.findViewById(R.id.textViewFechaFinal);

            }

            void bindData(Practica practica){
                grupo.setText(practica.getGrupo());
                materia.setText(practica.getModulo());
                contenido.setText(practica.getDescrtiption());
                fechainicio.setText(practica.getFechaInicio());
                fechafinal.setText(practica.getFechaFinal());

            }


        }



    }
