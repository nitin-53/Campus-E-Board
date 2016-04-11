package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import xyz.leapmind.ceb.campus_e_board.R;

public class Register extends Activity implements OnClickListener {

    private Button btn_student, btn_teacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_option);

        btn_student = (Button) findViewById(R.id.btn_student);
        btn_teacher = (Button) findViewById(R.id.btn_teacher);

        btn_student.setOnClickListener(Register.this);
        btn_teacher.setOnClickListener(Register.this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_teacher:
                Intent obj = new Intent(Register.this, RegisterTeacher.class);
                startActivity(obj);
                break;

            case R.id.btn_student:
                Intent obj1 = new Intent(Register.this, RegisterStudent.class);
                startActivity(obj1);
                break;

            default:
                break;
        }
    }
}

