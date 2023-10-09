package com.example.inclass4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, RegisterFragment.SelectDept, DepartmentFragment.DepartmentFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoRegistration() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment(), "RegisterFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoDept() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new DepartmentFragment(), "DepartmentFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendDepartment(String Dept) {
        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag("RegisterFragment");
                if(registerFragment != null){
                    registerFragment.setSelectedDept(Dept);
                    getSupportFragmentManager().popBackStack();
                }
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }
}