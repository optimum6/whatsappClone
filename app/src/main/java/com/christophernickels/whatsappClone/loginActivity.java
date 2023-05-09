package com.christophernickels.whatsappClone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class loginActivity extends AppCompatActivity {

//    Widgets
    EditText username, password;
    Button btnLogin;
    TextView txtRegister;

//    Firebase
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Instantiating widgets
        username = findViewById(R.id.edtTextUsername);
        password = findViewById(R.id.edtTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        auth = FirebaseAuth.getInstance();

//        Adding event listener for the register link
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));
            }
        });

//        Adding event listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = username.getText().toString();
                String txtPassword = password.getText().toString();

                if (txtUsername.isEmpty() || txtPassword.isEmpty()){
                    Toast.makeText(loginActivity.this, "Please fill out all fields...", Toast.LENGTH_SHORT).show();
                } else {
                    loginNow(txtUsername, txtPassword);
                }
            }
        });
    }


    private void loginNow(String username, String password) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(loginActivity.this, task -> {
            if (task.isSuccessful()){
                //                Opening the Main activity
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(loginActivity.this, "Login failed...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}