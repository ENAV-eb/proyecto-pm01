package com.eb.appdemo.common.new2;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.ContactPhone;
import com.eb.appdemo.entidades.Modulo;
import com.eb.appdemo.entidades.Responsables;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private static final String TAG = "MyActivity";

    ArrayList<ContactPhone> contacts;

    private Modulo newModulo = Modulo.getInstance();

    public ContactsAdapter(ArrayList<ContactPhone> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_contact,
                null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);

        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.nName.setText(contacts.get(position).getName());
        holder.nNumber.setText(contacts.get(position).getNumber());

        holder.mAdd.setChecked(contacts.get(position).getSelected());

        holder.mAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contacts.get(holder.getAdapterPosition()).setSelected(isChecked);

                ContactPhone contactPhone = contacts.get(holder.getAdapterPosition());
                List<Responsables> responsablesList = newModulo.getResponsables();

                if(responsablesList == null) { responsablesList =  new ArrayList<>();}

                if(isChecked == true){
                    responsablesList.add(new Responsables(contactPhone));
                } else {
                    responsablesList.remove(responsablesList.indexOf(contactPhone));
                }

                newModulo.setResponsables(responsablesList);
                Log.i(TAG,"ContactsAdapter onCheckedChanged = " +
                        contacts.get(holder.getAdapterPosition())) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView nName,nNumber;
        LinearLayout mLayout;
        CheckBox mAdd;

        ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nName = itemView.findViewById(R.id.item_contact_name);
            nNumber = itemView.findViewById(R.id.item_contact_number);
            mLayout = itemView.findViewById(R.id.item_contact_layout);
            mAdd = itemView.findViewById(R.id.item_contact_check);
        }

    }
}
