package com.develop.expense.myapplication;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static com.develop.expense.myapplication.MyApplication2.adView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
 public LinearLayout layAd;
    MyApplication2 myApplication2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Expense Tracker");
        Fragment fragment=new Firstview();
        FragmentManager fm= getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.firstLayout,fragment).commit();
        layAd = (LinearLayout)findViewById(R.id.testL);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
 myApplication2= (MyApplication2)getApplication();
        myApplication2.loadAd(MainActivity.this,layAd);

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
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment fragment=new Firstview();
            FragmentManager fm= getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.firstLayout,fragment).commit();



        } else if (id == R.id.nav_summary) {

            Fragment fragment= new Summary();
            FragmentManager fm= getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.firstLayout,fragment).commit();




        } else if (id == R.id.nav_extra) {

            Fragment fragment= new Extra();
            FragmentManager fm= getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.firstLayout,fragment).commit();




        }

        else if (id == R.id.about)
        {AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.show(getFragmentManager(), "aboutDialog");}
        else if (id == R.id.nav_settings) {
            startActivity( new Intent(MainActivity.this,Settings.class));


        }

        else if (id == R.id.about) {
            AlertDialog.Builder aboutAlert= new AlertDialog.Builder(this);

            aboutAlert.setTitle("About").setCancelable(true).setMessage
                    ("Application name- My Expense Tracker\n\nDeveloper- Weem Works\n\nContact- weemworks@gmail.com").setPositiveButton("Thank You", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });



            aboutAlert.show();

        }
        else if (id == R.id.rateapp) {

            Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

}


    @Override
    protected void onResume() {
        super.onResume();
        myApplication2.loadAd(MainActivity.this,layAd);


    }
}
