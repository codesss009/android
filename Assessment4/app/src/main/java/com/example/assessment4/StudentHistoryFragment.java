package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assessment4.Models.CourseHistory;
import com.example.assessment4.Models.DataServices;
import com.example.assessment4.Models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Student mParam1;

    public StudentHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentHistoryFragment newInstance(Student param1) {
        StudentHistoryFragment fragment = new StudentHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Student) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_history, container, false);
    }
    ListView listView;
    StudentAdapter adapter;
    ArrayList<CourseHistory> HistoryList;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        HistoryList = mParam1.getCourses();
        TextView textViewName = view.findViewById(R.id.textViewStudentName);
        textViewName.setText(mParam1.getName());
        adapter = new StudentAdapter(getActivity(),R.layout.fragment_student_history, HistoryList);
        listView.setAdapter(adapter);
    }
    class StudentAdapter extends ArrayAdapter<CourseHistory>{

        public StudentAdapter(@NonNull Context context, int resource, @NonNull List<CourseHistory> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.history_row_item, parent, false);
            }

            TextView textViewHours = convertView.findViewById(R.id.textViewCourseHours);
            TextView textViewCourse = convertView.findViewById(R.id.textViewCourseName);
            TextView textViewGrade = convertView.findViewById(R.id.textViewCourseLetterGrade);
            TextView textViewCourse_Id = convertView.findViewById(R.id.textViewCourseNumber);

            CourseHistory courseHistory =  getItem(position);
            textViewCourse.setText(courseHistory.getName());
            textViewCourse_Id.setText(courseHistory.getNumber());
            textViewGrade.setText(courseHistory.getLetterGrade());
            textViewHours.setText(String.valueOf(courseHistory.getHours()));

            return convertView;

        }
    }
}