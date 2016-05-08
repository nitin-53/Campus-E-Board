package xyz.leapmind.ceb.campus_e_board.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xyz.leapmind.ceb.campus_e_board.R;

/**
 * Created by Karan on 5/8/2016.
 */
public class ForgotOTP extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_otp);
    }

    public void forgotOtpValidatons(View view) {
        EditText otpEditText = (EditText) findViewById(R.id.unique_no);
        EditText newPasswdEditText = (EditText) findViewById(R.id.new_passwd);
        EditText confirmPasswdEditText = (EditText) findViewById(R.id.confirm_passwd);

        String otp = otpEditText.getText().toString().trim();
        String newPasswd = newPasswdEditText.getText().toString().trim();
        String confirmPasswd = confirmPasswdEditText.getText().toString().trim();

        if (otp.equals("") || newPasswd.equals("") || (confirmPasswdEditText.equals(""))) {
            Toast.makeText(ForgotOTP
                            .this, "Don't Leave Empty Fields",
                    Toast.LENGTH_LONG).show();
        } else if (!newPasswd.equals(confirmPasswd)) {
            Toast.makeText(ForgotOTP.this, "Passwords don't match...", Toast.LENGTH_LONG).show();
            newPasswdEditText.setText("");
            confirmPasswdEditText.setText("");
        } else if (!newPasswd.matches("^[a-zA-Z (?! )0-9@#_]{6,15}$")) {
            Toast.makeText(ForgotOTP.this, "Password Length [6,15] and may include alphabets" +
                    "numbers and @, _, #", Toast.LENGTH_SHORT).show();
            newPasswdEditText.setText("");
        }
    }
}
