package xyz.leapmind.ceb.campus_e_board.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import xyz.leapmind.ceb.campus_e_board.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button loginStudent, loginTeacher, register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginStudent = (Button) findViewById(R.id.btn_login_student);
        loginTeacher = (Button) findViewById(R.id.btn_login_teacher);
        register = (Button) findViewById(R.id.btn_reg);

        loginStudent.setOnClickListener(this);
        loginTeacher.setOnClickListener(this);
        register.setOnClickListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean skipSplash = prefs.getBoolean("skipSplash", false);
        if (!skipSplash) {
            Intent splash = new Intent(MainActivity.this, Splash.class);
            startActivity(splash);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_login_student:
                SharedPreferences prefs1 = this.getSharedPreferences(
                        getString(R.string.login_preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = prefs1.edit();
                editor1.putBoolean(getString(R.string.student_login_preference), true);
                editor1.apply();
                SharedPreferences prefs2 = this.getSharedPreferences(
                        getString(R.string.login_preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();
                editor2.putBoolean(getString(R.string.teacher_login_preference), false);
                editor2.apply();

                Intent obj1 = new Intent(MainActivity.this, Login.class);
                startActivity(obj1);
                finish();
                break;

            case R.id.btn_login_teacher:
                SharedPreferences prefs3 = this.getSharedPreferences(
                        getString(R.string.login_preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = prefs3.edit();
                editor3.putBoolean(getString(R.string.teacher_login_preference), true);
                editor3.apply();
                SharedPreferences prefs4 = this.getSharedPreferences(
                        getString(R.string.login_preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor4 = prefs4.edit();
                editor4.putBoolean(getString(R.string.student_login_preference), false);
                editor4.apply();

                Intent obj2 = new Intent(MainActivity.this, Login.class);
                startActivity(obj2);
                finish();
                break;

            case R.id.btn_reg:
                Intent obj3 = new Intent(MainActivity.this, Register.class);
                startActivity(obj3);
                finish();
                break;

        }
    }


}
