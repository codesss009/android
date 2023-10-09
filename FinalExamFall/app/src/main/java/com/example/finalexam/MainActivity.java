package com.example.finalexam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements RegisterFragment.RegisterFragmentListener, LoginFragment.LoginFragmentListener, SearchFragment.SearchFragmentListener, MyFavoritesFragment.MyFavoritesFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();
    }
    @Override
    public void gotoLoginFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void gotoRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void authSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SearchFragment())
                .commit();
    }

    @Override
    public void gotoFavorites() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MyFavoritesFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoPhotoDetails() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new PhotoDetailsFragment())
                .addToBackStack(null)
                .commit();
    }
}