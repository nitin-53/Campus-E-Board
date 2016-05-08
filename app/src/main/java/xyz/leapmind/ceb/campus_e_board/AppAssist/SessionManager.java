package xyz.leapmind.ceb.campus_e_board.AppAssist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by nitin on 25/4/16.
 * <p/>
 * This class maintains session data across the app using the SharedPreferences. We store a boolean
 * flag ' isLoggedIn ' in shared preferences to check the login status.
 */
public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "CEBLogin";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
