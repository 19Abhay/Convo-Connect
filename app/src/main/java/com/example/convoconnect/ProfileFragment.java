package com.example.convoconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.convoconnect.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#ProfileFragment()} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container,false);

        TextInputEditText text_profile_name, text_profile_password, text_profile_email,text_profile_mobno;

        TextView plain_profilename,plain_profileemail;
        MaterialButton btn_updateprofile;

        text_profile_name=v.findViewById(R.id.text_profile_name);
        text_profile_mobno=v.findViewById(R.id.text_profile_mobno);
        text_profile_password=v.findViewById(R.id.text_profile_password);
        text_profile_email=v.findViewById(R.id.text_profile_email);
        plain_profilename=v.findViewById(R.id.plain_profile_name);
        plain_profileemail=v.findViewById(R.id.plain_profile_email);
        btn_updateprofile=v.findViewById(R.id.btn_updateprofile);

        TextView text_profileabout;
        ImageView img_changeabout;

        text_profileabout=v.findViewById(R.id.text_profileabout);
        img_changeabout=v.findViewById(R.id.img_profilechangeabout);

        //show detials of current users on profile page by fetching data from database




        String currentuser= FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Toast.makeText(getContext(),snapshot.child("name")
                        //      .getValue().toString(),Toast.LENGTH_LONG).show();
                        text_profile_name.setText(snapshot.child("name").getValue().toString());
                        text_profile_mobno.setText(snapshot.child("mobile").getValue().toString());
                        text_profile_password.setText(snapshot.child("password").getValue().toString());
                        text_profile_email.setText(snapshot.child("emailid").getValue().toString());

                        plain_profilename.setText(snapshot.child("name").getValue().toString());

                        plain_profileemail.setText(snapshot.child("emailid").getValue().toString());

                        if (snapshot.child("about").getValue()!=null)
                        {
                            text_profileabout.setText(snapshot.child("about").getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getContext(), "Server error", Toast.LENGTH_LONG).show();

                    }
                });


        //update name,mobno,email,password on click of update profile button

        btn_updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=text_profile_name.getText().toString();
                String mobno=text_profile_mobno.getText().toString();
                String email=text_profile_email.getText().toString();
                String password=text_profile_password.getText().toString();
                HashMap<String,Object> hm=new HashMap<>();
                hm.put("name",name);
                hm.put("mobno",mobno);
                hm.put("emailid",email);
                hm.put("password",password);

                FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(hm);
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_LONG).show();
            }
        });

        img_changeabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit=new EditText(getContext());
                // Toast.makeText(getContext(), "Change", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertbox=new AlertDialog.Builder(getContext());
                alertbox.setTitle("What's your Mood Today");
                alertbox.setMessage("Enter about yourself");
                alertbox.setView(edit);
                alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String,Object> hm=new HashMap<>();
                        hm.put("about",edit.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(hm);
                    }
                });
                alertbox.setNegativeButton("Dissmiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Negative", Toast.LENGTH_LONG).show();
                    }
                });
                alertbox.show();

            }
        });
        return v;
}
}