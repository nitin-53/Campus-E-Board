package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import xyz.leapmind.ceb.campus_e_board.R;

public class RegisterStudent extends AppCompatActivity {
    private Spinner sp_class, sp_sem, sp_section, sp_grp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);

        sp_class = (Spinner) findViewById(R.id.spClass);
        sp_sem = (Spinner) findViewById(R.id.spSemester);
        sp_section = (Spinner) findViewById(R.id.spSection);
        sp_grp = (Spinner) findViewById(R.id.spGroup);

        String[] class_list = getResources().getStringArray(R.array.spclass);
        String[] sem_list = getResources().getStringArray(R.array.spsem);
        String[] sec_list = getResources().getStringArray(R.array.spsection);
        String[] grp_list = getResources().getStringArray(R.array.spgroup);

        ArrayAdapter<String> adp, adp1, adp2, adp3;

        adp = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, class_list);
        sp_class.setAdapter(adp);

        adp1 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, sem_list);
        sp_sem.setAdapter(adp1);

        adp2 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, sec_list);
        sp_section.setAdapter(adp2);

        adp3 = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, grp_list);
        sp_grp.setAdapter(adp3);

    }
}
