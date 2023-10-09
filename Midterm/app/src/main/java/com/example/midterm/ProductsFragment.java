package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midterm.databinding.FragmentProductsBinding;
import com.example.midterm.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Product> productsList = new ArrayList<>();
    ArrayAdapter<Product> adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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

    FragmentProductsBinding binding;
    ArrayList<Product> mProducts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProducts();
        adapter = new ProductsAdapter(getActivity(), productsList);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = productsList.get(i);
                mListener.goToProductReviews(product);
            }
        });
    }
// ADAPTER------------------------------------------------------------------>
    public class ProductsAdapter extends ArrayAdapter<Product> {

        public ProductsAdapter(@NonNull Context context, @NonNull List objects) {
            super(context, R.layout.row_item_product, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_item_product, parent, false);
            }
            TextView textViewProductName = convertView.findViewById(R.id.textViewProductName);
            TextView textViewPrice = convertView.findViewById(R.id.textViewProductPrice);
            TextView textViewProductDesc = convertView.findViewById(R.id.textViewProductDesc);
            TextView textViewReviews = convertView.findViewById(R.id.textViewProductReviews);
            ImageView imageView = convertView.findViewById(R.id.imageViewProductIcon);


            Product products =  getItem(position);

            textViewProductName.setText(products.getName());
            textViewPrice.setText("$ "+products.getPrice());
            textViewProductDesc.setText(products.getDescription());
            textViewReviews.setText(products.getReview_count()+"  Reviews");
            Picasso.get().load(products.getImg_url()).into(imageView);


            return convertView;

        }
    }
//HITTING API---------------------------------------------------------------->
        private final OkHttpClient client = new OkHttpClient();
    void getProducts(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/products")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray productsJSONArray = jsonObject.getJSONArray("products");
                        for (int i = 0; i < productsJSONArray.length(); i++) {
                            JSONObject productsJSON = productsJSONArray.getJSONObject(i);
                            Product product = new Product(productsJSON);
                            productsList.add(product);
                        }
                        Log.d("DEMO", String.valueOf(productsList));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
    ProductsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProductsListener) context;
    }

    interface ProductsListener{
        void goToProductReviews(Product product);
    }


}