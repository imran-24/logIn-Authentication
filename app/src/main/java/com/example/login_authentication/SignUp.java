package com.example.login_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    EditText email, password, name;
    TextView logIn;
    AppCompatButton signUp;
    private FirebaseAuth mAuth;
    Intent intent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        logIn = findViewById(R.id.log_in_btn);
        signUp = findViewById(R.id.sign_up_btn);
        mAuth = FirebaseAuth.getInstance();
    }

    public void toSignUp(View view) {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String user_name = name.getText().toString().trim();
        if(emailText.isEmpty()){
            email.setError("Enter an email address");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            password.setError("Enter a valid email address");
            password.requestFocus();
        }
        if(emailText.isEmpty()){
            email.setError("Enter an email");
            email.requestFocus();
            return;
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
            mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(view.GONE);
                    if(task.isSuccessful()){
                        finish();
                        Toast.makeText(SignUp.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, LogOut.class));
                    }
                    else{
                        if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            Toast.makeText(SignUp.this, "User already exist", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignUp.this, "Registration unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public void logInPage(View view) {
        intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }
}