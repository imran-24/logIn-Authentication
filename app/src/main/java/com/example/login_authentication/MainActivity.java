package com.example.login_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password, emailErrorText, passwordErrorText;
    AppCompatButton logIn;
    TextView signUp;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.sign_up_link);
        logIn = findViewById(R.id.log_in_btn);
        mAuth = FirebaseAuth.getInstance();
    }

    public void toLogIn(View view) {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if(emailText.isEmpty()){
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            password.setError("Enter a valid email address");
            password.requestFocus();
            return ;
        }

        if(passwordText.isEmpty()){
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        if(password.length() < 6){
            password.setError("Minimum length of a password should be 6");
            password.requestFocus();
            return;
        }
        else{
            progressBar.setVisibility(view.VISIBLE);
            mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(view.GONE);
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        finish();
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LogOut.class));
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Login Failed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void signUpPage(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);

    }
}