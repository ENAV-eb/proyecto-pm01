package com.eb.appdemo.common.modulo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.common.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleRegistroModuloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleRegistroModuloFragment extends Fragment {



    private NewModuloFragment moduloFragment;



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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((NewModuloFragment) getParentFragment()).setTitle("Resumen del Nuevo Módulo",this.getParentFragment().getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        moduloFragment.setTitle(Constantes.DETALLE_NUEVO_MODULO);
    }
}