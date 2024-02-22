package com.cifpceuta.pruebasqllite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class MyArrayAdapterBorrar extends RecyclerView.Adapter<MyArrayAdapterBorrar.ViewHolder> {

    ArrayList<Practica> practicas;


    public MyArrayAdapterBorrar(ArrayList<Practica> practicas) {
        this.practicas = practicas;
    }



    @NonNull
    @Override
    public MyArrayAdapterBorrar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elemento_borrar,parent,false);
        return new MyArrayAdapterBorrar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArrayAdapterBorrar.ViewHolder holder, int position) {

        try{

            holder.bindData(practicas.get(position));


        }catch (ClassCastException castException){

            holder.bindData(new Practica((Map<String,Object>) practicas.get(position)));


        }





    }

    public int getItemCount() {
        return practicas.size();
    }

    public void setFilterList(ArrayList<Practica> lista){
        practicas = lista;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView contenido;
        CardView cardView;

        Button btnBorrar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv_elemento);
            contenido = itemView.findViewById(R.id.tv_nombre_elemento);
            btnBorrar = itemView.findViewById(R.id.btn_borrar_elemento);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Practica p = practicas.get(getAdapterPosition());
                    Toast.makeText(itemView.getContext(),"Elemento eliminado ",Toast.LENGTH_LONG).show();
                    practicas.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());


                }
            });



        }

        void bindData(Practica practica){

            contenido.setText(practica.getDescrtiption());




        }

    }



    }

