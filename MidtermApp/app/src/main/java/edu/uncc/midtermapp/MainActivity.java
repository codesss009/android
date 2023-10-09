package edu.uncc.midtermapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.midtermapp.models.Question;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeListener, TriviaFragment.TriviaListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new WelcomeFragment())
                .commit();
    }


    @Override
    public void sendToTrivia(ArrayList<Question> questions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, TriviaFragment.newInstance(questions))
                .commit();
    }

    @Override
    public void goToStats(int count, int size) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, StatsFragment.newInstance(count, size))
                .commit();
    }
}