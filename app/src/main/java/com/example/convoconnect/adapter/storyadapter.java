package com.example.convoconnect.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devlomi.circularstatusview.CircularStatusView;
import com.example.convoconnect.Home;
import com.example.convoconnect.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

import com.example.convoconnect.model.*;


public class storyadapter extends RecyclerView.Adapter{
    Context con;
    ArrayList<storymodel>list;


    TextView text_statusname,text_statususertime;
    CircleImageView img_statususericon;
    CircularStatusView circular_status_view;

    public storyadapter(Context con,ArrayList<storymodel>list)
    {
        this.con=con;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(con).inflate(R.layout.samplestorydesign,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            text_statusname.setText("your status");
        }
        else
        {


            text_statusname.setText(list.get(position).name);
        }
//       text_statusname.setText(list.get(position).name);
        text_statususertime.setText(list.get(position).time+" | "+list.get(position).date);
        //place img_ststususericon in place of circular_status_view
        circular_status_view.setPortionsCount(list.get(position).urls.size());


        circular_status_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MyStory> myStories=new ArrayList<>();
                for(String s:list.get(position).urls){
                    myStories.add(
                     new MyStory(
                            s
                    ));
                }


                new StoryView.Builder(((Home)con).getSupportFragmentManager())
                        .setStoriesList(myStories) // Required
                        .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                        .setTitleText(list.get(position).name) // Default is Hidden
                        .setSubtitleText(list.get(position).time+"") // Default is Hidden
                        .setTitleLogoUrl("some-link") // Default is Hidden
                        .setStoryClickListeners(new StoryClickListeners() {
                            @Override
                            public void onDescriptionClickListener(int position) {
                                //your action
                            }

                            @Override
                            public void onTitleIconClickListener(int position) {
                                //your action
                            }
                        }) // Optional Listeners
                        .build() // Must be called before calling show method
                        .show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_statusname=itemView.findViewById(R.id.text_status_username);
            text_statususertime=itemView.findViewById(R.id.text_status_date_time);
            img_statususericon=itemView.findViewById(R.id.status_user_icon);
            circular_status_view=itemView.findViewById(R.id.circular_status_view);
        }
    }
}