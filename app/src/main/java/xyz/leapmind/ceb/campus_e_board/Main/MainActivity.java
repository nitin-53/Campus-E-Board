package xyz.leapmind.ceb.campus_e_board.Main;

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

    private Button login, register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_reg);

        login.setOnClickListener(this);
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
            case R.id.btn_login:
                Intent obj = new Intent(MainActivity.this, Login.class);
                startActivity(obj);
                break;

            case R.id.btn_reg:
                Intent obj1 = new Intent(MainActivity.this, Register.class);
                startActivity(obj1);
                break;

        }
    }


}
