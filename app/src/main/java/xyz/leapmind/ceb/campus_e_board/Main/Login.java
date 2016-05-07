package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import xyz.leapmind.ceb.campus_e_board.R;

public class Login extends AppCompatActivity implements OnClickListener {
    private EditText userId, pass;
    private Button login1;
    private String get_userName, get_password;
    private TextView forgot;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userId = (EditText) findViewById(R.id.userID);
        pass = (EditText) findViewById(R.id.password);
        login1 = (Button) findViewById(R.id.btn_login);
        forgot = (TextView) findViewById(R.id.forgot);

        login1.setOnClickListener(Login.this);
        forgot.setOnClickListener(Login.this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        SharedPreferences prefs1 = this.getSharedPreferences(
                getString(R.string.login_preference_file), Context.MODE_PRIVATE);
        boolean isThisStudent = prefs1.getBoolean(getString(R.string.student_login_preference), false);

        SharedPreferences prefs2 = this.getSharedPreferences(
                getString(R.string.login_preference_file), Context.MODE_PRIVATE);
        boolean isThisTeacher = prefs2.getBoolean(getString(R.string.teacher_login_preference), false);


        switch (v.getId()) {
            case R.id.btn_login:
                get_userName = userId.getText().toString();
                get_password = pass.getText().toString();

                if (get_userName.equals("") || get_password.equals("")) {
                    Toast.makeText(Login.this, "Don't leave empty fields", Toast.LENGTH_SHORT).show();
                } else if (isThisStudent) {
                    if (get_userName.matches("^[Uu][EeMm]\\d{6}$")) {
                        if (get_userName.equals("UE128001") && get_password.equals("123")) {
                            Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();
                            Intent obj1 = new Intent(Login.this, Student.class);
                            startActivity(obj1);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "INVALID CREDENTIALS !", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            pass.setText("");
                        }
                    } else {
                        Toast.makeText(Login.this, "UserID Pattern is wrong", Toast.LENGTH_SHORT).show();
                        userId.setText("");
                        pass.setText("");
                    }
                } else if (isThisTeacher) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(get_userName).matches()) {
                        if (get_userName.equals("ks18994ks@gmail.com") && get_password.equals("123")) {
                            Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();
                            Intent obj = new Intent(Login.this, Teacher.class);
                            startActivity(obj);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "INVALID CREDENTIALS !", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            pass.setText("");
                        }
                    } else {
                        Toast.makeText(Login.this, "UserID Pattern is wrong", Toast.LENGTH_SHORT).show();
                        userId.setText("");
                        pass.setText("");
                    }
                }

                /*else if (get_userName.equals("Teacher") && get_password.equals("123")) {
                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent obj = new Intent(Login.this, Teacher.class);
                    startActivity(obj);
                    finish();
                } else if (get_userName.equals("Student") && get_password.equals("123")) {
                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent obj1 = new Intent(Login.this, Student.class);
                    startActivity(obj1);
                } else {
                    Toast.makeText(Login.this, "INVALID", Toast.LENGTH_SHORT).show();
                    userId.setText("");
                    pass.setText("");

                } */

                break;
            case R.id.forgot:

                Intent obj2 = new Intent(Login.this, Forgot.class);
                startActivity(obj2);
                break;

            default:
                break;
        }
    }

}
