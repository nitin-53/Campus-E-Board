package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xyz.leapmind.ceb.campus_e_board.R;

public class RegisterTeacher extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_teacher);
    }

    public void teacherSignUp(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.teacher_name);
        EditText emailEditText = (EditText) findViewById(R.id.teacher_email);
        EditText phnEditText = (EditText) findViewById(R.id.teacher_phn);
        EditText passwordEditText = (EditText) findViewById(R.id.teacher_password);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phn = phnEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (name.equals("") || email.equals("") || phn.equals("")) {

            Toast.makeText(RegisterTeacher.this, "Only Section or Group may be empty",
                    Toast.LENGTH_SHORT).show();
        } else if (!name.matches("^[\\p{L} .'-]+$")) {
            Toast.makeText(RegisterTeacher.this, "Name can only contains alphabets, space, period, " +
                    "hyphen", Toast.LENGTH_SHORT).show();
            nameEditText.setText("");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterTeacher.this, "Email address is not in proper format",
                    Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
        } else if (!phn.matches("^\\d{10}$")) {
            Toast.makeText(RegisterTeacher.this, "Mobile no must be of 10 digits only",
                    Toast.LENGTH_SHORT).show();
            phnEditText.setText("");
        } else if (!password.matches("^[a-zA-Z (?! )0-9@#_]{6,15}$")) {
            Toast.makeText(RegisterTeacher.this, "Password Length [6,15] and may include alphabets" +
                    "numbers and @, _, #", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
        }
    }
}
