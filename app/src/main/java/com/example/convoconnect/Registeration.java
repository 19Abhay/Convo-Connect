package com.example.convoconnect;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.RegistrationManager;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registeration extends AppCompatActivity {

    TextView text_gotomain;

    MaterialButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        //hide action bar
     /*   getSupportActionBar().hide();
        if(getSupportActionBar()!=null);

        text_gotomain=findViewById(R.id.text_gotomain);

        text_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent class is used redirect from one activity to another activity
                //implicit:transfer you fromr one page to another page
                //explicit:transfer you from one app to another app
                Intent in=new Intent(Registeration.this, MainActivity.class);
                startActivity(in);


            }
        });

  //      btn_login = findViewById(R.id.btn_signUp);

    //    btn_login.setOnClickListener(new View.OnClickListener() {
      //      @Override
        //    public void onClick(View view) {
          //      Intent in = new Intent(Registeration.this, home.class);
            //    startActivity(in);




            }
        });

    };*/
        //save data in database
        //------get data of form to store to database--------------


        TextInputEditText txt_name, txt_email, txt_mobno, txt_password;
        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_mobno = findViewById(R.id.mobile);
        String name, email, mobno, password;

        MaterialButton btn_signup;
        btn_signup = findViewById(R.id.btn_signUp);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, mobile, password;
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                mobile = txt_mobno.getText().toString();
                password = txt_password.getText().toString();
                if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registeration.this, "please fill all fields properly",
                            Toast.LENGTH_SHORT).show();
                }
                {
                    //save data in database
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                String uid=task.getResult().getUser().getUid();
                                HashMap<String,String> user =new HashMap<>();
                                user.put("name",name);
                                user.put("mobile",mobile);
                                user.put("emailid",email);
                                user.put("password",password);
                                user.put("gender","NA");
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Registeration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Registeration.this, MainActivity.class);
                                                startActivity(intent);



                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(Registeration.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registeration.this,e.getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                   // FirebaseDatabase.getInstance().getReference().setValue("Hello");


                }
            }
        });

        TextView txt_gotomain;

        text_gotomain = findViewById(R.id.text_gotomain);
        text_gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent class is used redirect from one activity to another activity
                //implicit:transfer you fromr one page to another page
                //explicit:transfer you from one app to another app
                Intent in = new Intent(Registeration.this, MainActivity.class);
                startActivity(in);
            }
   });
    }
}