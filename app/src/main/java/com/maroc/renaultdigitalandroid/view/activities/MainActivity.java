package com.maroc.renaultdigitalandroid.view.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.maroc.renaultdigitalandroid.R;
import com.maroc.renaultdigitalandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enable DarkMode Setup
        //Helpers.getInstance(this).enableDarkMode(); Not in this project
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Begin of Main UI
        if (!this.setupBottomNavigationView()) {
            return;
        }
    }

    private boolean setupBottomNavigationView() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_home);
        if (navHostFragment == null) {
            Snackbar.make(this, binding.getRoot(), getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
            return false;
        }
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationViewMain, navController);
        return true;
    }
}