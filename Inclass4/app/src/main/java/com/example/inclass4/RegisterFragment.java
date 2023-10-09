package com.example.inclass4;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public  String selectedDept;

    public void setSelectedDept(String selectedDept) {
        this.selectedDept = selectedDept;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
    Button buttonSelectDept, buttonSubmit;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonSelectDept = view.findViewById(R.id.buttonSelectDept);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        if(selectedDept != null){
            TextView textViewDept = view.findViewById(R.id.textViewSelectedDept);
            textViewDept.setText(selectedDept);
        }
        else{
            Log.d(TAG, "onViewCreated: seldept");
        }
        buttonSelectDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoDept();
            }
        });
        EditText name, email, id;
        name = view.findViewById(R.id.editTextName);
        email = view.findViewById(R.id.editTextEmail);
        id = view.findViewById(R.id.editTextID);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile(name.getText().toString(), email.getText().toString(), id.getText().toString(), selectedDept);
                mListener.gotoProfile(profile);
            }
        });
    }
    SelectDept mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectDept) context;
    }

    public interface SelectDept{
        void gotoDept();
        void gotoProfile(Profile profile);
    }
}