package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xyz.leapmind.ceb.campus_e_board.R;

public class Forgot extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
    }

    public void forgotPassword(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.registered_emailId);

        String email = emailEditText.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(Forgot.this, "Enter Email-Id",
                    Toast.LENGTH_SHORT).show();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(Forgot.this, "Email address is not in proper format",
                    Toast.LENGTH_SHORT).show();
            emailEditText.setText("");
        } else {
            Intent intent_obj = new Intent(Forgot.this, ForgotOTP.class);
            startActivity(intent_obj);
            finish();
        }
    }
}
