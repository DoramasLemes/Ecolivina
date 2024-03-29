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
            if (emailUser.isEmpty() || passwordUser.isEmpty()) {
                email.setError("El campo email es obligatorio");
                password.setError("El campo contraseña es obligatorio");
                Toast.makeText(LoginActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                //Si los campos cumplen los requisitos ejecutamos el servicio
                ejecutarServicio("http://10.0.2.2/ecolivina/validar_usuario.php");
            }
        });
    }
    //Metodo para loguear un usuario en MySQL
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            //Si el servicio responde correctamente
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    //Si el login es exitoso vamos a la pantalla principal
                    goToMain();
                }else{
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Si el servicio responde con un error
            @Override
            public void onErrorResponse(VolleyError error) {
                String sError= error.toString();
                Toast.makeText(LoginActivity.this, sError, Toast.LENGTH_SHORT).show();
                System.out.println("Error consecuente: " + sError);
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
        //Se crea la cola de peticiones
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo para ir a la pantalla de registro
    public void registerButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Metodo para ir a la pantalla principal
    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        //Pasamos los datos del usuario a la pantalla principal
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
    }
}