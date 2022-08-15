package com.example.login_authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogOut extends AppCompatActivity {
    AppCompatButton logOut;
    TextView info;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        logOut = findViewById(R.id.log_out_btn);
        mAuth = FirebaseAuth.getInstance();
        info = findViewById(R.id.info);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(LogOut.this, MainActivity.class));
        }
        else{
            info.setText(currentUser.getDisplayName());
        }
    }

    public void toLogOut(View view) {
        mAuth.signOut();
        startActivity(new Intent(LogOut.this, MainActivity.class));
    }
}