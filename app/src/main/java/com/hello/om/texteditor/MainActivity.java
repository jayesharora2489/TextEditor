package com.hello.om.texteditor;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.net.URI;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle adt;
    private NavigationView nv;
    private ViewPager vp;
    MyFragPagerAdapter ad;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar t=findViewById(R.id.toolbar);
        setSupportActionBar(t);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nv=findViewById(R.id.nav_view);
        nv.setItemIconTintList(null);

        dl=findViewById(R.id.dl);
        adt=new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);

        dl.addDrawerListener(adt);
        adt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vp=findViewById(R.id.vp);
        ad=new MyFragPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(ad);
        ad.addTab();

        TabLayout tl=findViewById(R.id.tab);
        tl.setupWithViewPager(vp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolmenu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean returnResult = adt.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.nw: {
                ad.addTab();
                vp.setCurrentItem(ad.getCount() - 1);
                returnResult = true;
                break;

            }

            case R.id.open: {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.setType("text/*");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, 0);
                } else {
                    Toast.makeText(this, "Couldn't access storage.", Toast.LENGTH_SHORT).show();
                }
                returnResult = true;
                break;

            }

            case R.id.save: {

                break;
            }
            case R.id.saveas: {

            }
        }
        /* Recommended to pass this event to the toggle.*/
        return returnResult || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Uri fileUri=data.getData();
            ad.addTab(fileUri);
            vp.setCurrentItem(ad.getCount()-1);

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adt.syncState();   // Sync indicator state if events haven't been sent to the toggle for a while.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adt.onConfigurationChanged(newConfig); // Recommended to pass these changes to the toggle
    }
}






