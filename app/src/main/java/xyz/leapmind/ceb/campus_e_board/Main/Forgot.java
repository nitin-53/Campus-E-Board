package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

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

public class Forgot extends AppCompatActivity {
    private static final String TAG = Forgot.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;


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
            sendMail(email);
        }
    }

    /**
     * function to verify login details in mysql db
     */
    private void sendMail(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_send_email";

        pDialog.setMessage("Submitting ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FORGOT_EMAIL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Submission Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully submitted email
                        Toast.makeText(Forgot.this, "Email submitted successfully !", Toast.LENGTH_SHORT).show();
                        // Create login session
                        session.setLogin(true);

                        // Launch main activity
                        Intent intent = new Intent(Forgot.this,
                                ForgotOTP.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in submission of email. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Email submission Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
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
