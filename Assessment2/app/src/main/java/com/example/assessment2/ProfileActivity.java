package com.example.assessment2;

import static com.example.assessment2.Profile.Profile_Key;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        TextView Gender = findViewById(R.id.textViewGender);
        TextView Weight = findViewById(R.id.textViewWeight);
        if (getIntent() != null && getIntent().hasExtra(Profile_Key)){
            Profile profile = (Profile)getIntent().getSerializableExtra(Profile_Key);
            Gender.setText(profile.getGender());
            Weight.setText(profile.getWeight());
        }
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}