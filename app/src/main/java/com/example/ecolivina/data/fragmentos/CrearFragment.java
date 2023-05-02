package com.example.ecolivina.data.fragmentos;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ecolivina.R;
import com.example.ecolivina.data.AdapterTipos;
import com.example.ecolivina.data.MainActivity;
import com.example.ecolivina.data.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CrearFragment extends Fragment {
    int idTipo, idCategoria;
    String nombreTipo, imgTipo;
    ImageView viewTipo, viewCat;
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
        newParams.height = 1;
        UILayout.setLayoutParams(newParams);

        // Agregar el LifecycleObserver al ciclo de vida del fragmento
        getLifecycle().addObserver(lifecycleObserver);

        //Declaración de las variables necesarias
        viewTipo = view.findViewById(R.id.imageViewTipo);
        textTipo = view.findViewById(R.id.textTipo);
        viewCat = view.findViewById(R.id.imageViewCat);
        textCat = view.findViewById(R.id.textCat);
        editPeso = view.findViewById(R.id.editPeso);
        editCantidad = view.findViewById(R.id.editCantidad);
        editDescrip = view.findViewById(R.id.editDescrip);
        btnCrear = view.findViewById(R.id.btnCrear);

        Bundle bundle = getArguments();
        if (bundle != null) {
            idTipo = bundle.getInt("idtipo");
            nombreTipo = bundle.getString("nombre");
            imgTipo = bundle.getString("img");
            idCategoria = bundle.getInt("idCategoria");
            textTipo.setText(nombreTipo);
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(imgTipo)
                    .centerCrop()
                    .into(viewTipo);
        }

        ejecutarServicio("http://10.0.2.2/ecolivina/categorias/fetch.php?id="+idCategoria);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campos();
            }
        });

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

    private void campos() {
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
            } else {

            }
        }
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
                            textCat.setText(response.getString("nombre"));
                            Glide.with(Objects.requireNonNull(getContext()))
                                    .load(response.getString("img"))
                                    .centerCrop()
                                    .into(viewTipo);

                            } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error en la petición ".concat(error.toString()), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(request);

    }
}