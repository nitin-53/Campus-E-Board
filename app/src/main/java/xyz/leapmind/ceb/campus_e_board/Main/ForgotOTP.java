package xyz.leapmind.ceb.campus_e_board.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xyz.leapmind.ceb.campus_e_board.AppAssist.SessionManager;
import xyz.leapmind.ceb.campus_e_board.AppControl.AppConfig;
import xyz.leapmind.ceb.campus_e_board.AppControl.AppController;
import xyz.leapmind.ceb.campus_e_board.R;

/**
 * Created by Karan on 5/8/2016.
 */
public class ForgotOTP extends AppCompatActivity {

    private static final String TAG = ForgotOTP.class.getSimpleName();
    String msg = "Android : ";
    private ProgressDialog pDialog;
    private SessionManager session;
    private EditText otpEditText, newPasswdEditText, confirmPasswdEditText;
    private String otp, newPasswd, confirmPasswd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_otp);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish(); // or do something else
    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    public void forgotOtpValidatons(View view) {
        otpEditText = (EditText) findViewById(R.id.unique_no);
        newPasswdEditText = (EditText) findViewById(R.id.new_passwd);
        confirmPasswdEditText = (EditText) findViewById(R.id.confirm_passwd);
        otp = otpEditText.getText().toString().trim();
        newPasswd = newPasswdEditText.getText().toString().trim();
        confirmPasswd = confirmPasswdEditText.getText().toString().trim();


        if (otp.equals("") || newPasswd.equals("") || (confirmPasswdEditText.equals(""))) {
            Toast.makeText(ForgotOTP.this, "Don't Leave Empty Fields", Toast.LENGTH_LONG).show();
        } else if (!otp.matches("^\\d{6}$")) {
            Toast.makeText(ForgotOTP.this, "OTP must be of 10 digits only", Toast.LENGTH_SHORT).show();
            otpEditText.setText("");
        } else if (!newPasswd.matches("^[a-zA-Z (?! )0-9@#_]{6,15}$")) {
            Toast.makeText(ForgotOTP.this, "Password Length [6,15] and may include alphabets" +
                    "numbers and @, _, #", Toast.LENGTH_SHORT).show();
            newPasswdEditText.setText("");
            confirmPasswdEditText.setText("");
        } else if (!newPasswd.equals(confirmPasswd)) {
            Toast.makeText(ForgotOTP.this, "Password does not match", Toast.LENGTH_LONG).show();
            newPasswdEditText.setText("");
            confirmPasswdEditText.setText("");
        } else {
            requestNewPassword(otp, newPasswd);
        }
    }

    /**
     * Function to store new Password in DB
     */
    private void requestNewPassword(final String otp, final String newPasswd) {
        // Tag used to cancel the request
        String tag_string_req = "req_new_passwd";

        pDialog.setMessage("Registering new passsword...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RESET_PASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Requesting New Password Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User reset password succesfully
                        Toast.makeText(getApplicationContext(), "You reset your password Successfully",
                                Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(ForgotOTP.this, MainActivity.class);
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                    } else {

                        // Error occurred in requesting new password
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Password Request Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);
                params.put("password", newPasswd);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
