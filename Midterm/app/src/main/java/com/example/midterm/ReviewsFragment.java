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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midterm.databinding.FragmentReviewsBinding;
import com.example.midterm.models.Product;
import com.example.midterm.models.Review;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Product mParam1;
    private String mParam2;

    ArrayAdapter<Review> adapter;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(Product param1) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    FragmentReviewsBinding binding;
    ArrayList<Review> mReviews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewProductName.setText(mParam1.getName());
        binding.textViewProductPrice.setText(mParam1.getPrice());
        Picasso.get().load(mParam1.getImg_url()).into(binding.imageViewProductIcon);
        getReviews();
        adapter = new ReviewsAdapter(getActivity(), mReviews);
        binding.listView.setAdapter(adapter);
        binding.buttonCreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.createReview(mParam1);
            }
        });
    }
    public class ReviewsAdapter extends ArrayAdapter<Review> {

        public ReviewsAdapter(@NonNull Context context, @NonNull List objects) {
            super(context, R.layout.row_item_review, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_item_review, parent, false);
            }
            TextView textViewReview = convertView.findViewById(R.id.textViewReview);
            TextView textViewReviewDate = convertView.findViewById(R.id.textViewReviewDate);
            ImageView imageViewRating = convertView.findViewById(R.id.imageViewReviewRating);


            Review review = getItem(position);

            textViewReview.setText(review.getReview());
            textViewReviewDate.setText("$ " + review.getCreated_at());
            imageViewRating.setImageResource(R.drawable.stars_1);
            //Picasso.get().load(review.getReview()).into(imageViewRating);


            return convertView;

        }
    }
    private final OkHttpClient client = new OkHttpClient();

    void getReviews(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/product/reviews/"+mParam1.getPid())
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
                        JSONArray reviewsJSONArray = jsonObject.getJSONArray("reviews");
                        Log.d("DEMO","message");
                        for (int i = 0; i < reviewsJSONArray.length(); i++) {
                            JSONObject reviewJSON = reviewsJSONArray.getJSONObject(i);
                            Review review = new Review(reviewJSON);
                            mReviews.add(review);
                        }
                        Log.d("DEMO", String.valueOf(mReviews));

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mListener.sendQuestionsToTriva(mTriviaQuestions);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }
    CreateReview mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateReview) context;
    }
    interface CreateReview{
        void createReview(Product product);
    }
}