package com.example.ecolivina.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Declaración de las variables necesarias
    EditText name, apellidos, username, email, password, password2, edad;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Declaración de las variables necesarias
        name = findViewById(R.id.nombreRegister);
        apellidos = findViewById(R.id.apellidosRegister);
        username = findViewById(R.id.usernameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        password2 = findViewById(R.id.passwordRepeatRegister);
        edad = findViewById(R.id.edadRegister);
        btn_register = findViewById(R.id.btnRegister);

        //Validación de los campos
        btn_register.setOnClickListener(v -> {

            // Creamos el Array de todos los campos
            EditText[] listCampos = {name, apellidos, username, email
                    , apellidos, email, password, password2, edad};

            //Iteramos el array para ver si hay algun campo vacio
            for (EditText listCampo : listCampos) {
                String campoTxt = listCampo.getText().toString();
                if (campoTxt.isEmpty()) {
                    //Añadimos el error y avisamos al usuario
                    listCampo.setError("El campo es obligatorio");
                    Toast.makeText(RegisterActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().equals(password2.getText().toString())){
                    //Si las contraseñas son iguales, ejecutamos el servicio
                    ejecutarServicio("http://localhost:8080/ecoLivina/registrar_usuario.php");
            }else{
                    //Si las contraseñas no son iguales, avisamos al usuario
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    password.setError("Las contraseñas no coinciden");
                    password2.setError("Las contraseñas no coinciden");
                }
        }
    });
    }

    //Metodo para registrar un usuario en MySQL
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
            }
        }){
            //Añadimos los parametros que vamos a enviar al servidor
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("nombre", name.getText().toString());
                parametros.put("apellidos", apellidos.getText().toString());
                parametros.put("username", username.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("password", password.getText().toString());
                parametros.put("edad", edad.getText().toString());
                return parametros;
            }
        };
        //Creamos la cola de peticiones para que la libreria las ejecute
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo para ir a la pantalla de login
    public void loginButton(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}