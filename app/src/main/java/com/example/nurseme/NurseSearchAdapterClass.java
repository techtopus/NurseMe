package com.example.nurseme;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ConcurrentModificationException;
import java.util.List;

public class NurseSearchAdapterClass extends RecyclerView.Adapter<NurseSearchAdapterClass.ViewHolder> {

    private List<NurseRecyclerClass> listItems;
    private Context context;


    public NurseSearchAdapterClass(List<NurseRecyclerClass> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final NurseRecyclerClass listItem=listItems.get(i);
        viewHolder.username.setText(listItem.getUsername());
        viewHolder.location.setText(listItem.getLocation());
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NurseRequestActivity.class);
                intent.putExtra("nurseEmail",listItem.getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username,location;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.textView41);
            location=itemView.findViewById(R.id.textView43);
            cardview=itemView.findViewById(R.id.cardview);
        }
    }

}

