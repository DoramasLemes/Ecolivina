package com.example.ecolivina.data.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;
import com.example.ecolivina.data.AdapterProductos;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TipoFragment extends Fragment {

    Spinner spinner;
    ArrayList<List<String>> listaProductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo, container, false);







        return view;

    }

    private void ejecutarServicio(String URL){
        //Se crea la cola de peticiones, si la peticion es correcta se ejecuta el codigo de la funcion onResponse
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            //Se crea la lista de productos
            listaProductos = new ArrayList<>();

            //Se recorren los datos de la respuesta
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    listaProductos.add(new ArrayList<>(Arrays.asList(jsonObject.getString("nombre"))));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error en la iteracion de los datos", Toast.LENGTH_SHORT).show();
                }
            }
            //Se crea el adaptador
            //ArrayAdapter<CharAdapter> adapter = ArrayAdapter.createFromResource(getContext(), listaProductos, android.R.layout.simple_spinner_item);

            //spinner.setAdapter(adapter);
        }, error -> {
            Toast.makeText(getActivity(), "Error en la petición", Toast.LENGTH_SHORT).show();
        });
        //Se agrega la peticion a la cola
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(jsonArrayRequest);
    }

}