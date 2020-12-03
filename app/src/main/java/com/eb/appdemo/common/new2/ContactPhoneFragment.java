package com.eb.appdemo.common.new2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.eb.appdemo.R;
import com.eb.appdemo.common.modulo.NewModuloFragment;
import com.eb.appdemo.common.util.Constantes;
import com.eb.appdemo.entidades.ContactPhone;
import com.eb.appdemo.entidades.Modulo;
import com.eb.appdemo.entidades.Responsables;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.security.Permission;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactPhoneFragment extends Fragment  {

    private static final String TAG = "MyActivity";

    private Modulo newModulo = Modulo.getInstance();

    private RecyclerView rvContacts;
    private RecyclerView.Adapter rvContactsAdapter;
    private RecyclerView.LayoutManager rvContactsLayoutManager;

    TextInputLayout busqueda_contactos;
    TextInputEditText variable_busqueda_contactos;

    ArrayList<ContactPhone> contactPhonesList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NewModuloFragment moduloFragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactPhoneFragment() {
        // Required empty public constructor
    }

    public ContactPhoneFragment(NewModuloFragment moduloFragment) {
        this.moduloFragment = moduloFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactPhoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactPhoneFragment newInstance(String param1, String param2) {
        ContactPhoneFragment fragment = new ContactPhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_phone, null, false);
        //permissionAccessPhoneContacts();

        moduloFragment.setTitle(Constantes.ACCESS_CONTACTS_TITLE);
        contactPhonesList =  new ArrayList<>();
        hooks(view);

        if(ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionAccessPhoneContacts();
        } else {
            getContactsList();
        }

        //TODO
        setBusquedaAction();



        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i(TAG,"onRequestPermissionsResult parent = " + getParentFragment()) ;
        Log.i(TAG,"onRequestPermissionsResult requestCode = " + requestCode) ;
        Log.i(TAG,"onRequestPermissionsResult permissions[0]" + permissions[0]);
        Log.i(TAG,"onRequestPermissionsResult grantResults[0]" + grantResults[0]);
        if (permissions[0].equals(Manifest.permission.READ_CONTACTS)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG,"onRequestPermissionsResult inside if");
            getContactsList();
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        moduloFragment.setTitle(Constantes.ACCESS_CONTACTS_TITLE);
    }

    private void hooks(View view) {
        rvContacts = view.findViewById(R.id.contactList);
        variable_busqueda_contactos = view.findViewById(R.id.variable_busqueda_contactos);
        busqueda_contactos = view.findViewById(R.id.busqueda_contactos);

    }

    private void setContactAdapterList() {
        rvContacts.setNestedScrollingEnabled(false);
        rvContacts.setHasFixedSize(false);
        rvContactsLayoutManager =  new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);

        rvContacts.setLayoutManager(rvContactsLayoutManager);

        rvContactsAdapter =  new ContactsAdapter(contactPhonesList);
        rvContacts.setAdapter(rvContactsAdapter);
    }

    @SuppressLint({"NewApi", "LocalSuppress"})
    private void setBusquedaAction(){
        busqueda_contactos.setEndIconOnClickListener(v -> {
            Log.i(TAG,"ContactPhoneFragment setBusquedaAction variable_busqueda_contactos = " +
                     variable_busqueda_contactos.getText());
            Log.i(TAG,"ContactPhoneFragment setBusquedaAction contactPhonesList = " +
                    contactPhonesList.toString());
            ArrayList<ContactPhone> contactPhonesListSearch = contactPhonesList.stream().filter(c ->
                    c.getName().contains(variable_busqueda_contactos.getText()))
                    .collect(Collectors.toCollection(ArrayList::new));

            Log.i(TAG,"ContactPhoneFragment setBusquedaAction contactPhonesListSearch = " +
                    contactPhonesListSearch.toString());

            rvContactsAdapter =  new ContactsAdapter(contactPhonesListSearch);
            rvContacts.setAdapter(rvContactsAdapter);

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"ContactPhoneFragment onPause contactPhonesList = " +
                contactPhonesList.toString());

        //setDataForNewModule();

        Log.i(TAG,"ContactPhoneFragment onPause newModule = " +
                newModulo.toString());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDataForNewModule() {
        newModulo.setResponsables(contactPhonesList.stream()
                .filter(c-> c.getSelected().equals(true))
                .map(r -> new Responsables(r))
                .collect(Collectors.toList()));

    }


    private void permissionAccessPhoneContacts() {
       requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
               PackageManager.PERMISSION_GRANTED);
    }

    private void getContactsList() {
        Log.i(TAG,"getContactsList inside ");
        Cursor contactsPhone = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                null,null,null);

        while(contactsPhone.moveToNext()) {
            String name = contactsPhone.getString(contactsPhone.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = contactsPhone.getString(contactsPhone.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactPhone contactPhone = new ContactPhone(name,number);

            contactPhonesList.add(contactPhone);
        }

        setContactAdapterList();

        Log.i(TAG,"getContactsList contactPhonesList " + contactPhonesList.toString());

    }

}