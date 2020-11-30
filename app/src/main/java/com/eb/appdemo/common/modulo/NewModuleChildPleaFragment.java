package com.eb.appdemo.common.modulo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eb.appdemo.R;
import com.eb.appdemo.common.util.Constantes;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewModuleChildPleaFragment} factory method to
 * create an instance of this fragment.
 */
public class NewModuleChildPleaFragment extends Fragment {

    private MaterialTextView materialTextView;
    private LinearLayout fechaInicioLayout;

    private OnChildFragmentInteractionListener mParentListener;
    private NewModuloFragment moduloFragment;

    private static final String TAG = "MyActivity";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

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
    public static NewModuleChildPleaFragment newInstance(String param1, String param2) {
        NewModuleChildPleaFragment fragment = new NewModuleChildPleaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_module_child_plea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ((NewModuloFragment) getParentFragment()).setTitle("Datos Generales", ((NewModuloFragment) getParentFragment()).getView());
        // mParentListener.messageFromChildToParent("Datos Generales");

        asignarHooks(view);

        setDatePicker();
    }

    @Override
    public void onResume() {
        super.onResume();
        moduloFragment.setTitle(Constantes.DATOS_GENERALES);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        /*
        try {
            getParentFragment().getActivity();

            Log.i(TAG," : " + getParentFragment().toString());

            mParentListener = (OnChildFragmentInteractionListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException((getParentFragment().getChildFragmentManager()
                    .getFragments().get(1)).toString() +
                    " must implement OnChildFragmentInteractionListener");
        }

         */

    }

    @Override
    public void onDetach() {
        super.onDetach();

        mParentListener = null;
    }

    private void asignarHooks(View view){
        materialTextView = view.findViewById(R.id.fecha_inicio_modulo);
        fechaInicioLayout = view.findViewById(R.id.fecha_inicio_modulo_layout);
    }

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

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                materialTextView.setText("Fecha de inicio: " + materialDatePicker.getHeaderText());
            }
        });
    }

    public interface OnChildFragmentInteractionListener {
        void messageFromChildToParent(String myString);
    }

}