package com.example.bccprojecttest3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;


    EditText emailRegistEditText, passRegistEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.loginTextView).setOnClickListener(this);

        emailRegistEditText = (EditText) findViewById(R.id.emailRegistEditText);
        passRegistEditText = (EditText) findViewById(R.id.passRegistEditText);
    }

    private void daftarUser(){
        String email = emailRegistEditText.getText().toString().trim();
        String password = passRegistEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailRegistEditText.setError("E-Mail tidak boleh kosong");
            emailRegistEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailRegistEditText.setError("E-Mail tidak valid");
            emailRegistEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passRegistEditText.setError("Password tidak boleh kosong");
            passRegistEditText.requestFocus();
            return;
        }
        if(password.length()<6){
            passRegistEditText.setError("Password kurang dari 6 digit");
            passRegistEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Pengguna berhasil didaftarkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, userInfoActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                daftarUser();

                break;
            case R.id.loginTextView:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }
    }
}
