package com.example.convoconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convoconnect.R;
import com.example.convoconnect.model.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messageadapter extends RecyclerView.Adapter {
    Context con;
    ArrayList<messagemodel>list;
    TextView text_sendermsg,tex_sendermsgtime,text_semdermsgdate,text_recivermsg,tex_recivermsgtime,text_recivermsgdate;


    public messageadapter(Context con,ArrayList<messagemodel>list)
    {
        this.con=con;
        this.list=list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1)
        {
            View v= LayoutInflater.from(con).inflate(R.layout.sendermsgsampledesign,parent,false);
            return new SenderViewHolder(v);
        }
        else
        {
            View v=LayoutInflater.from(con).inflate(R.layout.recivermsgsampledesign,parent,false);
            return new ReciverViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass()==SenderViewHolder.class)
        {
            text_sendermsg.setText(list.get(position).message);
            tex_sendermsgtime.setText(list.get(position).time);
            text_semdermsgdate.setText(list.get(position).date);

        }
        else
        {
            text_recivermsg.setText(list.get(position).message);
            tex_recivermsgtime.setText(list.get(position).time);
            text_recivermsgdate.setText(list.get(position).date);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).sender.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            text_sendermsg=itemView.findViewById(R.id.text_sendermsg);
            text_semdermsgdate=itemView.findViewById(R.id.text_sendermsgdate);
            tex_sendermsgtime=itemView.findViewById(R.id.text_msgtime);
        }
    }

    class ReciverViewHolder extends RecyclerView.ViewHolder{

        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);

            text_recivermsg=itemView.findViewById(R.id.text_recivermsg);
            text_recivermsgdate=itemView.findViewById(R.id.text_recivermsgdate);
            tex_recivermsgtime=itemView.findViewById(R.id.text_recivermsgtime);
        }
    }
}
