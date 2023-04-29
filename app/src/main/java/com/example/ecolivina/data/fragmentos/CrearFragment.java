package com.example.ecolivina.data.fragmentos;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecolivina.R;
import com.example.ecolivina.data.MainActivity;
import com.example.ecolivina.data.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class CrearFragment extends Fragment {
    int idtipo;
    TextView textTipo, textCat;
    EditText editPeso, editCantidad, editDescrip;
    Button btnCrear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear, container, false);
        
        // Obtener la referencia al ConstraintLayout del main activity
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        ConstraintLayout UILayout = mainActivity.findViewById(R.id.UILayout);

        // Ocultamos el ConstraintLayout
        UILayout.setVisibility(View.INVISIBLE);
        // Cambiamos la altura del ConstraintLayout del main activity
        ConstraintLayout.LayoutParams newParams = (ConstraintLayout.LayoutParams) UILayout.getLayoutParams();
        newParams.height= 1;
        UILayout.setLayoutParams(newParams);

        // Agregar el LifecycleObserver al ciclo de vida del fragmento
        getLifecycle().addObserver(lifecycleObserver);

        Bundle bundle = getArguments();
        if (bundle != null) {
            idtipo = bundle.getInt("idtipo");
            // hacer algo con el valor
        }



        Toast.makeText(getActivity(), "idtipo: "+idtipo, Toast.LENGTH_SHORT).show();

        //Declaración de las variables necesarias
        textTipo = view.findViewById(R.id.textTipo);
        editPeso = view.findViewById(R.id.editPeso);
        editCantidad = view.findViewById(R.id.editCantidad);
        editDescrip = view.findViewById(R.id.editDescrip);
        btnCrear = view.findViewById(R.id.btnCrear);

        textTipo.setText("Tipo: "+idtipo);
        campos();

        return view;

    }
    private final LifecycleObserver lifecycleObserver = new LifecycleObserver() {
        // Cuando el fragmento se destruye
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPaused() {
            // Obtener la referencia al ConstraintLayout del main activity
            MainActivity mainActivity = (MainActivity) getActivity();
            ConstraintLayout UILayout;

            {
                assert mainActivity != null;
                UILayout = mainActivity.findViewById(R.id.UILayout);
            }
            // Restablecer la restricción de ancho para los botones
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) UILayout.getLayoutParams();
            UILayout.setVisibility(View.VISIBLE);
            layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            UILayout.setLayoutParams(layoutParams);
        }
    };

    private void campos(){
        //Validación de los campos
            // Creamos el Array de todos los campos
            EditText[] listCampos = {editPeso, editCantidad, editDescrip};

            //Iteramos el array para ver si hay algun campo vacio
            for (EditText listCampo : listCampos) {
                String campoTxt = listCampo.getText().toString();
                if (campoTxt.isEmpty()) {
                    //Añadimos el error y avisamos al usuario
                    listCampo.setError("El campo es obligatorio");
                    Toast.makeText(getContext(), "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        }
    }