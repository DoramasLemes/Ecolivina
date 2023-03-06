package com.example.ecolivina.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ecolivina.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<List<String>> listaProductos;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se crea el recycler view
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ejecutarServicio("http://10.0.2.2/ecolivina/consulta.php");

    }

    //Metodo buscar los productos de Ecolivina en MySQL
    private void ejecutarServicio(String URL){
        //Se crea la cola de peticiones, si la peticion es correcta se ejecuta el codigo de la funcion onResponse
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL,response -> {
            //Se crea la lista de productos
            listaProductos = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    listaProductos.add(new ArrayList<String>(Arrays.asList(jsonObject.getString("nombre"))));
                    listaProductos.add(new ArrayList<String>(Arrays.asList(jsonObject.getString("precio"))));
                    listaProductos.add(new ArrayList<String>(Arrays.asList(jsonObject.getString("peso"))));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error en la iteracion de los datos", Toast.LENGTH_SHORT).show();
                }
            }
            //Se crea el adaptador
            AdapterProductos adaptadorProductos = new AdapterProductos(listaProductos);
            recycler.setAdapter(adaptadorProductos);
        }, error -> {
            Toast.makeText(MainActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
        });

        };
    }