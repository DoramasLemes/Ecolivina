package com.example.ecolivina.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;
import com.example.ecolivina.data.fragmentos.FavFragment;
import com.example.ecolivina.data.fragmentos.HomeFragment;
import com.example.ecolivina.data.fragmentos.PerfilFragment;
import com.example.ecolivina.data.fragmentos.TipoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String emailUser, passwordUser;
    //Declaracion del BottomMenu
    BottomNavigationView navigationView;
    HomeFragment homeFragment = new HomeFragment();
    FavFragment favFragment = new FavFragment();
    TipoFragment tipoFragment = new TipoFragment();
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
            case R.id.navigation_subir:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, tipoFragment).commit();
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
        String URL = "http://10.0.2.2/ecolivina/usuarios/fetch.php?email="+emailUser;
        ejecutarServicio(URL);
    }

    private void ejecutarServicio(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int iduser = response.getInt("iduser");
                            String nombre = response.getString("nombre");
                            String apellidos = response.getString("apellidos");
                            String username = response.getString("username");
                            String email = response.getString("email");
                            String password = response.getString("password");
                            int telefono = response.getInt("telefono");
                            Bundle bundle = new Bundle();
                            bundle.putInt("iduser", iduser);
                            bundle.putString("nombre", nombre);
                            bundle.putString("apellidos", apellidos);
                            bundle.putString("username", username);
                            bundle.putString("email", email);
                            bundle.putString("password", password);
                            bundle.putInt("telefono", telefono);
                            System.out.println("El id del usuario es: " + iduser);
                            tipoFragment.setArguments(bundle);
                            homeFragment.setArguments(bundle);
                            perfilFragment.setArguments(bundle);

                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error en la petición ".concat(error.toString()), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }
}