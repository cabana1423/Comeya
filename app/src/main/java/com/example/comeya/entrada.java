package com.example.comeya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class entrada extends AppCompatActivity {
    BottomNavigationView navegar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);
        navegar=(BottomNavigationView)findViewById(R.id.navegacion);
        navegar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.home){
                    showSelectedFragment(new Home());
                }
                if(item.getItemId()==R.id.search){
                    showSelectedFragment(new search());
                }
                if(item.getItemId()==R.id.add){
                    showSelectedFragment(new add());
                }
                return false;
            }
        });
    }
    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}