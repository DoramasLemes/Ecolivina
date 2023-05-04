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

//Creacion del adaptador para mostrar cada producto en el recycler view
public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolder> implements View.OnClickListener {

    ArrayList<List<String>> listaProductos;
    private View.OnClickListener listener;

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

        holder.asignarProductos(listaProductos.get(position).get(1), listaProductos.get(position).get(2), listaProductos.get(position).get(3));
        Glide.with(holder.itemView.getContext())
                .load(listaProductos.get(position).get(4))
                .centerCrop()
                .into(holder.imagenProducto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    //Obtener la posición del elemento seleccionado
                    int position = holder.getAdapterPosition();

                    //Obtener los datos correspondientes del elemento seleccionado en la lista de tipos
                    String nombreProducto = (String) listaProductos.get(position).get(1);
                    String precioProducto = (String) listaProductos.get(position).get(2);
                    String pesoProducto = (String) listaProductos.get(position).get(3);
                    String imagenProducto = (String) listaProductos.get(position).get(4);

                    //Llamar al método onClick del listener para ejecutar cualquier otra acción adicional
                    listener.onClick(v);
                }
            }});
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

    //Devuelve el tamaño de la lista de productos
    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    //Se crea el view holder para mostrar los datos de cada producto
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreProducto, precioProducto, pesoProducto;
        ImageView imagenProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreProducto=itemView.findViewById(R.id.nombre);
            precioProducto=itemView.findViewById(R.id.precioTipos);
            pesoProducto=itemView.findViewById(R.id.pesoProducto);
            imagenProducto=itemView.findViewById(R.id.imagenTipo);
        }

        //Se asignan los datos de cada producto
        @SuppressLint("SetTextI18n")
        public void asignarProductos(String s, String s1, String s2) {
            nombreProducto.setText(s);
            precioProducto.setText(s1 + "€/Kg");
            pesoProducto.setText(s2 + "Kg total");
        }
    }
}
