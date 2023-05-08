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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //Declaración de las variables necesarias
    EditText name, apellidos, username, email, password, password2, prefix, telefono;
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
        prefix = findViewById(R.id.prefijoRegister);
        telefono = findViewById(R.id.telefonoRegister);
        btn_register = findViewById(R.id.btnRegister);

        //Validación de los campos
        btn_register.setOnClickListener(v -> {

            // Creamos el Array de todos los campos
            EditText[] listCampos = {name, apellidos, username, email
                    , apellidos, password, password2, prefix, telefono};

            //Iteramos el array para ver si hay algun campo vacio
            for (EditText listCampo : listCampos) {
                String campoTxt = listCampo.getText().toString();
                if (campoTxt.isEmpty()) {
                    //Añadimos el error y avisamos al usuario
                    listCampo.setError("El campo es obligatorio");
                    Toast.makeText(RegisterActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }else if(!verificarEmail(email.getText().toString())){
                    //Si el email no es valido, avisamos al usuario
                    email.setError("El email no es valido");
                    Toast.makeText(RegisterActivity.this, "El email no es valido", Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().equals(password2.getText().toString())){
                    //Si las contraseñas son iguales, ejecutamos el servicio
                    ejecutarServicio("http://10.0.2.2/ecoLivina/registrar_usuario.php");
                }else{
                    //Si las contraseñas no son iguales, avisamos al usuario
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    password.setError("Las contraseñas no coinciden");
                    password2.setError("Las contraseñas no coinciden");
                }
            }
        });
    }

    private Boolean verificarEmail(String email){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^(.+)@(.+)$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    //Metodo para registrar un usuario en MySQL
    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            //Si la respuesta es exitosa, ejecutamos el metodo onResponse
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    //Si el registro es exitoso, vamos a la pantalla de login
                    loginButton(null);
                }else {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Si la respuesta es erronea, ejecutamos el metodo onErrorResponse
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            //Añadimos los parametros que vamos a enviar al servidor
            @Override
            protected Map<String, String> getParams() {
                String telefonoCompleto = prefix.getText().toString().concat(telefono.getText().toString());
                Map<String, String> parametros = new HashMap<>();
                parametros.put("nombre", name.getText().toString());
                parametros.put("apellidos", apellidos.getText().toString());
                parametros.put("username", username.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("password", password.getText().toString());
                parametros.put("telefono", telefonoCompleto);
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