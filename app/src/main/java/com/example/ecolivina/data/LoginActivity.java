package com.example.ecolivina.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecolivina.R;


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
        btn_login = (Button) findViewById(R.id.login);

        //Validación de los campos
        btn_login.setOnClickListener(v -> {
            String emailUser = email.getText().toString();
            String passwordUser = password.getText().toString();

            //Validacion de introduccion de los campos
            if (emailUser.isEmpty() && passwordUser.isEmpty()) {
                email.setError("El campo email es obligatorio");
                password.setError("El campo contraseña es obligatorio");
                Toast.makeText(LoginActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo para ir a la pantalla de registro
    public void registerButton(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}