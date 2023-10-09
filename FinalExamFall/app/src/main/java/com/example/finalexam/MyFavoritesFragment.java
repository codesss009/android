package com.example.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.databinding.FavoriteListItemBinding;
import com.example.finalexam.databinding.FragmentMyFavoritesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyFavoritesFragment extends Fragment {

    public MyFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentMyFavoritesBinding binding;
    ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFavoritesBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoritesAdapter = new FavoritesAdapter();
        binding.recyclerView.setAdapter(favoritesAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("favourite_items").addSnapshotListener(new EventListener<QuerySnapshot>(){
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("demo", "Listen failed.", error);
                    return;
                }

                mFavorites.clear();
                for (QueryDocumentSnapshot document : value) {
                    Item item = document.toObject(Item.class);
                    mFavorites.add(item);
                }
                favoritesAdapter.notifyDataSetChanged();


            }
        });

        return binding.getRoot();

    }
    ArrayList<Item> mFavorites = new ArrayList<>();
    void getFavorites(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favourite_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mFavorites.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item item = document.toObject(Item.class);
                        mFavorites.add(item);
                    }
                    favoritesAdapter.notifyDataSetChanged();
                } else {
                    Log.d("demo", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    FavoritesAdapter favoritesAdapter;
    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    FirebaseFirestore db;

    class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ItemsViewHolder> {
        @NonNull
        @Override
        public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FavoriteListItemBinding binding = FavoriteListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ItemsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
            Item item = mFavorites.get(position);
            holder.setupUI(item);

        }

        @Override
        public int getItemCount() {
            return mFavorites.size();
        }

        class ItemsViewHolder extends RecyclerView.ViewHolder {
            FavoriteListItemBinding mBinding;
            Item mItem;
            public ItemsViewHolder(FavoriteListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoPhotoDetails();
                    }
                });
            }

            public void setupUI(Item item) {
                mItem = item;
                mBinding.textViewCreatedAt.setText(item.photo_creation_date);
                mBinding.textViewUserFullName.setText(item.owner_name);
                Picasso.get().load(item.photo_small_image).into(mBinding.imageViewThumbnail);
                Picasso.get().load(item.owner_icon).into(mBinding.imageViewUserThumbnail);
                mBinding.textViewDescription.setText(mItem.photo_description);



                mBinding.imageViewTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete the item
                        //refresh by getting the items
                        db = FirebaseFirestore.getInstance();
                        db.collection("favourite_items").document(mItem.id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //removed to show the snapshot listener
                                //getItems();
                            }
                        });
                    }
                });
            }
        }

    }
    MyFavoritesFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MyFavoritesFragmentListener) context;
    }

    interface MyFavoritesFragmentListener{
        void gotoPhotoDetails();
    }
}