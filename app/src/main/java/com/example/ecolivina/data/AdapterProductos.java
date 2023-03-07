package com.example.ecolivina.data;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecolivina.R;

import java.util.ArrayList;
import java.util.List;

//Creacion del adaptador para mostrar cada producto en el recycler view
public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolder>{

    ArrayList<List<String>> listaProductos;

    public AdapterProductos(ArrayList<List<String>> listaProductos) {
        this.listaProductos = listaProductos;
    }

    //Se infla el view holder
    @NonNull
    @Override
    public AdapterProductos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_producto, parent, false);
        return new ViewHolder(view);
    }

    //Comunica adaptador con viewHolder y se añaden los datos de cada producto
    @Override
    public void onBindViewHolder(@NonNull AdapterProductos.ViewHolder holder, int position) {
        //for (int i = 0; i < listaProductos.size(); i++) {
            holder.asignarProductos(listaProductos.get(position).get(0), listaProductos.get(position).get(1), listaProductos.get(position).get(2));
        //}
    }

    //Devuelve el tamaño de la lista de productos
    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    //Se crea el view holder para mostrar los datos de cada producto
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreProducto, precioProducto, pesoProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto=itemView.findViewById(R.id.nombreProducto);
            precioProducto=itemView.findViewById(R.id.precioProducto);
            pesoProducto=itemView.findViewById(R.id.pesoProducto);
        }

        //Se asignan los datos de cada producto
        @SuppressLint("SetTextI18n")
        public void asignarProductos(String s, String s1, String s2) {
            nombreProducto.setText(s);
            precioProducto.setText(s1 + "/Kg");
            pesoProducto.setText(s2 + "Kg");
        }
    }
}
