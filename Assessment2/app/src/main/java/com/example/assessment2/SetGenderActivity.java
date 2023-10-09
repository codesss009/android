package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SetGenderActivity extends AppCompatActivity {
RadioGroup gender;
public final static String Gender = "gender";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gender);
        setTitle("Set Gender");
        gender = findViewById(R.id.radioGroupGender);
        findViewById(R.id.buttonSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer G = gender.getCheckedRadioButtonId();
                Intent intent = new Intent();
                if (G == R.id.radioButtonFemale){
                    intent.putExtra(Gender, "Female");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else if(G == R.id.radioButtonMale){
                    intent.putExtra(Gender, "Male");
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Gender, "N/A");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}