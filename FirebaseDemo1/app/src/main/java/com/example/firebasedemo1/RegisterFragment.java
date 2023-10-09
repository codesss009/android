package com.example.firebasedemo1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasedemo1.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RegisterFragment extends Fragment {

   private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    FragmentLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register Fragment");
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                String email = String.valueOf(binding.editTextEmail.getText());
                String password = String.valueOf(binding.editTextPassword.getText());
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Register in Successfully");
                                    Log.d(TAG, mAuth.getCurrentUser().getUid());
                                } else {
                                    Log.d(TAG, task.getException().getMessage());
                                }
                            }
                        });
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("notes").get()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot documents: task.getResult()){
                                        Log.d(TAG, documents.getId());
                                        Log.d(TAG, String.valueOf(documents.getData()));
                                    }
                            }
                        }
                                });
            }
        });
binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment())
                .commit();
    }
});
            }
}