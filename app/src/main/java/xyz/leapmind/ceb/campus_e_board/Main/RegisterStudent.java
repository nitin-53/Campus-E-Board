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

public class RegisterStudent extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);
    }

    public void studentSignUp(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.student_name);
        EditText emailEditText = (EditText) findViewById(R.id.student_email);
        EditText phnEditText = (EditText) findViewById(R.id.student_phn);
        EditText roll_noEditText = (EditText) findViewById(R.id.student_rollNo);
        EditText classEditText = (EditText) findViewById(R.id.student_class);
        EditText semEditText = (EditText) findViewById(R.id.student_semester);
        EditText sectionEditText = (EditText) findViewById(R.id.student_section);
        EditText groupEditText = (EditText) findViewById(R.id.student_group);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phn = phnEditText.getText().toString();
        String rollNo = roll_noEditText.getText().toString();
        String clas = classEditText.getText().toString();
        String sem = semEditText.getText().toString();
        String section = sectionEditText.getText().toString();
        String group = groupEditText.getText().toString();

        if (name.equals("") || email.equals("") || phn.equals("") || rollNo.equals("") ||
                clas.equals("") || sem.equals("")) {
            Toast.makeText(RegisterStudent.this, "Only Section or Group may be empty",
                    Toast.LENGTH_SHORT).show();
        } else if (!name.matches("^[\\p{L} .'-]+$")) {
            Toast.makeText(RegisterStudent.this, "Name can only contains alphabets, space, period, " +
                    "hyphen", Toast.LENGTH_SHORT).show();
            nameEditText.setText("");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterStudent.this, "Email address is not in proper format",
                    Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
        } else if (!phn.matches("^\\d{10}$")) {
            Toast.makeText(RegisterStudent.this, "Mobile no must be of 10 digits only",
                    Toast.LENGTH_SHORT).show();
            phnEditText.setText("");
        } else if (!rollNo.matches("^[Uu][EeMm]\\d{6}$")) {
            Toast.makeText(RegisterStudent.this, "Roll no is not in proper format",
                    Toast.LENGTH_SHORT).show();
            roll_noEditText.setText("");
        } else if (!clas.matches("^[a-zA-Z]{2,3}[ (?! )]?[a-zA-Z]{0,4}$")) {
            Toast.makeText(RegisterStudent.this, "Class is not in proper format",
                    Toast.LENGTH_SHORT).show();
            classEditText.setText("");
        } else if (!sem.matches("^[1-9]{1}[0]?$")) {
            Toast.makeText(RegisterStudent.this, "Semester can be in range of [1, 10]",
                    Toast.LENGTH_SHORT).show();
            semEditText.setText("");
        } else if (!section.equals("") && !section.matches("^[AB]{1}$")) {
            Toast.makeText(RegisterStudent.this, "Section can be either A or B or empty",
                    Toast.LENGTH_SHORT).show();
            sectionEditText.setText("");
        } else if (!group.equals("") && !group.matches("^[1-3]{1}$")) {
            Toast.makeText(RegisterStudent.this, "Group can be either 1 or 2 or 3 or empty",
                    Toast.LENGTH_SHORT).show();
            groupEditText.setText("");
        }
    }
}
