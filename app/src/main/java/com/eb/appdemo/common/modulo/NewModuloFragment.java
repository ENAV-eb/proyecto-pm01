package com.eb.appdemo.common.modulo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewModuloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewModuloFragment extends Fragment {

    private static final String TAG = "MyActivity";

    private TabLayout module_tab_layout;
    private ViewPager2 module_view_container;
    private FragmentStateAdapter pagerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewModuloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewModuloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewModuloFragment newInstance(String param1, String param2) {
        NewModuloFragment fragment = new NewModuloFragment();
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
        return inflater.inflate(R.layout.fragment_new_modulo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        module_tab_layout = view.findViewById(R.id.module_tab_layout);
        module_view_container = view.findViewById(R.id.module_view_container);

        List<String> tabs = Arrays.asList("Plea","Time");

        module_tab_layout.setTabGravity(module_tab_layout.GRAVITY_FILL);

        pagerAdapter = new ModuleAdapter(this.getActivity(),tabs.size());


        module_view_container.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        module_view_container.setAdapter(pagerAdapter);

        new TabLayoutMediator(module_tab_layout, module_view_container,
                false,
                (tab, position) -> tab.setText(tabs.get(position))
        ).attach();



    }
    /*
    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        insertNestedFragment();
    }

    // Embeds the child fragment dynamically
    private void insertNestedFragment() {
        Fragment childFragment = new NewModuleChildPleaFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }
    */

}