package com.eb.appdemo.common.modulo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.KeeperMainPageActivity;
import com.eb.appdemo.common.util.Constantes;
import com.eb.appdemo.entidades.Modulo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleRegistroModuloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleRegistroModuloFragment extends Fragment {



    private NewModuloFragment moduloFragment;

    private Modulo newModulo = Modulo.getInstance();

    private MaterialTextView resumen_nombre_modulo,resumen_campo_modulo,
            resumen_tipo,resumen_fecha_inicio,resumen_semana_fumigacion,
            resumen_semanas_limpieza,resumen_semanas_cosecha,resumen_responsables;

    private MaterialButton add_new_module;


    public DetalleRegistroModuloFragment() {
        // Required empty public constructor
    }

    public DetalleRegistroModuloFragment(NewModuloFragment moduloFragment) {
        this.moduloFragment = moduloFragment;
        moduloFragment.setTitle(Constantes.DETALLE_NUEVO_MODULO);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleRegistroModuloFragment.
     */

    public static DetalleRegistroModuloFragment newInstance(String param1, String param2) {
        DetalleRegistroModuloFragment fragment = new DetalleRegistroModuloFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_registro_modulo, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hooks(view);

        setResumenContent();

        setActionAddModulo();
    }

    @Override
    public void onResume() {
        super.onResume();
        moduloFragment.setTitle(Constantes.DETALLE_NUEVO_MODULO);
    }

    private void hooks(View view) {
        resumen_nombre_modulo = view.findViewById(R.id.resumen_nombre_modulo);
        resumen_campo_modulo = view.findViewById(R.id.resumen_campo_modulo);
        resumen_tipo = view.findViewById(R.id.resumen_tipo);
        resumen_fecha_inicio = view.findViewById(R.id.resumen_fecha_inicio);
        resumen_semana_fumigacion = view.findViewById(R.id.resumen_semana_fumigacion);
        resumen_semanas_limpieza = view.findViewById(R.id.resumen_semanas_limpieza);
        resumen_semanas_cosecha = view.findViewById(R.id.resumen_semanas_cosecha);
        resumen_responsables = view.findViewById(R.id.resumen_responsables);
        add_new_module = view.findViewById(R.id.add_new_module);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setResumenContent(){

        try {
            resumen_nombre_modulo.setText(newModulo.getNombre_modulo());
            resumen_campo_modulo.setText(newModulo.getNombre_campo());
            resumen_tipo.setText(newModulo.getTipo_campo());
            resumen_fecha_inicio.setText(newModulo.getFechaInicio());
            //TODO escenario en caso las listas son null
            resumen_semana_fumigacion.setText(newModulo.getSemanaFumigacion().toString());
            resumen_semanas_limpieza.setText(newModulo.getSemanaLimpieza().toString());
            resumen_semanas_cosecha.setText(newModulo.getSemanaCosecha().toString());
            resumen_responsables.setText(newModulo.getResponsables().stream().map(r -> {
                return r.getName();
            }).collect(Collectors.toList()).toString());
        } catch (NullPointerException e) {
            e.getStackTrace();
        }

    }

    private void setActionAddModulo(){
        add_new_module.setOnClickListener(v -> {

            List<String> users = new ArrayList<>();
            //TODO add for future if contact is a user, add to users
            users.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
            newModulo.setUsers(users);

            DatabaseReference moduloReference = FirebaseDatabase.getInstance().getReference();

            moduloReference.child("modulos")
                    .child(newModulo.getId())
                    .setValue(newModulo).addOnSuccessListener(command -> {
                        newModulo = new Modulo();

            });

            Intent mainIntent = new Intent(getContext(), KeeperMainPageActivity.class);
            startActivity(mainIntent);

        });
    }
}