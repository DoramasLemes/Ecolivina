package com.example.ecolivina.data.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ecolivina.R;
import com.example.ecolivina.data.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ProductFragment extends Fragment {

    String id, tipo, precio, peso, imagen;
    TextView textTipo, textCat, textDescrip, textPeso, textPrecio;
    ImageView viewProducto;
    ImageView btnChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

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
        viewProducto = view.findViewById(R.id.imageProducto);
        textTipo = view.findViewById(R.id.textViewTipo);
        textCat = view.findViewById(R.id.textViewCat);
        textDescrip = view.findViewById(R.id.textViewDescrip);
        textPeso = view.findViewById(R.id.textViewPeso);
        textPrecio = view.findViewById(R.id.textViewPrecio);
        btnChat = view.findViewById(R.id.btnChat);

        Bundle bundle = getArguments();
        if (bundle != null) {;
            id = bundle.getString("id");
            tipo = bundle.getString("tipo");
            precio = bundle.getString("precio");
            peso = bundle.getString("peso");
            imagen = bundle.getString("imagen");

            ejecutarServicio("http://10.0.2.2/ecolivina/productos/fetch.php?id="+id);

            textCat.setText(id);
            textTipo.setText(tipo);
            String precioText = precio+"€/Kg";
            textPrecio.setText(precioText);
            String pesoText = peso+"Kg";
            textPeso.setText(pesoText);
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(imagen)
                    .centerCrop()
                    .into(viewProducto);
        }

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
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
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
        }
    };

    private void ejecutarServicio(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("idproducto");
                            String precio = response.getString("precio");
                            String peso = response.getString("peso");
                            String descripcion = response.getString("descripcion");
                            int iduser = response.getInt("iduser");
                            String categoria = response.getString("categoria");
                            int idtipo = response.getInt("idtipo");
                            textCat.setText(categoria);
                            textDescrip.setText(descripcion);

                            String URL = "http://10.0.2.2/ecolivina/usuarios/fetchId.php?id="+iduser;
                            ejecutarServicio2(URL);

                            Bundle bundle = getArguments();
                            int telefono = bundle.getInt("telefono");
                            String username = bundle.getString("username");

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

    private void ejecutarServicio2(String URL) {
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
                            System.out.println("El telefono del usuario vendedor es: " + telefono+" y el username es: "+username);

                            btnChat.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = "https://wa.me/"+telefono;

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                }
                            });


                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error en la petición ".concat(error.toString()), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}