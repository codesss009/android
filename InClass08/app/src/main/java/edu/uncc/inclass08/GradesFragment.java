package edu.uncc.inclass08;

import static java.lang.String.format;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.inclass08.databinding.FragmentGradesBinding;
import edu.uncc.inclass08.databinding.GradeRowItemBinding;

public class GradesFragment extends Fragment {
    public GradesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentGradesBinding binding;
    GradeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<Grade> mGrades = new ArrayList<>();
    Double total_credit_hrs = 0.0;
    Integer total_grade_points = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Grades");
        adapter = new GradeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                adapter.notifyDataSetChanged();
                mGrades.clear();
                for(QueryDocumentSnapshot document: value){
                    Grade grade = document.toObject(Grade.class);
                    mGrades.add(grade);
                    String s = grade.CourseLetterGrade;
                    total_grade_points += 'h' - Character.toLowerCase(s.charAt(0)) + 1;
                    total_credit_hrs += grade.getCourseHours();
                }
                //float dpa = 4.8787f;
                float gpa = (float) (total_grade_points/total_credit_hrs);
                String formatting = String.valueOf(gpa);
                String formatted = String.format("%.3f",formatting);
                binding.textViewGPA.setText(formatted);
                binding.textView2.setText(String.valueOf(total_credit_hrs));
            }
        });
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoAddCourse();
            }
        });
    }
    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder>{
        @NonNull
        @Override
        public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding rowBinding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new GradeViewHolder(rowBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
            Grade grade = mGrades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        class GradeViewHolder extends RecyclerView.ViewHolder{
            GradeRowItemBinding mBinding;
            Grade mGrade;
            public GradeViewHolder(@NonNull GradeRowItemBinding rowBinding) {
                super(rowBinding.getRoot());
                this.mBinding = rowBinding;
            }

            public void setupUI(Grade grade){
                this.mGrade = grade;
                mBinding.textViewCourseHours.setText(String.valueOf(mGrade.getCourseHours()));
                mBinding.textViewCourseLetterGrade.setText(mGrade.CourseLetterGrade);
                mBinding.textViewCourseName.setText(mGrade.CourseName);
                mBinding.textViewCourseNumber.setText(mGrade.getCourseNumber());
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(mAuth.getUid()).document(grade.CourseNumber).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

            }
        }
    }

    GradesListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GradesListener) {
            mListener = (GradesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GradesListener");
        }
    }
    interface GradesListener{
        void gotoAddCourse();

        void gotoGrades();
    }
}