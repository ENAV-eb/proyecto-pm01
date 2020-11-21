package com.eb.appdemo.common.modulo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.Modulo;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ModuloListaAdapter extends RecyclerView.Adapter<ModuloListaAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Modulo> lista = new ArrayList<>();

    public ModuloListaAdapter(Context context, ArrayList<Modulo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ModuloListaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_modulos,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuloListaAdapter.MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView txt_temperatura;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
