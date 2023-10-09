package edu.uncc.hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.uncc.hw06.databinding.ForumRowItemBinding;
import edu.uncc.hw06.databinding.FragmentForumsBinding;

public class ForumsFragment extends Fragment {

    public ForumsFragment() {
        // Required empty public constructor
    }

    FragmentForumsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ForumAdapter adapter;
    ArrayList<Forum> mForums = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Forums");
        adapter = new ForumAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Forums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mForums.clear();
                for(QueryDocumentSnapshot doc: value){
                    Forum forum  = doc.toObject(Forum.class);
                    mForums.add(forum);
                }
                adapter.notifyDataSetChanged();
            }
        });
        binding.buttonCreateForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewForum();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });



    }


    class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder>{
        @NonNull
        @Override
        public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForumRowItemBinding rowBinding = ForumRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ForumViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
            Forum Forum = mForums.get(position);
            holder.setupUI(Forum);
        }
        @Override
        public int getItemCount() {
            return mForums.size();
        }

        class ForumViewHolder extends RecyclerView.ViewHolder{
            ForumRowItemBinding mBinding;
            Forum mForum;
            public ForumViewHolder(@NonNull ForumRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.forum(mForum);
                    }
                });
            }
            public void setupUI(Forum Forum){
                this.mForum = Forum;
                mBinding.textViewForumTitle.setText(mForum.title);
                mBinding.textViewForumText.setText(mForum.getDescription());
                mBinding.textViewForumCreatedBy.setText(mForum.author);
                mBinding.textViewForumLikesDate.setText(String.valueOf(mForum.likes + " Likes | "+mForum.created_at));
                mBinding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBinding.imageViewLike.setImageResource(R.drawable.like_favorite);
                        mForum.likes +=1 ;
                    }
                });
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseFirestore.getInstance().collection("Forums")
                                .document(mForum.doc_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //adapter.notifyDataSetChanged();
                                    }
                                });

                    }
                });
            }
        }
    }

    ForumsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ForumsListener) context;
    }

    interface ForumsListener {
        void createNewForum();
        void logout();
        void forum(Forum mForum);
    }
}