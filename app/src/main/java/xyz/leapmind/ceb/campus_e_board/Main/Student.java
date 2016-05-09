package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import xyz.leapmind.ceb.campus_e_board.AppAssist.SessionManager;
import xyz.leapmind.ceb.campus_e_board.R;

public class Student extends AppCompatActivity {
    Adapter adapter;
    ViewPager pagerStudent;
    String msg = "Android : ";
    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        // Session manager
        session = new SessionManager(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarStudent);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutStudent);
        tabLayout.addTab(tabLayout.newTab().setText("Notices"));
        tabLayout.addTab(tabLayout.newTab().setText("Project"));
        tabLayout.addTab(tabLayout.newTab().setText("Class"));
        tabLayout.addTab(tabLayout.newTab().setText("Section"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerStudent = (ViewPager) findViewById(R.id.pagerStudent);
        adapter = new Adapter(getSupportFragmentManager());
        adapter.student = true;
        pagerStudent.setAdapter(adapter);

        pagerStudent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerStudent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
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
        if (id == R.id.log_out) {
            session.setLogin(false);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

