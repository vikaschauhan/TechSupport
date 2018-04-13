package in.infocruise.techsupport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import in.infocruise.techsupport.Model.User;
import in.infocruise.techsupport.helper.MySingleton;
import in.infocruise.techsupport.helper.PhoneCallStateReceiver;
import in.infocruise.techsupport.helper.SQLiteHandler;
import in.infocruise.techsupport.helper.SessionManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * login url for login verification to get username and password.
     */
    private String login_url = "http://202.83.19.113:84/service.asmx/VerifyLogin?";
    // log statement label
    private static final String TAG = LoginActivity.class.getSimpleName();
    // declaring Session Manager
    private SessionManager session;
    private SQLiteHandler db;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mPasswordInput, mNameInput;
    private View mProgressView;
    private View mLoginFormView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Set up the login form.
        mNameInput =  findViewById(R.id.username_input);

        mPasswordInput = findViewById(R.id.password_input);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        if (session.isUserLoggedIn()){
            mNameInput.setText(session.getUserDetails().get("name"));
            mPasswordInput.setText(session.getUserDetails().get("password"));

            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
           // startActivity( new Intent(getApplicationContext(), DashboardActivity.class));
        }

    }








    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mNameInput.setError(null);
        mPasswordInput.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordInput.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordInput;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(name)) {
            mNameInput.setError(getString(R.string.error_field_required));
            focusView = mNameInput;
            cancel = true;
        } else if (!isNameValid(name)) {
            mNameInput.setError(getString(R.string.error_invalid_email));
            focusView = mNameInput;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(name, password);
            //mAuthTask.execute((Void) null);
            verifyLogin(name, password);
        }
    }

    private boolean isNameValid(String name){
        return name.contains("");
    }


    private boolean isPasswordValid(String password) {
        //check for password length greater than 4
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
   private void verifyLogin(final String NAME, final String PASSWORD){
       login_url += "name=" + NAME + "&password=" + PASSWORD;
       JsonArrayRequest request = new JsonArrayRequest(login_url,
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                       try {
                           for (int i = 0; i < response.length(); i++) {
                               JSONObject techSupport = (JSONObject) response.get(i);
                               String userId = techSupport.getString("id");
                               String username = techSupport.getString("name");
                               String passwordC = techSupport.getString("password");
                               User user1 = new User(username,passwordC);

                               db.createUser(user1);


                               db.closeDB();
                               if (Objects.equals(NAME, username) & Objects.equals(PASSWORD, passwordC)) {
                                   // Create login session
                                   //session.setLogin(true);
                                   session.createUserLoginSession(NAME,
                                           PASSWORD);


                                   Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                   intent.putExtra("userId", userId);
                                   intent.putExtra("password",passwordC);
                                   intent.putExtra("username",username);
                                   startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                                   finish();
                               } else {
                                   Toast.makeText(getApplicationContext(), "Error: Wrong Username or Password", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                                   startActivity(intent);

                               }

                           }

                       } catch (JSONException e) {
                           e.printStackTrace();
                           Toast.makeText(getApplicationContext(),
                                   "Error: " + e.getMessage(),
                                   Toast.LENGTH_LONG).show();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               VolleyLog.d(TAG, "Error: " + error.getMessage());
               Toast.makeText(getApplicationContext(),
                       error.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
       // Add a request (in this example, called stringRequest) to your RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(request);
   }


//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }


}

