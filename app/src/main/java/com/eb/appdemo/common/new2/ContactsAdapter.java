package com.eb.appdemo.common.new2;

import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    ArrayList<ContactPhone> contacts;

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

        holder.mAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contacts.get(holder.getAdapterPosition()).setSelected(isChecked);
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
