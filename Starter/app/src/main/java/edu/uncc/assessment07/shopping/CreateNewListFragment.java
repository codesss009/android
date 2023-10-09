package edu.uncc.assessment07.shopping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uncc.assessment07.databinding.FragmentCreateNewListBinding;
import edu.uncc.assessment07.models.ShoppingList;

public class CreateNewListFragment extends Fragment {
    FragmentCreateNewListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateNewListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create New List");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewListCancel();
            }
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = binding.editTextName.getText().toString();
                if(listName.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter a list name", Toast.LENGTH_SHORT).show();
                } else {

                    DocumentReference docRef = db.collection("Shopping Lists").document();
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("docId",docRef.getId());
                    data.put("name",listName );
                    data.put("ownerId",mAuth.getUid());
                    //ShoppingList shoppingList = new ShoppingList(listName, mAuth.getUid(), docRef.getId());
                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mListener.createNewListDone();
                        }
                    });
                }
            }
        });
    }

    CreateNewListListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateNewListListener) context;
    }

    public interface CreateNewListListener {
        void createNewListDone();
        void createNewListCancel();
    }

}