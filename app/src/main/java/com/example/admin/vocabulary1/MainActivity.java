package com.example.admin.vocabulary1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.FragmentTransaction;
//import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.admin.vocabulary1.fragments.FragmentMain;
import com.example.admin.vocabulary1.fragments.FragmentMap;
import com.example.admin.vocabulary1.fragments.FragmentTest;
import com.example.admin.vocabulary1.fragments.FragmentWatch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentMain fmain;
    FragmentWatch fwatch;
    FragmentTest ftest;
    FragmentTransaction ftrans;
    FragmentMap fmap;

    DatabaseMyHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper= new DatabaseMyHelper(this);
        fmain = new FragmentMain();
        fwatch = new FragmentWatch();
        ftest = new FragmentTest();
        fmap = new FragmentMap();
        ftrans= getFragmentManager().beginTransaction();
        ftrans.replace(R.id.container,fmain);
        ftrans.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        ftrans= getFragmentManager().beginTransaction();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        switch(item.getItemId())
        {
            case R.id.nav_camera: ftrans.replace(R.id.container,fmain); break;
            case R.id.nav_gallery:
                                Cursor cursor = database.query(dbHelper.TABLE_COUNTRY, null, null, null, null, null, null);
                                if (cursor.moveToFirst()) ftrans.replace(R.id.container,fwatch);
                                else Toast.makeText(MainActivity.this, "Словарь пуст",Toast.LENGTH_LONG).show();
                                cursor.close();
                break;
            case R.id.nav_slideshow: ftrans.replace(R.id.container,ftest);break;
            case R.id.nav_share:    ftrans.replace(R.id.container,fmain);
                                    database.delete(dbHelper.TABLE_COUNTRY,null,null);break;
          //  case R.id.map:    ftrans.replace(R.id.container,fmap);
        }
        ftrans.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
