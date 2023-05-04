package com.example.ecolivina.data;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecolivina.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTipos extends RecyclerView.Adapter<AdapterTipos.ViewHolder> implements View.OnClickListener{

    ArrayList<List<?>> listaTipos;
    private View.OnClickListener listener;
    public AdapterTipos(ArrayList<List<?>> listaTipos) {
        this.listaTipos = listaTipos;

    }

    //Se infla el view holder
    @NonNull
    @Override
    public AdapterTipos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tipos, parent, false);

        view.setOnClickListener(this);

        return new AdapterTipos.ViewHolder(view);
    }

    //Comunica adaptador con viewHolder y se añaden los datos de cada tipo
    @Override
    public void onBindViewHolder(@NonNull AdapterTipos.ViewHolder holder, int position) {

        holder.asignarTipos((String) listaTipos.get(position).get(1));
        Glide.with(holder.itemView.getContext())
                .load(listaTipos.get(position).get(2))
                .centerCrop()
                .into(holder.imagenTipo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(listener!=null){
                //Obtener la posición del elemento seleccionado
                int position = holder.getAdapterPosition();

                //Obtener los datos correspondientes del elemento seleccionado en la lista de tipos
                int idTipo = (int) listaTipos.get(position).get(0);
                String nombreTipo = (String) listaTipos.get(position).get(1);
                String imagenTipo = (String) listaTipos.get(position).get(2);
                int idCategoria = (int) listaTipos.get(position).get(3);

                //Llamar al método onClick del listener para ejecutar cualquier otra acción adicional
                listener.onClick(v);
            }
        }});
    }

    //Devuelve el tamaño de la lista de tipos
    @Override
    public int getItemCount() {
        return listaTipos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    //Se crea el view holder para mostrar los datos de cada tipo
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView imagenTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombre);
            imagenTipo=itemView.findViewById(R.id.imagenTipo);

        }

        //Se asignan los datos de cada tipo
        @SuppressLint("SetTextI18n")
        public void asignarTipos(String s) {
            nombre.setText(s);
        }

    }


}
