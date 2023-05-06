package com.example.ecolivina.data.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;
import com.example.ecolivina.data.AdapterTipos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TipoFragment extends Fragment {

    ArrayList<List<?>> listaTipos;
    RecyclerView recycler;
    AdapterTipos adapterTipos;
    Fragment fragment = new CrearFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo, container, false);

        //Se crea el recycler view
        recycler = view.findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ejecutarServicio("http://10.0.2.2/ecolivina/tipos/fetchAll.php");

        return view;

    }

    private void ejecutarServicio(String URL){
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaTipos = new ArrayList<>();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        try {
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(0);
                                if (jsonObject.has("id")) {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject object = response.getJSONObject(i);
                                        int id = object.getInt("id");
                                        String nombre = object.getString("nombre");
                                        String img = object.getString("img");
                                        int idCategoria = object.getInt("idcategoria");

                                        listaTipos.add(new ArrayList<>(Arrays.asList(id, nombre, img, idCategoria)));
                                        adapterTipos = new AdapterTipos(listaTipos);

                                        adapterTipos.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int idTipoClick = (int) listaTipos.get(recycler.getChildAdapterPosition(v)).get(0);
                                                String nombreTipoClick = listaTipos.get(recycler.getChildAdapterPosition(v)).get(1).toString();
                                                String imgTipoClick = listaTipos.get(recycler.getChildAdapterPosition(v)).get(2).toString();
                                                int idCategoriaClick = (int) listaTipos.get(recycler.getChildAdapterPosition(v)).get(3);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("idtipo", idTipoClick);
                                                bundle.putString("nombre", nombreTipoClick);
                                                bundle.putString("img", imgTipoClick);
                                                bundle.putInt("idCategoria", idCategoriaClick);
                                                Bundle bundleGet = getArguments();
                                                int iduser = bundleGet.getInt("iduser");
                                                String nombre = bundleGet.getString("nombreUser");
                                                bundle.putInt("iduser", iduser);
                                                bundle.putString("nombreUser", nombre);
                                                fragment.setArguments(bundle);
                                                fragmentTransaction.replace(R.id.container, fragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }
                                        });

                                        recycler.setAdapter(adapterTipos);

                                    }
                                } else {
                                    // hacer algo si la respuesta no tiene el formato esperado
                                    throw new JSONException("El valor de la clave 'idtipo' no se encontró en la respuesta del servidor");
                                }
                            } else {
                                // la respuesta está vacía
                                throw new JSONException("Respuesta del servidor vacía");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException("Error al procesar la respuesta del servidor: " + e.getMessage());
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