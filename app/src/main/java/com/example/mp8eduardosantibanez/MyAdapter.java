package com.example.mp8eduardosantibanez;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //Provides direct reference to each of the views within a data item
    //used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder{
        //Your holder should contain a member variable for any view that will be set as you render a row
        public TextView name1;
        public TextView course1;
        public TextView grade1;

        public ViewHolder(View itemView) {
            super(itemView);
            //name1 = (TextView) itemView.findViewById(R.id.name);
            course1= (TextView) itemView.findViewById(R.id.course);
            grade1 = (TextView) itemView.findViewById(R.id.grade);
        }
    }

    private List<Grade> mQ;
    //Create constructor for class
    public MyAdapter(List<Grade> query){
        mQ=query;
    }
    @Override
    //inflate the item layout and create the holder
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        //inflate the custom layout
        View contactView=inflater.inflate(R.layout.query,parent,false);

        //Return a new Holder Instance
        ViewHolder viewHolder=new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Grade data=mQ.get(position);

        TextView text1=holder.course1;
        text1.setText(data.getcourse_name());
        TextView text2=holder.grade1;
        text2.setText(data.getgrade());
        //TextView text3=holder.name1;
        //text3.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return mQ.size();
    }
}