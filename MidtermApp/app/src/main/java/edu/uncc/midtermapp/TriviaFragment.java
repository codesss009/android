package edu.uncc.midtermapp;


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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import edu.uncc.midtermapp.databinding.FragmentTriviaBinding;
import edu.uncc.midtermapp.models.Answer;
import edu.uncc.midtermapp.models.Question;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriviaFragment extends Fragment {
    private ArrayList<Question> mTriviaQuestions = new ArrayList<Question>();
    ArrayList<Answer> currentAnswers = new ArrayList<>();
    ArrayAdapter<Answer> adapter;
    int currentQuestionIndex = 0;
    Question currentQuestion;
    private static final String ARG_PARAM1 = "param1";
    int count = 0;


    // TODO: Rename and change types of parameters
    private ArrayList<Question> mParam1;

    public TriviaFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static TriviaFragment newInstance(ArrayList<Question> param1) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (ArrayList<Question>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    FragmentTriviaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTriviaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final OkHttpClient client = new OkHttpClient();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Trivia Fragment");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, currentAnswers);
        Objects.requireNonNull(binding.listViewAnswers).setAdapter(adapter);
        binding.listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Answer selectedAnswer = adapter.getItem(i);
                Log.d("Selected","ans"+ selectedAnswer.answer_text + selectedAnswer.getAnswer_id());

                RequestBody formBody = new FormBody.Builder()
                        .add("question_id", currentQuestion.getQuestion_id())
                        .add("answer_id", selectedAnswer.getAnswer_id())
                        .build();

                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/api/trivia/checkAnswer")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Error rendering the correct answer", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            String body = responseBody.string();

                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                Log.d("DEMO", "onResponse: "+body);
                                Log.d("DEMO", "onResponse: "+jsonObject.getBoolean("isCorrectAnswer"));
                                if (jsonObject.getBoolean("isCorrectAnswer")) {
                                    Log.d("DEMO", "onResponse:inside ");
                                    count++;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (currentQuestionIndex == mParam1.size()-1) {
                                                mListener.goToStats(count, mParam1.size());
                                                Toast.makeText(getActivity(), "Questions completed", Toast.LENGTH_SHORT).show();
                                            } else {
                                                currentQuestionIndex++;
                                                currentQuestion = mParam1.get(currentQuestionIndex);
                                                displayQuestion();
                                            }
                                        }
                                    });
                                }
                                else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            displayQuestion();
                                            Toast.makeText(getActivity(), "Incorrect answer", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Could not fetch data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
                if (mParam1.size() > 0) {
                    currentQuestionIndex = 0;
                    currentQuestion = mParam1.get(0);
                    displayQuestion();
                }
                else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Trivia has no questions", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }
    void displayQuestion(){
        binding.textViewTriviaQuestion.setText(currentQuestion.getQuestion_text());
        binding.textViewTriviaTopStatus.setText("Question" +(currentQuestionIndex + 1)+"of"+ mParam1.size());
        currentAnswers.clear();
        currentAnswers.addAll(currentQuestion.getAnswers());
        adapter.notifyDataSetChanged();
        if(currentQuestion.getQuestion_url() != null){
            Picasso.get().load(currentQuestion.getQuestion_url()).into(binding.imageViewQuestion);
        }
    }
     TriviaListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TriviaListener) context;
    }

    interface TriviaListener{
        void goToStats(int count, int size);
    }
}


