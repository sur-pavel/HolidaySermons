package com.sur_pavel.holidaysermons;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sur_pavel.holidaysermons.databinding.ActivitySecondBinding;
import com.sur_pavel.holidaysermons.ui.main.PlaceholderFragment;
import com.sur_pavel.holidaysermons.ui.main.SectionsPagerAdapter;

import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//TabLayout
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.appBarSecond.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.appBarSecond.tabs;
        tabs.setupWithViewPager(viewPager);
//Drawer
        setSupportActionBar(binding.appBarSecond.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else {
            Log.e("Second Activity", "navHostFragment: null");
        }


        Data.holiday = GetItemName(savedInstanceState);
        ConfigureNavListView(viewPager);
    }

    private void ConfigureNavListView(ViewPager viewPager) {
        ArrayAdapter<String> aa = new ArrayAdapter<>(binding.navListview.getContext(),
                android.R.layout.simple_list_item_1, Data.fathers);
        ListView navListview = binding.navListview;
        navListview.setAdapter(aa);
        navListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                viewPager.setCurrentItem((int) id, false);
            }
        });
    }

    private String GetItemName(Bundle savedInstanceState) {
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("item");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("item");
        }
        return newString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_second);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof PlaceholderFragment) {
                handled = ((PlaceholderFragment)f).onBackPressed();
                if(handled) {
                    break;
                }
            }
        }
        if(!handled) {
            super.onBackPressed();
        }
    }}