package com.eb.appdemo.common.modulo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.appdemo.R;
import com.eb.appdemo.common.detalleModulo.DetalleModuloActivity;
import com.eb.appdemo.common.new2.KeeperMainPageActivity;
import com.eb.appdemo.entidades.ModeloCardView;
import com.eb.appdemo.entidades.Modulo;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ModuloListaAdapter extends RecyclerView.Adapter<ModuloListaAdapter.ModuloViewHolder> {

    private static final String TAG = "MyActivity";

    private ArrayList<ModeloCardView> lista ;

    public ModuloListaAdapter(ArrayList<ModeloCardView> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ModuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_modulo,parent,false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        return new ModuloViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ModuloListaAdapter.ModuloViewHolder holder, int position) {

        holder.cm_temperature.setText((lista.get(position).getTemperatura()));
        holder.cm_humedad.setText((lista.get(position).getHumedad()));
        holder.cm_luminosidad.setText((lista.get(position).getLuminosidad()));
        holder.cm_soil.setText((lista.get(position).getPh_suelo()));
        holder.cm_crop.setText((lista.get(position).getCrop()));
        holder.cm_tiempo.setText((lista.get(position).getTiempo()));

        holder.cm_modulo.setText((lista.get(position).getNombre_modulo()));
        holder.cm_campo.setText((lista.get(position).getNombre_campo()));
        holder.cm_toogle.setActivated(lista.get(position).isActive());

        holder.cm_toogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO update the active module, if inactive will freeze and place last of list
                // or discard, need some thinking
                Log.i(TAG,"ModuloListaAdapter setOnCheckedChangeListener value = " + isChecked) ;
            }
        });
        holder.itemView.setOnClickListener(v -> {

            //TODO sent to new activity where can check historic live data
            String id = lista.get(position).getId();
            Log.i(TAG,"ModuloListaAdapter setOnClickListener id = " + id) ;

            //TODO la idea, si es posible, cambiar el menu del parent y iniciar el nuevo fragment
            Intent mainIntent = new Intent(v.getContext(), DetalleModuloActivity.class);
            v.getContext().startActivity(mainIntent);

        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ModuloViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView cm_temperature, cm_humedad, cm_luminosidad, cm_soil, cm_crop,
                cm_tiempo, cm_campo, cm_modulo, cm_semana, cm_fase;

        SwitchMaterial cm_toogle;

        public ModuloViewHolder(@NonNull View itemView) {
            super(itemView);

            cm_temperature = itemView.findViewById(R.id.cm_temperature);
            cm_humedad = itemView.findViewById(R.id.cm_humedad);
            cm_luminosidad = itemView.findViewById(R.id.cm_luminosidad);
            cm_soil = itemView.findViewById(R.id.cm_soil);
            cm_crop = itemView.findViewById(R.id.cm_crop);
            cm_tiempo = itemView.findViewById(R.id.cm_tiempo);

            cm_campo = itemView.findViewById(R.id.cm_campo);
            cm_modulo = itemView.findViewById(R.id.cm_modulo);
            cm_toogle = itemView.findViewById(R.id.cm_toogle);
            cm_semana = itemView.findViewById(R.id.cm_semana);
            cm_fase = itemView.findViewById(R.id.cm_fase);

        }
    }
}
