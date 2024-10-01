package com.example.convoconnect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import com.example.convoconnect.model.usermodel;
import com.example.convoconnect.adapter.useradapter;
public class Chat extends Fragment {

    public Chat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        ArrayList<usermodel>list=new ArrayList<>();


        RecyclerView userecycler=v.findViewById(R.id.user_recycler);
        useradapter adapter=new useradapter(getContext(),list);
        userecycler.setAdapter(adapter);
        userecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //lets bind adapter to the recycler view


        //Coading to fetch data of all users from database
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    // Toast.makeText(getContext(),user.getKey(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(),user.child("name").getValue()+"", Toast.LENGTH_SHORT).show();
                    usermodel u1 = new usermodel();
                    u1.uid = user.getKey().toString();
                    u1.name = user.child("name").getValue().toString();
                    u1.mobno = user.child("mobile").getValue().toString();
                    u1.emailid = user.child("emailid").getValue().toString();
                    u1.password = user.child("password").getValue().toString();
                    u1.gender = user.child("gender").getValue().toString();
                    if (!u1.uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        list.add(u1);
                    } else {
                        ((Home) getActivity()).getSupportActionBar().setSubtitle("Welcome," + u1.name);
                    }

                }
                Toast.makeText(getContext(),list.size()+"",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}

/*

                if(!u1.uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                }
                else
                {
                    ((Home)getActivity()).getSupportActionBar().setSubtitle("Welcome,"+u1.name);
                }
 */