package com.example.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.databinding.FragmentSearchBinding;
import com.example.finalexam.databinding.ItemListItemBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSearchBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Item> items = new ArrayList<>();
    HashMap<String,Object> favourites = new HashMap<>();
    ItemsAdapter adapter;
   FirebaseFirestore db;
   ArrayList<Item> favouritesList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    String query = "";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemsAdapter();
        binding.recyclerView.setAdapter(adapter);

//        MenuItem favoriteButton = getView().findViewById(R.id.my_favorites_menu_item);
//        favoriteButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                mListener.gotoFavorites();
//                return false;
//            }
//        });

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = binding.editTextSearchKeyword.getText().toString();
                if(query.length() != 0){
                    getSearchResults(query);
                }
                else{
                    Toast.makeText(getActivity(), "Search for something", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout_menu_item){
            //logout code goes here ...
            return true;
        } else if(item.getItemId() == R.id.my_favorites_menu_item){
            //my favorites code goes here ...
            mListener.gotoFavorites();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void getSearchResults(String query){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.unsplash.com/search/photos/").newBuilder();
        urlBuilder.addQueryParameter("client_id", "5QAt1jUGCT-JazrX-tOAm95PBVmP1DdJ4_Y1X9llrLw");
        urlBuilder.addQueryParameter("query", query);
        urlBuilder.addQueryParameter("per_page", "20");
        urlBuilder.addQueryParameter("orientation", "landscape");
        urlBuilder.addQueryParameter("content_filter", "high");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = null;
                    try {
                        body = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("demo", "onResponse: " + body);

                    try {
                        JSONObject rootJson = new JSONObject(body);
                        JSONArray itemsJsonArray = rootJson.getJSONArray("results");
                        items.clear();

                        for (int i = 0; i < itemsJsonArray.length(); i++) {
                            JSONObject itemJsonObject = itemsJsonArray.getJSONObject(i);
                            Item item = new Item(itemJsonObject);
                            items.add(item);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                } else {

                }
            }
        });
    }
    class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>{
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(ItemListItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = items.get(position);
            holder.setupUI(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            ItemListItemBinding mBinding;
            Item mItem;
            public ItemViewHolder(ItemListItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding = vhBinding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //mListener.gotoItemDetails(mItem);
                    }
                });
            }

            public void setupUI(Item item){
                this.mItem = item;
                mBinding.textViewUserFullName.setText(mItem.owner_name);
                mBinding.textViewCreatedAt.setText(mItem.photo_creation_date);
                mBinding.textViewDescription.setText(mItem.photo_description);
                Picasso.get().load(item.photo_small_image).into(mBinding.imageViewThumbnail);
                Picasso.get().load(item.owner_icon).into(mBinding.imageViewUserThumbnail);
                //mBinding.imageViewFavorite.setImageResource(R.drawable.ic_heart_favorite);
                mBinding.imageViewFavorite.setTag(R.drawable.ic_heart_not_favorite);

                mBinding.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if ((int) mBinding.imageViewFavorite.getTag() == R.drawable.ic_heart_not_favorite) {
                            db = FirebaseFirestore.getInstance();
//                        favourites.put("owner_name", mItem.owner_name);
//                        favourites.put("photo_creation_date",)
//                        favourites.put("photo_description", )
//                        favourites.put("imageViewThumbnail", )
//                                favourites.putA
                            db.collection("favourite_items").document(mItem.id).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    mBinding.imageViewFavorite.setImageResource(R.drawable.ic_heart_favorite);
                                    mBinding.imageViewFavorite.setTag(R.drawable.ic_heart_favorite);
                                    Toast.makeText(getActivity(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                                   // favouritesList.add(mItem);
                                }
                            });
                        }
                        else{
                            db = FirebaseFirestore.getInstance();
                            db.collection("favourite_items").document(mItem.id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                   // favouritesList.
                                    mBinding.imageViewFavorite.setTag(R.drawable.ic_heart_not_favorite);
                                    mBinding.imageViewFavorite.setImageResource(R.drawable.ic_heart_not_favorite);
                                    Toast.makeText(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }

    }

    SearchFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SearchFragmentListener) context;
    }

    interface SearchFragmentListener{
        void gotoFavorites();
    }
}