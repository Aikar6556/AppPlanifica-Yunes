package com.cifpceuta.pruebasqllite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyArrayAdapterViajes extends RecyclerView.Adapter<MyArrayAdapterViajes.ViewHolder> {

    ArrayList<Viaje> viajes;



    public MyArrayAdapterViajes(ArrayList<Viaje> viajes) {
        this.viajes = viajes;
    }



    @Override
    public MyArrayAdapterViajes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elemento,parent,false);


        return new MyArrayAdapterViajes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binddata(viajes.get(position));


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public void setFilterList(ArrayList<Viaje> lista){
        viajes = lista;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        TextView grupo,contenido,fecha;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            cardView = itemView.findViewById(R.id.cv_elemento);
            grupo = itemView.findViewById(R.id.textViewGrupo);
            contenido = itemView.findViewById(R.id.textViewContenido);
            fecha = itemView.findViewById(R.id.textViewFechaInicio);


        }


        void binddata(Viaje viaje){

            grupo.setText(viaje.getGrupo());
            contenido.setText(viaje.getNombreViaje());
            fecha.setText(viaje.getFecha());


        }
    }


}
