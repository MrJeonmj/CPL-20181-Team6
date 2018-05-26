package com.example.user.jolp_v0;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // BottomNavigationViewHelperでアイテムのサイズ、アニメーションを調整
        BottomNavigationViewHelper.disableShiftMode(bottomavigation);
        // BottomNavigationViewを選択したときのリスナー
        bottomavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager manager = getFragmentManager();

                // 各選択したときの処理
                switch (item.getItemId()) {
                    case R.id.nav_temp:
                        manager.beginTransaction().add(R.id.content_main, new Temp()).commit();

                        return true;
                    case R.id.nav_home:
                        manager.beginTransaction().replace(R.id.content_main, new Maincontent2()).commit();

                        return true;
                    case R.id.nav_breath:
                        manager.beginTransaction().replace(R.id.content_main, new Breath()).commit();

                        return true;
                }

                return false;
            }
        });


        //비상상황 알림
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        FragmentManager manager = getFragmentManager();
        if (id == R.id.nav_home) {
            manager.beginTransaction().add(R.id.content_main, new Maincontent2()).commit();


        } else if (id == R.id.nav_temp) {
            manager.beginTransaction().add(R.id.content_main, new Temp()).commit();


        } else if (id == R.id.nav_breath) {
            manager.beginTransaction().add(R.id.content_main, new Breath()).commit();
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            Intent intent1 = new Intent(Main2Activity.this, Login.class);
            finish();
            startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
