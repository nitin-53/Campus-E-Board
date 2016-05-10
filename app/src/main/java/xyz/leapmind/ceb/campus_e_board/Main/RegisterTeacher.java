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

public class RegisterTeacher extends AppCompatActivity {
    private static final String TAG = RegisterTeacher.class.getSimpleName();
    String msg = "Android : ";
    private ProgressDialog pDialog;
    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_teacher);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterTeacher.this,
                    Teacher.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish(); // or do something else
    }

    public void teacherSignUp(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.teacher_name);
        EditText emailEditText = (EditText) findViewById(R.id.teacher_email);
        EditText phnEditText = (EditText) findViewById(R.id.teacher_phn);
        EditText passwordEditText = (EditText) findViewById(R.id.teacher_password);
        EditText pfnEditText = (EditText) findViewById(R.id.teacher_pf);
        EditText confPasswdEditText = (EditText) findViewById(R.id.conf_teacher_password);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phn = phnEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String pfn = pfnEditText.getText().toString().trim();
        String confPasswd = confPasswdEditText.getText().toString().trim();

        if (name.equals("") || email.equals("") || phn.equals("") || pfn.equals("") || confPasswd.equals("")) {

            Toast.makeText(RegisterTeacher.this, "All fields must be filled",
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
        } else if (!confPasswd.equals(password)) {
            Toast.makeText(RegisterTeacher.this, "Passwords don't match",
                    Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
            confPasswdEditText.setText("");
        } else if (!pfn.matches("^\\d{4}$")) {
            Toast.makeText(RegisterTeacher.this, "P. F. no must be of 4 digits only",
                    Toast.LENGTH_SHORT).show();
            pfnEditText.setText("");
        } else {
            registerUser(name, email, phn, pfn, password);
        }
    }


    /**
     * Function to store user in MySQL database will post params
     * to register url
     */
    private void registerUser(final String name, final String email, final String phn, final String pfn,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER_T_EMAIL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        Toast.makeText(getApplicationContext(), "User successfully registered. " +
                                        "Please verify email by clicking link in email!",
                                Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterTeacher.this,
                                MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("phn", phn);
                params.put("pfn", pfn);
                params.put("password", password);

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
