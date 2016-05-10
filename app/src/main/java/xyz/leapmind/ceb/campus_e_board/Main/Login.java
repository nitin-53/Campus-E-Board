package xyz.leapmind.ceb.campus_e_board.Main;

/**
 * Created by nitin on 11/4/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity implements OnClickListener {
    private static final String TAG = Login.class.getSimpleName();
    String msg = "Android : ";
    private EditText userId, pass;
    private Button login1;
    private String userName, password, rollNo, pfn;
    private TextView forgot;
    private ProgressDialog pDialog;
    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userId = (EditText) findViewById(R.id.userID);
        pass = (EditText) findViewById(R.id.password);
        login1 = (Button) findViewById(R.id.btn_login);
        forgot = (TextView) findViewById(R.id.forgot);

        login1.setOnClickListener(Login.this);
        forgot.setOnClickListener(Login.this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, Student.class);
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
                userName = userId.getText().toString().trim();
                password = pass.getText().toString().trim();

                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(Login.this, "Don't leave empty fields", Toast.LENGTH_SHORT).show();
                } else if (isThisStudent) {
                    if (userName.matches("^[Uu][EeMm]\\d{6}$")) {
                        rollNo = userName.toUpperCase();
                        checkLogin(rollNo, password);
                        /*if (userName.equals("UE128001") && password.equals("123")) {
                            Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();
                            Intent obj1 = new Intent(Login.this, Student.class);
                            startActivity(obj1);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "INVALID CREDENTIALS !", Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            pass.setText("");
                        }*/
                    } else {
                        Toast.makeText(Login.this, "UserID Pattern is wrong", Toast.LENGTH_SHORT).show();
                        userId.setText("");
                        pass.setText("");
                    }
                } else if (isThisTeacher) {
                    if (userName.matches("^\\d{4}$")) {
                        pfn = userName;
                        checkLoginTeacher(pfn, password);



                        /*if (userName.equals("ks18994ks@gmail.com") && password.equals("123")) {
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
                    }*/
                    }
                }

                /*else if (userName.equals("Teacher") && password.equals("123")) {
                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    Intent obj = new Intent(Login.this, Teacher.class);
                    startActivity(obj);
                    finish();
                } else if (userName.equals("Student") && password.equals("123")) {
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

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String userName, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();
                        // Create login session
                        session.setLogin(true);

                        // Launch main activity
                        Intent intent = new Intent(Login.this,
                                Student.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("rollNo", userName);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details in mysql db
     */
    private void checkLoginTeacher(final String userName, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN_T, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();
                        // Create login session
                        session.setLogin(true);

                        // Launch main activity
                        Intent intent = new Intent(Login.this,
                                Teacher.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.toString(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pfn", userName);
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
