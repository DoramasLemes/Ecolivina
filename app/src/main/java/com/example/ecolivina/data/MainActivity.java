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
import com.example.ecolivina.data.fragmentos.FavFragment;
import com.example.ecolivina.data.fragmentos.HomeFragment;
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
    HomeFragment homeFragment = new HomeFragment();
    FavFragment favFragment = new FavFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView= findViewById(R.id.navigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        //Se configura el listener para el bottom menu
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    break;
                case R.id.navigation_favs:
                    Toast.makeText(MainActivity.this, "Favoritos", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, favFragment).commit();
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
}