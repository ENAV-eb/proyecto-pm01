package com.eb.appdemo.common.modulo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.eb.appdemo.R;
import com.eb.appdemo.common.util.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewModuleChildTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewModuleChildTimeFragment extends Fragment {

    private static final String TAG = "MyActivity";

    MultiSpinnerSearch mssFumigaciónModulo;
    MultiSpinnerSearch mssLimpiezaModulo;
    MultiSpinnerSearch mssCosechaModulo;

    SingleSpinnerSearch singleSpinnerSearch;

    List<KeyPairBoolData> listaSemanas = new ArrayList<>();
    List<KeyPairBoolData> listaFumigacion = new ArrayList<>();
    List<KeyPairBoolData> listaLimpieza = new ArrayList<>();
    List<KeyPairBoolData> listaCosecha = new ArrayList<>();

    List<Long> semanasFumigacion = new ArrayList<>();
    List<Long> semanasCosecha = new ArrayList<>();
    List<Long> semanasLimpieza = new ArrayList<>();

    private NewModuloFragment newModuloFragment;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public NewModuleChildTimeFragment() {
        // Required empty public constructor
    }

    public NewModuleChildTimeFragment(NewModuloFragment moduloFragment) {
        this.newModuloFragment = moduloFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewModuleChildTimeFragment.
     */

    public static NewModuleChildTimeFragment newInstance(String param1, String param2) {
        NewModuleChildTimeFragment fragment = new NewModuleChildTimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_new_module_child_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((NewModuloFragment) getParentFragment()).setTitle("Semanas Calendario",this.getParentFragment().getView());

        asignarHooks(view);

        setListaSemanas();

        setMultiSpinnerFumigacion();
        setMultiSpinnerLimpieza();
        setMultiSpinnerCosecha();
    }

    @Override
    public void onResume() {
        super.onResume();
        newModuloFragment.setTitle(Constantes.CALENDARIO_SEMANA);
    }

    @Override
    public void onPause() {
        super.onPause();


        semanasFumigacion = mssFumigaciónModulo.getSelectedIds();
        semanasLimpieza = mssLimpiezaModulo.getSelectedIds();
        semanasCosecha = mssCosechaModulo.getSelectedIds();



        Log.i(TAG,"On listaFumigacion= " + semanasFumigacion.size() ) ;
        Log.i(TAG,"On listaLimpieza= " + semanasLimpieza.size() ) ;
        Log.i(TAG,"On listaCosecha= " + semanasCosecha.size() ) ;
    }

    private void asignarHooks(View view) {
        mssFumigaciónModulo = view.findViewById(R.id.mssFumigaciónModulo);
        mssLimpiezaModulo = view.findViewById(R.id.mssLimpiezaModulo);
        mssCosechaModulo = view.findViewById(R.id.mssCosechaModulo);
    }


    private void setListaSemanas(){

        KeyPairBoolData item = new KeyPairBoolData();;

        for(int i=0; i < Constantes.CANTIDAD_LISTA_SEMANAS ; i++) {
            int j = i+1;
            item.setId(j);
            item.setName("Semana "+j);
            item.setSelected(false);

            listaFumigacion.add(item);
            listaLimpieza.add(item);
            listaCosecha.add(item);
            listaSemanas.add(item);
            item = new KeyPairBoolData();
        }
    }

    private void setMultiSpinnerFumigacion(){

        //Fumigación txt
        mssFumigaciónModulo.setSearchEnabled(true);
        mssFumigaciónModulo.setSearchHint("campo de búsqueda");
        mssFumigaciónModulo.setHintText("Seleccione la(s) semana(s) que correspondan");
        mssFumigaciónModulo.setEmptyTitle("No se encuentra esa semana ");
        mssFumigaciónModulo.setShowSelectAllButton(false);
        mssFumigaciónModulo.setClearText("Cerrar");

        mssFumigaciónModulo.setItems(listaFumigacion, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {

                for (KeyPairBoolData k: selectedItems) {
                    listaFumigacion.get(listaFumigacion.indexOf(k)).setSelected(true);
                }
                Log.i(TAG,"On mssFumigaciónModulo= " + selectedItems.size() ) ;
            }
        });

        /*

        mssFumigaciónModulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mssFumigaciónModulo.setItems(listaFumigacion, new MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> selectedItems) {

                        for (KeyPairBoolData k: selectedItems) {
                            listaFumigacion.get(listaFumigacion.indexOf(k)).setSelected(true);
                        }
                        Log.i(TAG,"On mssFumigaciónModulo= " + selectedItems.size() ) ;
                    }
                });

            }
        });

         */

    }

    private void setMultiSpinnerLimpieza(){

        mssLimpiezaModulo.setSearchEnabled(true);
        mssLimpiezaModulo.setSearchHint("campo de búsqueda");
        mssLimpiezaModulo.setHintText("Seleccione la(s) semana(s) que correspondan");
        mssLimpiezaModulo.setEmptyTitle("No se encuentra esa semana ");
        mssLimpiezaModulo.setShowSelectAllButton(false);
        mssLimpiezaModulo.setClearText("Cerrar");

        mssLimpiezaModulo.setItems(listaLimpieza, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {

                Log.i(TAG,"On mssLimpiezaModulo= " + selectedItems.size() ) ;

            }
        });

    }

    private void setMultiSpinnerCosecha(){

        mssCosechaModulo.setSearchEnabled(true);
        mssCosechaModulo.setSearchHint("campo de búsqueda");
        mssCosechaModulo.setHintText("Seleccione la(s) semana(s) que correspondan");
        mssCosechaModulo.setEmptyTitle("No se encuentra esa semana ");
        mssCosechaModulo.setShowSelectAllButton(false);
        mssCosechaModulo.setClearText("Cerrar");

        mssCosechaModulo.setItems(listaCosecha, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {

                Log.i(TAG,"On mssCosechaModulo= " + selectedItems.size() ) ;

            }

        });
    }
}