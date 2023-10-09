package com.example.inclass05;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DataServices.App mParam1;
    private DataServices.App mParam2;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppDetailsFragment newInstance(DataServices.App param1) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DataServices.App) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_details, container, false);
    }
ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> Genre;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewAppsList);
        Genre = DataServices.getAppCategories();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, Genre);
        TextView appName = view.findViewById(R.id.textViewAppName);
        TextView artistName = view.findViewById(R.id.textViewArtistName);
        TextView ReleaseDate = view.findViewById(R.id.textViewReleaseDate);
        appName.setText(mParam1.name);
        artistName.setText(mParam1.getArtistName());
        ReleaseDate.setText(mParam1.getReleaseDate());
        listView.setAdapter(adapter);
    }
}