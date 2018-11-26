package com.example.martinkayondo.sns;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView postList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                userMenuSelector(item);
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null){
            sendUserToLogin();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userMenuSelector(MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_home:
                Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_find_friends:
                Toast.makeText(this,"Find Friends",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this," Go to Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_messages:
                Toast.makeText(this,"Messages",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout: {
                firebaseAuth.signOut();
                sendUserToLogin();
                break;
            }
            case R.id.nav_friends:
                Toast.makeText(this,"Friends",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_post:
                Toast.makeText(this,"New Post",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendUserToLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
