package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import xyz.leapmind.ceb.campus_e_board.R;

public class Student extends FragmentActivity {
    Adapter adapter;
    ViewPager pagerStudent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        pagerStudent = (ViewPager)findViewById(R.id.pagerStudent);
        adapter = new Adapter(getSupportFragmentManager());
        adapter.student=true;
        pagerStudent.setAdapter(adapter);
    }

}
