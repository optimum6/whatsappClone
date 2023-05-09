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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

//    Widgets
    EditText username, email, password;
    Button btnRegister;

//    Firebase
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Instantiating widgets
        username = findViewById(R.id.edtTextUsername);
        password = findViewById(R.id.edtTextPassword);
        email = findViewById(R.id.edtTextEmail);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();
        
//        Adding event listener for register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = username.getText().toString();
                String txtPassword = password.getText().toString();
                String txtEmail = email.getText().toString();

                if (txtUsername.isEmpty() || txtPassword.isEmpty() || txtEmail.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill out all fields...", Toast.LENGTH_SHORT).show();
                } else {
                    registerNow(txtUsername, txtEmail, txtPassword);
                }
            }
        });


    }

    private void registerNow(final String username, String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                String userId = firebaseUser.getUid();

                db = FirebaseDatabase.getInstance().getReference("MyUsers").child(userId);

//                Hashmaps
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", userId);
                hashMap.put("username", username);
                hashMap.put("imageUrl", "default");

//                Opening the Main activity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

}