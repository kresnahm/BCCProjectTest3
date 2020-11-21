package com.example.bccprojecttest3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText emailEditText, passEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.daftarTextView).setOnClickListener(this);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email tidak boleh kosong");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passEditText.setError("Email tidak boleh kosong");
            passEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                loginUser();



                break;
            case R.id.daftarTextView:
                Intent RegistIntent = new Intent(this, RegisterActivity.class);
                startActivity(RegistIntent);
                finish();
                break;
        }
    }

}
