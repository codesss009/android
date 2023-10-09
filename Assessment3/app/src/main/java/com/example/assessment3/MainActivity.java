package com.example.assessment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeFragmentListener, RegistrationFragment.RegistrationFragmentListener, SetGenderFragment.SetGenderListener, ProfileFragment.ProfileFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new WelcomeFragment() )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoRegistration() {

        getSupportFragmentManager().beginTransaction()
        .replace(R.id.rootView, new RegistrationFragment(),"RegistrationFragment" )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSetGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetGenderFragment() )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void sendGender(String gender) {
        RegistrationFragment registrationFragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("RegistrationFragment");
                if (registrationFragment != null){
                    registrationFragment.setGender(gender);
                    getSupportFragmentManager().popBackStack();
                }
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void close() {
        getSupportFragmentManager().popBackStack();
    }
}