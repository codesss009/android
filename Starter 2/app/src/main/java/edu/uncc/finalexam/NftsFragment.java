package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.finalexam.databinding.AssetsListItemBinding;
import edu.uncc.finalexam.databinding.FragmentNftsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NftsFragment extends Fragment {
    public NftsFragment() {
        // Required empty public constructor
    }

    FragmentNftsBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Asset> assets = new ArrayList<>();
    AssetsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNftsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getNfts();
        adapter = new AssetsAdapter();
        binding.recyclerViewNfts.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.recyclerViewNfts.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            mListener.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    void getNfts(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/nfts-assets")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Log.d("demo", "onResponse: " + body);

                    try {
                        JSONObject rootJson = new JSONObject(body);

                        JSONArray assetsArray = rootJson.getJSONArray("assets");
                        assets.clear();

                        for (int i = 0; i < assetsArray.length(); i++) {
                            JSONObject assetObject = assetsArray.getJSONObject(i);
                            JSONObject nftJsonObject = assetObject.getJSONObject("nft");
                            JSONObject collectionObject = assetObject.getJSONObject("collection");
                            Asset asset = new Asset(nftJsonObject, collectionObject);
                            assets.add(asset);

                        }
                        Log.d("asset", ""+assets.toString());

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

    class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.ContactViewHolder>{
        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactViewHolder(AssetsListItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            Asset asset = assets.get(position);
            holder.setupUI(asset);
        }

        @Override
        public int getItemCount() {
            return assets.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            AssetsListItemBinding mBinding;
            Asset mAsset;
            public ContactViewHolder(AssetsListItemBinding vhBinding) {
                super(vhBinding.getRoot());
                mBinding = vhBinding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });


            }

            public void setupUI(Asset asset){
                this.mAsset = asset;
                mBinding.textViewCollectionName.setText(mAsset.collection_name);
                mBinding.textViewNftName.setText(mAsset.nft_name);
                Picasso.get().load(mAsset.getCollection_image()).into(mBinding.imageViewCollectionBanner);
                Picasso.get().load(mAsset.getNft_image()).into(mBinding.imageViewNftIcon);

            }
        }

    }

    NftsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NftsListener) context;
    }

    interface NftsListener {
        void logout();
    }
}