package com.example.ecolivina.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ecolivina.R;
import com.example.ecolivina.data.fragmentos.FavFragment;
import com.example.ecolivina.data.fragmentos.HomeFragment;
import com.example.ecolivina.data.fragmentos.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String emailUser, passwordUser;
    //Declaracion del BottomMenu
    BottomNavigationView navigationView;
    HomeFragment homeFragment = new HomeFragment();
    FavFragment favFragment = new FavFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se reciben los datos del usuario
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, perfilFragment);
        //Se configura el bottom menu
        navigationView= findViewById(R.id.navigationView);

        //Se configura el fragmento inicial
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        //Se configura el listener para el bottom menu
        navigationView.setOnItemSelectedListener(item -> {
            changeFragment(item.getItemId());
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

    //Metodo para cambiar de fragmento
    private void changeFragment(int id){
        switch (id){
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                break;
            case R.id.navigation_favs:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, favFragment).commit();
                break;
            case R.id.navigation_subir:
                Toast.makeText(MainActivity.this, "Subir", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_buzon:
                Toast.makeText(MainActivity.this, "Buzon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, perfilFragment).commit();
                break;
        }
    }

    //Metodo para recibir los datos del usuario
    public void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        emailUser = extras.getString("email");
        passwordUser = extras.getString("password");
        Toast.makeText(MainActivity.this, "El mail del usuario es: " +emailUser + "\n La contraseña del usuario es: " + passwordUser, Toast.LENGTH_SHORT).show();
    }
}