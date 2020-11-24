package com.eb.appdemo.common.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.KeeperMainPageActivity;
import com.eb.appdemo.common.new2.KeeperPresentationActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigParentFragment} factory method to
 * create an instance of this fragment.
 */
public class ConfigParentFragment extends Fragment {


    MaterialButton sign_out;

    public ConfigParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment ConfigParentFragment.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hooks(view);

        settingLogOutAction();
    }

    private void hooks(View view){
        sign_out = view.findViewById(R.id.sign_out);
    }

    private void settingLogOutAction(){

        sign_out.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();


            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
                    getString(R.string.preference_file), Context.MODE_PRIVATE);

            SharedPreferences.Editor editorSP = sharedPreferences.edit();

            editorSP.clear();
            editorSP.apply();

            Intent mainIntent = new Intent(this.getContext(), KeeperPresentationActivity.class);

            startActivity(mainIntent);

        });

    }
}