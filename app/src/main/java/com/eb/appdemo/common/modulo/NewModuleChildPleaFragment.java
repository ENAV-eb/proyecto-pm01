package com.eb.appdemo.common.modulo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.common.util.Constantes;
import com.eb.appdemo.entidades.Modulo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewModuleChildPleaFragment} factory method to
 * create an instance of this fragment.
 */
public class NewModuleChildPleaFragment extends Fragment {

    private Modulo newModulo = Modulo.getInstance();

    private TextInputEditText nombre_modulo, campo_modulo;
    private RadioGroup tipo_campo_modulo;
    private MaterialTextView fecha_inicio_modulo;



    private LinearLayout fechaInicioLayout;

    private NewModuloFragment moduloFragment;

    String nombre,campo,tipo_campo,fecha_inicio = "";

    private static final String TAG = "MyActivity";



    public NewModuleChildPleaFragment(NewModuloFragment moduloFragment) {
        // Required empty public constructor
        this.moduloFragment = moduloFragment;
        moduloFragment.setTitle(Constantes.DATOS_GENERALES);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment NewModuleChildPleaFragment.
     */

    /*
    public static NewModuleChildPleaFragment newInstance() {
        NewModuleChildPleaFragment fragment = new NewModuleChildPleaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_module_child_plea, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ((NewModuloFragment) getParentFragment()).setTitle("Datos Generales", ((NewModuloFragment) getParentFragment()).getView());
        // mParentListener.messageFromChildToParent("Datos Generales");

        asignarHooks(view);

        setRadioButton(view);

        setDatePicker();
    }

    @Override
    public void onResume() {
        super.onResume();
        moduloFragment.setTitle(Constantes.DATOS_GENERALES);

    }


    @Override
    public void onPause() {
        super.onPause();
        setDataForNewModule();
        Log.i(TAG,"NewModuleChildPleaFragment onPause newModulo = "  + newModulo.toString());
    }



    private void setDataForNewModule(){

        nombre = nombre_modulo.getText().toString();
        campo = campo_modulo.getText().toString();
        //fecha_inicio = fecha_inicio_modulo.getText().toString();

        newModulo.setNombre_modulo(nombre);
        newModulo.setNombre_campo(campo);
        newModulo.setTipo_campo(tipo_campo);
        //newModulo.setFechaInicio(fecha_inicio);

    }

    private void asignarHooks(View view){
        fecha_inicio_modulo = view.findViewById(R.id.fecha_inicio_modulo);
        fechaInicioLayout = view.findViewById(R.id.fecha_inicio_modulo_layout);
        nombre_modulo = view.findViewById(R.id.nombre_modulo);
        campo_modulo = view.findViewById(R.id.campo_modulo);
        tipo_campo_modulo = view.findViewById(R.id.tipo_campo_modulo);
    }

    private void setRadioButton(View view){
        tipo_campo_modulo.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = view.findViewById(checkedId);
            tipo_campo = radioButton.getText().toString();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDatePicker(){

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Fecha de Inicio");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();



        fechaInicioLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(),"MATERIAL_DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(
                (MaterialPickerOnPositiveButtonClickListener<Long>)
                        selection -> {
            fecha_inicio_modulo.setText("Fecha de inicio: " + materialDatePicker.getHeaderText());
            newModulo.setFechaInicio(Instant.ofEpochMilli(selection)
                    .atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1).toString());
        });
    }

}