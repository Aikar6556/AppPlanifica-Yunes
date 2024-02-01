package com.cifpceuta.pruebasqllite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

        public static int obtenerSemanaDelMes(LocalDate fecha) {
        int diaDelMes = fecha.getDayOfMonth();
        int diaDeLaSemana = fecha.getDayOfWeek().getValue();

        int semanaDelMes = (diaDelMes - 1) / 7 + 1;

        if (diaDelMes <= 7 && diaDeLaSemana > diaDelMes) {
            semanaDelMes++;
        }

        return semanaDelMes;
    }

        @Override
        public void onBindViewHolder(@NonNull MyArrayAdapterPracticas.ViewHolder holder, int position) {


          try{

              holder.bindData(practicas.get(position));


          }catch (ClassCastException castException){

              holder.bindData(new Practica((Map<String,Object>) practicas.get(position)));


          }

            String fecha = holder.fechafinal.getText().toString();

            String formatoFecha = "dd/MM/yyyy";

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatoFecha);

            LocalDate localDate = LocalDate.parse(fecha,dateTimeFormatter);

            Log.d("Semana num",""+obtenerSemanaDelMes(localDate));



            System.out.printf(String.valueOf(localDate));


            LocalDate fechaActual = LocalDate.now();

            int diasRestantes = (int) ChronoUnit.DAYS.between(fechaActual,localDate);

            Log.d("Días restantes", String.valueOf(diasRestantes));

            int color;


            // Comparar las fechas
            if (diasRestantes <=3) {
                color = Color.parseColor("#FFCDD2");
                holder.cardView.setCardBackgroundColor(color);


            } else if (diasRestantes >=5 && diasRestantes <=7) {
                color = Color.parseColor("#FFC107");
                holder.cardView.setCardBackgroundColor(color);


            } else {
                color = Color.parseColor("#4CAF50");

                holder.cardView.setCardBackgroundColor(color);



            }



        }





        @Override
        public int getItemCount() {
            return practicas.size();
        }

    public void setFilterList(ArrayList<Practica> lista){
        practicas = lista;
        notifyDataSetChanged();
    }





        class ViewHolder extends RecyclerView.ViewHolder {

            TextView grupo,materia,contenido,fechainicio,fechafinal;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                cardView= itemView.findViewById(R.id.cardView);
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

               // cardView.setCardBackgroundColor(Color.GREEN);

            }







        }




    }
