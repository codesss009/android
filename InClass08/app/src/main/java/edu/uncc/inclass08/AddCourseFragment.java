package edu.uncc.inclass08;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.uncc.inclass08.databinding.FragmentAddCourseBinding;

public class AddCourseFragment extends Fragment {
    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddCourseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Course");
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoGrades();
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseLetterGrade;
                String courseNumber = binding.editTextCourseNumber.getText().toString();
                String courseName = binding.editTextCourseName.getText().toString();
                double courseHours = Double.parseDouble(binding.editTextCourseHours.getText().toString());
                Grade grade = new Grade();
                grade.CourseHours = courseHours;
                grade.CourseName = courseName;
                grade.CourseNumber = courseNumber;
                int selectedId = binding.radioGroupGrades.getCheckedRadioButtonId();

                if(courseName.isEmpty() || courseNumber.isEmpty() || binding.editTextCourseHours.getText().toString().isEmpty()) {
                   Toast.makeText(getContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if(selectedId == -1){
                    Toast.makeText(getContext(), "Please select a letter grade !!", Toast.LENGTH_SHORT).show();
                } else {

                    if(selectedId == R.id.radioButtonA) {
                        grade.CourseLetterGrade = "A";
                    } else if(selectedId == R.id.radioButtonB) {
                        grade.CourseLetterGrade = "B";
                    } else if(selectedId == R.id.radioButtonC) {
                        grade.CourseLetterGrade = "C";
                    } else if(selectedId == R.id.radioButtonD) {
                        grade.CourseLetterGrade = "D";
                    } else {
                        grade.CourseLetterGrade = "F";
                    }

                    //grade.CourseLetterGrade = courseLetterGrade;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Log.d(TAG, "onClick:"+grade.CourseLetterGrade);
                db.collection(mAuth.getUid()).document(courseNumber).set(grade).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mListener.gotoGrades();
                    }
                });
            }
        });

    }
    AddCourseListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //if(context instanceof AddCourseListener) {
            mListener = (AddCourseListener) context;
      //  } else {
       //     throw new RuntimeException(context.toString() + " must implement GradesListener");
        //  }
    }
    interface AddCourseListener{
        void gotoGrades();
    }
}