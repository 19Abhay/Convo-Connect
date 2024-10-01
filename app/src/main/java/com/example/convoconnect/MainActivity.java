package com.example.convoconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    TextView text_gotoregister;
    MaterialButton btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //hide action bar
        if (getSupportActionBar() != null) ;
        getSupportActionBar().hide();

        TextInputEditText txt_loginemail, txt_loginpassword;
        txt_loginemail = findViewById(R.id.txt_loginemail);
        txt_loginpassword = findViewById(R.id.txt_loginpassword);


        text_gotoregister = findViewById(R.id.text_gotoregister);

        text_gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent class is used redirect from one activity to another activity
                //implicit:transfer you fromr one page to another page
                //explicit:transfer you from one app to another app
                Intent in = new Intent(MainActivity.this, Registeration.class);
                startActivity(in);
            }
        });
        btn_login = findViewById(R.id.btn_signin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;

                email = txt_loginemail.getText().toString();
                password = txt_loginpassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Input email and Password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Welcome to Mundir", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(MainActivity.this, Home.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "User Not Found",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}