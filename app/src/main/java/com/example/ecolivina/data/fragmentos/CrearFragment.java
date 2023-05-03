package com.example.ecolivina.data.fragmentos;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.provider.MediaStore;
import android.util.Base64;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ecolivina.R;
import com.example.ecolivina.data.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class CrearFragment extends Fragment {
    String UPLOAD_URL = "http://10.0.2.2/ecolivina/productos/upload.php";
    Bitmap decode;
    int idCategoria;
    String nombreTipo, imgTipo;
    ImageView viewTipo, viewCat, productoImg;
    TextView textTipo, textCat;
    EditText editPeso, editCantidad, editDescrip;
    Button btnCrear;
    HomeFragment homeFragment = new HomeFragment();

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
        productoImg = view.findViewById(R.id.producto);
        viewTipo = view.findViewById(R.id.imageViewTipo);
        textTipo = view.findViewById(R.id.textTipo);
        viewCat = view.findViewById(R.id.imageViewCat);
        textCat = view.findViewById(R.id.textCat);
        editPeso = view.findViewById(R.id.editPeso);
        editCantidad = view.findViewById(R.id.editCantidad);
        editDescrip = view.findViewById(R.id.editDescrip);
        btnCrear = view.findViewById(R.id.btnCrear);

        Bundle bundle = getArguments();
        if (bundle != null) {;
            int iduser = bundle.getInt("iduser");
            Toast.makeText(getContext(), "iduser: "+iduser, Toast.LENGTH_SHORT).show();
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

        productoImg.setOnClickListener(v -> {showFileChooser();});
        btnCrear.setOnClickListener(v -> campos());

        return view;

    }

    public String getStringImagen(Bitmap bmp) {
        if (bmp != null) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
        } else {
            return null;
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"), 1);
    }

    private void setImageView(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        decode = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        productoImg.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try{
                Bitmap bmp = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(), filePath);
                setImageView(bmp);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
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
            }
        }
        if(editDescrip.getText().toString().length() > 100){
            editDescrip.setError("La descripción debe tener al máximo 100 caracteres");
        } else if (productoImg.getDrawable() == null) {
            Toast.makeText(getContext(), "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
        }else {
            uploadImage();
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
                            String nombre = response.getString("nombre");
                            String urlImagen = response.getString("img");
                            textCat.setText(nombre);

                            // Usa Glide para cargar la imagen desde la URL
                            Glide.with(requireContext())
                                    .load(urlImagen)
                                    .centerCrop()
                                    .into(viewCat);

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

    private String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImage() {

        final ProgressDialog loading = ProgressDialog.show(getContext(), "Subiendo...", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, homeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loading.dismiss();
                Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Bundle bundle = getArguments();
                int idtipo = bundle.getInt("idtipo");
                int iduser = bundle.getInt("iduser");
                String peso = editPeso.getText().toString().trim();
                String cantidad = editCantidad.getText().toString().trim();
                String descrip = editDescrip.getText().toString().trim();
                Map<String, String> params = new Hashtable<>();
                params.put("peso", peso);
                params.put("cantidad", cantidad);
                params.put("descripcion", descrip);
                params.put("imagen", getStringImage(decode));
                params.put("idtipo", String.valueOf(idtipo));
                params.put("iduser", String.valueOf(iduser));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(stringRequest);
    }

}