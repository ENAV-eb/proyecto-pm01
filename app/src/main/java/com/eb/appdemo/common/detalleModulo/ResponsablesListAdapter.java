package com.eb.appdemo.common.detalleModulo;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.ContactsAdapter;
import com.eb.appdemo.entidades.ContactPhone;
import com.eb.appdemo.entidades.Modulo;
import com.eb.appdemo.entidades.Responsables;

import java.util.ArrayList;

public class ResponsablesListAdapter extends RecyclerView.Adapter<ResponsablesListAdapter.ResponsablesViewHolder> {

    private static final String TAG = "MyActivity";

    ArrayList<Responsables> responsables;

    //private Modulo newModulo = Modulo.getInstance();

    public ResponsablesListAdapter(ArrayList<Responsables> responsables) {
        this.responsables = responsables;
    }

    @NonNull
    @Override
    public ResponsablesListAdapter.ResponsablesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_call,
                null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        ResponsablesViewHolder responsablesViewHolder =  new ResponsablesViewHolder(view);

        return responsablesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResponsablesListAdapter.ResponsablesViewHolder holder, int position) {
        holder.nName.setText(responsables.get(position).getName());
        holder.nNumber.setText(responsables.get(position).getNumber());

        holder.mCall.setOnClickListener(v -> {
            Log.i(TAG,"ResponsablesListAdapter setOnClickListener = " +
                    responsables.get(position).getName() +
                    " " + responsables.get(position).getNumber()) ;

            Intent intent =  new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+responsables.get(position).getNumber()));
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return responsables.size();
    }

    class ResponsablesViewHolder extends RecyclerView.ViewHolder {
        TextView nName,nNumber;
        LinearLayout mLayout;
        ImageButton mCall;

        ResponsablesViewHolder(@NonNull View itemView) {
            super(itemView);
            nName = itemView.findViewById(R.id.item_responsable_name);
            nNumber = itemView.findViewById(R.id.item_responsable_number);
            mLayout = itemView.findViewById(R.id.item_reponsable_layout);
            mCall = itemView.findViewById(R.id.btnCallContact);
        }

    }
}
