package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

import xyz.leapmind.ceb.campus_e_board.Array.CollectionOfArray;
import xyz.leapmind.ceb.campus_e_board.R;


public class Splash extends AppCompatActivity implements OnClickListener {


    public CollectionOfArray objArray;
    public Calendar cal;
    private TextView thought;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        View button = findViewById(R.id.closeSplash);
        button.setOnClickListener(this);

        thought = (TextView) findViewById(R.id.thought);

        objArray = new CollectionOfArray();

        cal = Calendar.getInstance();

        thought.setText("" + objArray.get_todayThought(cal.get(Calendar.DAY_OF_YEAR)));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.closeSplash) {
            if (((CheckBox) findViewById(R.id.chkSplash)).isChecked()) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("skipSplash", true);
                editor.apply();
            }
            finish();
        }
    }

}
