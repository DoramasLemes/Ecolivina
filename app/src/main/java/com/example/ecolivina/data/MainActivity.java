package com.example.ecolivina.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<List<String>> listaProductos;
    RecyclerView recycler;

    String emailUser, passwordUser;

    //Declaracion del BottomMenu
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView= findViewById(R.id.navigationView);

        //Se configura el listener para el bottom menu
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_favs:
                    Toast.makeText(MainActivity.this, "Favoritos", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_subir:
                    Toast.makeText(MainActivity.this, "Subir", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_buzon:
                    Toast.makeText(MainActivity.this, "Buzon", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_perfil:
                    cambiarActividad(perfilMain.class);
                    break;
            }
            return true;
        });

        //Se reciben los datos del usuario
        try {
            recibirDatos();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Error al recibir los datos del usuario", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(MainActivity.this, "El mail del usuario es: " +emailUser + "\n La contraseña del usuario es: " + passwordUser, Toast.LENGTH_SHORT).show();

        //Se crea el recycler view
        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ejecutarServicio("http://10.0.2.2/ecolivina/productos.php");

    }

    //Metodo para cambiar de actividad
    public void cambiarActividad(Class actividad){
        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }

    //Metodo para recibir los datos del usuario
    public void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        emailUser = extras.getString("email");
        passwordUser = extras.getString("password");
        Toast.makeText(MainActivity.this, "El mail del usuario es: " +emailUser + "\n La contraseña del usuario es: " + passwordUser, Toast.LENGTH_SHORT).show();
    }

    //Metodo buscar los productos de Ecolivina en MySQL
    private void ejecutarServicio(String URL){
        //Se crea la cola de peticiones, si la peticion es correcta se ejecuta el codigo de la funcion onResponse
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL,response -> {
            //Se crea la lista de productos
            listaProductos = new ArrayList<>();

            //Se recorren los datos de la respuesta
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    listaProductos.add(new ArrayList<String>(Arrays.asList(jsonObject.getString("tipo"), jsonObject.getString("precio"), jsonObject.getString("peso"),jsonObject.getString("img"))));
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
        //Se agrega la peticion a la cola
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        };
    }