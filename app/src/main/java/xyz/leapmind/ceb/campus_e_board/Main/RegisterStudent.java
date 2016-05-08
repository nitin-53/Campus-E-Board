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

public class RegisterStudent extends AppCompatActivity {
    private static final String TAG = RegisterStudent.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_student);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterStudent.this,
                    Student.class);
            startActivity(intent);
            finish();
        }
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
        EditText passwordEditText = (EditText) findViewById(R.id.student_password);

        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phn = phnEditText.getText().toString().trim();
        String rollNo = roll_noEditText.getText().toString().trim();
        String clas = classEditText.getText().toString().trim();
        String sem = semEditText.getText().toString().trim();
        String section = sectionEditText.getText().toString().trim();
        String group = groupEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.equals("") || email.equals("") || phn.equals("") || rollNo.equals("") ||
                clas.equals("") || sem.equals("") || password.equals("")) {
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
        } else if (!password.matches("^[a-zA-Z (?! )0-9@#_]{6,15}$")) {
            Toast.makeText(RegisterStudent.this, "Password Length [6,15] and may include alphabets" +
                    "numbers and @, _, #", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
        } else {
            registerUser(name, email, phn, rollNo, clas, sem, section, group, password);
        }
    }

    /**
     * Function to store user in MySQL database will post params
     * to register url
     */
    private void registerUser(final String name, final String email, final String phn, final String rollNo,
                              final String clas, final String sem, final String section,
                              final String group, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!",
                                Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterStudent.this,
                                MainActivity.class);
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
                        error.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("rollNo", rollNo);
                params.put("class", clas);
                params.put("sem", sem);
                params.put("section", section);
                params.put("grp", group);
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
