package com.example.ecolivina.data;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecolivina.R;

public class RegisterActivity extends AppCompatActivity {

    //Declaración de las variables necesarias
    EditText name, apellidos, username, email, password, password2, edad;
    Button btn_register, btn_login;
    
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
            EditText [] listCampos = {name,apellidos, username, email
                    , apellidos, email, password, password2, edad};

            //Iteramos el array para ver si hay algun campo vacio
            for (EditText listCampo : listCampos) {
                String campoTxt = listCampo.getText().toString();
                if (campoTxt.isEmpty()) {
                    //Añadimos el error y avisamos al usuario
                    listCampo.setError("El campo es obligatorio");
                    Toast.makeText(RegisterActivity.this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}