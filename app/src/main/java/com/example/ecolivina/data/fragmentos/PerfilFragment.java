package com.example.ecolivina.data.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecolivina.R;
import com.example.ecolivina.data.LoginActivity;
import com.example.ecolivina.data.MainActivity;

import java.util.Objects;

public class PerfilFragment extends Fragment {

    TextView txtEmail, txtUsername;
    Button btnTerminos, btnPoliticas, btnCerrarSesion, btnDelete;
    TerminosFragment terminosFragment = new TerminosFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

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

        txtEmail = view.findViewById(R.id.txtEmail);
        txtUsername = view.findViewById(R.id.txtUsername);
        btnTerminos = view.findViewById(R.id.btnTerminos);
        btnPoliticas = view.findViewById(R.id.btnPoliticas);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);

        Bundle bundle = getArguments();
        if (bundle != null) {;
            int iduser = bundle.getInt("iduser");
            String nombre = bundle.getString("nombre");
            String apellidos = bundle.getString("apellidos");
            String username = bundle.getString("username");
            String email = bundle.getString("email");
            String password = bundle.getString("password");
            int telefono = bundle.getInt("telefono");

            txtEmail.setText(email);
            txtUsername.setText(username);

        }

        btnTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, terminosFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnPoliticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoliticasFragment politicasFragment = new PoliticasFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, politicasFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return view;
        }

    // LifecycleObserver para el ciclo de vida del fragmento
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

}