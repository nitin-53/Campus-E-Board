package xyz.leapmind.ceb.campus_e_board.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import xyz.leapmind.ceb.campus_e_board.R;

public class Teacher extends AppCompatActivity {
    Adapter adapter;
    ViewPager pagerTeacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTeacher);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutTeacher);
        tabLayout.addTab(tabLayout.newTab().setText("Notices"));
        tabLayout.addTab(tabLayout.newTab().setText("Recent"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerTeacher = (ViewPager) findViewById(R.id.pagerTeacher);
        adapter = new Adapter(getSupportFragmentManager());
        adapter.student=false;
        pagerTeacher.setAdapter(adapter);

        pagerTeacher.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerTeacher.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        if (id == R.id.log_out)
            return true;

        return super.onOptionsItemSelected(item);
    }
}


