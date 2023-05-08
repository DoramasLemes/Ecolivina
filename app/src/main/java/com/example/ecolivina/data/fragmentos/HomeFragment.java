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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolivina.R;
import com.example.ecolivina.data.AdapterProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    ArrayList<List<String>> listaProductos;
    Fragment fragment = new ProductFragment();
    RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Se crea el recycler view
        recycler = view.findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ejecutarServicio("http://10.0.2.2/ecolivina/productos/fetchAll.php");
        return view;
    }
    //Metodo buscar los productos de Ecolivina en MySQL
    private void ejecutarServicio(String URL) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaProductos = new ArrayList<>();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        try {
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(0);
                                if (jsonObject.has("idproducto")) {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject object = response.getJSONObject(i);
                                        String id = String.valueOf(object.getInt("idproducto"));
                                        String tipo = object.getString("nombre_tipo");
                                        String precio = object.getString("precio");
                                        String peso = object.getString("peso");
                                        String img = object.getString("img");

                                        listaProductos.add(new ArrayList<>(Arrays.asList(id, tipo, precio, peso, img)));
                                    }
                                    AdapterProductos adapterProductos = new AdapterProductos(listaProductos);
                                    adapterProductos.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String idProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(0).toString();
                                            String tipoProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(1).toString();
                                            String precioProducto = listaProductos.get(recycler.getChildAdapterPosition(v)).get(2).toString();
                                            String pesoProducto = listaProductos.get(recycler.getChildAdapterPosition(v)).get(3).toString();
                                            String imagenProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(4).toString();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", idProducto);
                                            bundle.putString("tipo", tipoProducto);
                                            bundle.putString("precio", precioProducto);
                                            bundle.putString("peso", pesoProducto);
                                            bundle.putString("imagen", imagenProducto);
                                            Bundle bundleGet = getArguments();
                                            if (bundleGet != null) {
                                                int telefono = bundleGet.getInt("telefono");
                                                String username = bundleGet.getString("username");
                                                bundle.putInt("telefono", telefono);
                                                bundle.putString("username", username);
                                            }
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.replace(R.id.container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });
                                    recycler.setAdapter(adapterProductos);

                                } else {
                                    // hacer algo si la respuesta no tiene el formato esperado
                                    throw new JSONException("El valor de la clave 'idproducto' no se encontró en la respuesta del servidor");
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

    public void fetchByName(String URL) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaProductos = new ArrayList<>();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        try {
                            if (response.length() > 0) {
                                JSONObject jsonObject = response.getJSONObject(0);
                                if (jsonObject.has("idproducto")) {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject object = response.getJSONObject(i);
                                        String id = String.valueOf(object.getInt("idproducto"));
                                        String tipo = object.getString("nombre_tipo");
                                        String precio = object.getString("precio");
                                        String peso = object.getString("peso");
                                        String img = object.getString("img");

                                        listaProductos.add(new ArrayList<>(Arrays.asList(id, tipo, precio, peso, img)));
                                    }
                                    AdapterProductos adapterProductos = new AdapterProductos(listaProductos);
                                    adapterProductos.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String idProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(0).toString();
                                            String tipoProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(1).toString();
                                            String precioProducto = listaProductos.get(recycler.getChildAdapterPosition(v)).get(2).toString();
                                            String pesoProducto = listaProductos.get(recycler.getChildAdapterPosition(v)).get(3).toString();
                                            String imagenProducto = (String) listaProductos.get(recycler.getChildAdapterPosition(v)).get(4).toString();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", idProducto);
                                            bundle.putString("tipo", tipoProducto);
                                            bundle.putString("precio", precioProducto);
                                            bundle.putString("peso", pesoProducto);
                                            bundle.putString("imagen", imagenProducto);
                                            Bundle bundleGet = getArguments();
                                            if (bundleGet != null) {
                                                int telefono = bundleGet.getInt("telefono");
                                                String username = bundleGet.getString("username");
                                                bundle.putInt("telefono", telefono);
                                                bundle.putString("username", username);
                                            }
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.replace(R.id.container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });
                                    recycler.setAdapter(adapterProductos);

                                } else {
                                    // hacer algo si la respuesta no tiene el formato esperado
                                    throw new JSONException("El valor de la clave 'idproducto' no se encontró en la respuesta del servidor");
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