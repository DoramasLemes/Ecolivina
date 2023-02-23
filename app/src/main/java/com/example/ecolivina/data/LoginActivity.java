package com.example.ecolivina.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    //Declaración de las variables necesarias
    EditText email, password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declaración de las variables necesarias
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.login);

        //Validación de los campos
        btn_login.setOnClickListener(v -> {
            String emailUser = email.getText().toString();
            String passwordUser = password.getText().toString();

            //Validacion de introduccion de los campos
            if (emailUser.isEmpty() && passwordUser.isEmpty()) {
                email.setError("El campo email es obligatorio");
                password.setError("El campo contraseña es obligatorio");
                Toast.makeText(LoginActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                //Si los campos cumplen los requisitos ejecutamos el servicio
                ejecutarServicio("http://localhost:8080/ecoLivina/validar_usuario.php");
            }
        });
    }
    //Metodo para loguear un usuario en MySQL
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error de login", Toast.LENGTH_SHORT).show();
            }
        }){
            //Agregamos los parametros que vamos a enviar al servicio
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", email.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };
        //Creamos la cola de peticiones para que la libreria las ejecute
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo para ir a la pantalla de registro
    public void registerButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}