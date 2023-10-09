package edu.uncc.assessment07.shopping;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import edu.uncc.assessment07.databinding.FragmentAddListItemBinding;
import edu.uncc.assessment07.models.ShoppingList;

public class AddListItemFragment extends Fragment {
    private static final String ARG_PARAM_SHOPPING_LIST = "ARG_PARAM_SHOPPING_LIST";
    private ShoppingList mShoppingList;

    public AddListItemFragment() {
        // Required empty public constructor
    }

    public static AddListItemFragment newInstance(ShoppingList shoppingList) {
        AddListItemFragment fragment = new AddListItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_SHOPPING_LIST, shoppingList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShoppingList = (ShoppingList) getArguments().getSerializable(ARG_PARAM_SHOPPING_LIST);
        }
    }

    FragmentAddListItemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddListItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add List Item");

        binding.seekBarQuantity.setMax(20);
        binding.seekBarQuantity.setProgress(10);
        binding.textViewQuantity.setText(String.valueOf(10));
        mShoppingList.getDocId();
        binding.seekBarQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewQuantity.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                int quantity = binding.seekBarQuantity.getProgress();

                if(name.isEmpty()) {
                    Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                } else if(quantity == 0) {
                    Toast.makeText(getActivity(), "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: Add item to the list
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseFirestore db =  FirebaseFirestore.getInstance();
                    DocumentReference docRef = db
                            .collection("Shopping Lists")
                            .document(mShoppingList.getDocId())
                            .collection("Shopping List Items")
                            .document();
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("name", name);
                    data.put("quantity", quantity);
                    data.put("docId", docRef.getId());
                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //binding.editTextComment.setText("");
                                //notify the adapter !!

                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                mListener.addListItemDone();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addListItemCancel();
            }
        });
    }

    AddListItemListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddListItemListener) context;
    }

    public interface AddListItemListener {
        void addListItemDone();
        void addListItemCancel();
    }
}