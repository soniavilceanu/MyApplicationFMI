package com.example.myapplicationfmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button LogOUT;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.activity_dashboard);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profil) {
                    Toast.makeText(DashboardActivity.this, "Profil selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.setari) {
                    Toast.makeText(DashboardActivity.this, "Setari selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.carnet) {
                    Toast.makeText(DashboardActivity.this, "Carnet selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.orar) {
                    Toast.makeText(DashboardActivity.this, "Orar selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.calendar) {
                    Toast.makeText(DashboardActivity.this, "Calendar selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.internship) {
                    Toast.makeText(DashboardActivity.this, "Internship selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.voluntariat) {
                    Toast.makeText(DashboardActivity.this, "Voluntariat selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.cantina) {
                    Toast.makeText(DashboardActivity.this, "Cantina selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.informatii) {
                    Toast.makeText(DashboardActivity.this, "Informatii selected", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        // Adding click listener to Log Out button.
//        LogOUT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Finishing current DashBoard activity on button click.
//                finish();
//                Toast.makeText(DashboardActivity.this,"Log Out Successful", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}