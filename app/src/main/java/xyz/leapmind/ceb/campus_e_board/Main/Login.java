package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.content.Intent;
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
        switch (v.getId()) {
            case R.id.btn_login:
                get_userName = userId.getText().toString();
                get_password = pass.getText().toString();
                if (get_userName.equals("") || get_password.equals("")) {
                    Toast.makeText(Login.this, "Don't leave empty fields", Toast.LENGTH_SHORT).show();
                } else if (get_userName.equals("Teacher") && get_password.equals("123")) {
                    Toast.makeText(Login.this, "Sucessfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent obj = new Intent(Login.this, Teacher.class);
                    startActivity(obj);
                    finish();
                } else if (get_userName.equals("Student") && get_password.equals("123")) {
                    Toast.makeText(Login.this, "Sucessfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent obj1 = new Intent(Login.this, Student.class);
                    startActivity(obj1);
                } else {
                    Toast.makeText(Login.this, "INVALID", Toast.LENGTH_SHORT).show();
                    userId.setText("");
                    pass.setText("");

                }
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
