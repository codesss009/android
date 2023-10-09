package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SetProfile extends AppCompatActivity {
    public final static String Profile_Key = "profile_key";
    String sel_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        EditText Weight = findViewById(R.id.editTextWeight);
        RadioGroup Gender = findViewById(R.id.radioGroupGender);
        findViewById(R.id.buttonSetWeight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (Gender.getCheckedRadioButtonId() == R.id.radioButtonMale){
                     sel_gender  = "Male";
                }
                else{
                    sel_gender = "Female";
                }
                Profile profile = new Profile(Weight.getText().toString(), sel_gender);
                intent.putExtra(Profile_Key, profile);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}