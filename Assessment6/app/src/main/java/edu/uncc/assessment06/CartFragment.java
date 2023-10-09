package edu.uncc.assessment06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uncc.assessment06.databinding.CartRowItemBinding;
import edu.uncc.assessment06.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCartBinding binding;
    CartAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Product> mProducts = new ArrayList<>();
    public double price;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Cart");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         db.collection(mAuth.getCurrentUser().getDisplayName()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               mProducts.clear();
                price = 0.0;
                for(QueryDocumentSnapshot document: value){
                    Product product = document.toObject(Product.class);
                    price += Double.valueOf(product.getPrice());

                    mProducts.add(product);
            }
                binding.textViewTotal.setText("Total - $" + price);
                adapter = new CartFragment.CartAdapter();
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerView.setAdapter(adapter);
        }
    });

}
    class CartAdapter extends RecyclerView.Adapter<CartFragment.CartAdapter.CartViewHolder>{

        @NonNull
        @Override
        public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CartFragment.CartAdapter.CartViewHolder(CartRowItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CartFragment.CartAdapter.CartViewHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class CartViewHolder extends RecyclerView.ViewHolder{
            CartRowItemBinding mBinding;
            Product mProduct;
            public CartViewHolder(CartRowItemBinding rowItemBinding) {
                super(rowItemBinding.getRoot());
                mBinding = rowItemBinding;
            }
            void setupUI(Product product){
                this.mProduct = product;
                mBinding.textViewProductName.setText(product.getName());
                mBinding.textViewProductPrice.setText(product.getPrice());
                mBinding.imageViewDeleteFromCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(mAuth.getCurrentUser().getDisplayName()).document(mProduct.getPid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(mProducts.size() != 0){
                                    mProducts.remove(mProduct);
                                    price = price - Double.valueOf(mProduct.getPrice());
                                    //binding.textViewTotal.setText("Total - $ "+String.valueOf(price));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                Picasso.get().load(product.getImg_url()).into(mBinding.imageViewProductIcon);
            }
        }
    }
}