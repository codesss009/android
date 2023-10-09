package com.example.assessment2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button setWeightButton;
ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
    if(result.getResultCode() == RESULT_OK){
        Intent data = result.getData();
        String s = data.getStringExtra(SetWeightActivity.Weight);
        Weight.setText(s);
}
    }
});

    ActivityResultLauncher<Intent> genderResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                String s = data.getStringExtra(SetGenderActivity.Gender);
                Gender.setText(s);
            }
        }
    });
TextView Weight;
TextView Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main");
        Weight = findViewById(R.id.textViewWeight);
        setWeightButton = findViewById(R.id.buttonSetWeight);
        setWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetWeightActivity.class);
                startForResult.launch(intent);
            }
        });
        Gender = findViewById(R.id.textViewGender);
        findViewById(R.id.buttonSetGender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetGenderActivity.class);
                genderResult.launch(intent);
            }
        });
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile(Weight.getText().toString(), Gender.getText().toString() );
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(Profile.Profile_Key, profile);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight.setText("N/A");
                Gender.setText("N/A");
            }
        });
    }
}