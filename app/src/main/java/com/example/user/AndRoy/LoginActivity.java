package com.example.user.AndRoy;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This activity provides login and register for the user by firebase
 */

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.login));
        }

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        mProgressbar = (ProgressBar) findViewById(R.id.pgbar_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressbar.setVisibility(View.VISIBLE);
                if (!verifyEmail()){
                    mProgressbar.setVisibility(View.GONE);
                    return;
                }


               /* boolean isValid = MyPreferences.init(getApplicationContext())
                        .logInUser(email.getText().toString(), password.getText().toString());

                if (isValid) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("email", email.getText().toString().trim());
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main);
                    Snackbar.make(constraintLayout, getResources().getString(R.string.err_login), Snackbar.LENGTH_SHORT).show();
                }*/

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.getText().toString().trim(),
                        password.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mProgressbar.setVisibility(View.GONE);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        LoginActivity.this.finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressbar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
            }
        });
    }



    private boolean verifyEmail() {
        TextInputLayout emaillayout  = (TextInputLayout) findViewById(R.id.emailInputLayout);
        String emailText = email.getText().toString().trim();
        String regEx =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher = Pattern.compile(regEx).matcher(emailText);

        if (matcher.matches()) {
            emaillayout.setError("");
            return true;
        } else {
            email.setError(getResources().getString(R.string.err_email));
            emaillayout.setError(getResources().getString(R.string.err_email));
            return false;
        }
    }

    /*private boolean checkUser(String email, String password) {
        SharedPreferences mSharedPrefrences = getSharedPreferences(PrefContract.Login.PREF_NAME, Context.MODE_PRIVATE);
        String email_cross = mSharedPrefrences.getString(PrefContract.Login.EMAIL, "default");
        String pass_cross = mSharedPrefrences.getString(PrefContract.Login.PASSWORD, "pass");
        if (email_cross.equals(email) && pass_cross.equals(password)) {
            return true;
        } else return false;
    }*/
}