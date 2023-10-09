package com.example.week3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class Gender extends AppCompatActivity {
Button b;
RadioGroup r;
public static final String gender_key = "gender";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        b = findViewById(R.id.button3);
        r = findViewById(R.id.radioGroup);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent();
                Integer selected= r.getCheckedRadioButtonId();
                if (selected == R.id.radioMale) {
                    i.putExtra(gender_key, "Male");
                    setResult(RESULT_OK, i);
                }
                else{
                    i.putExtra(gender_key, "Female");
                    setResult(RESULT_OK, i);
                }
                finish();
            }
        });
    }
}