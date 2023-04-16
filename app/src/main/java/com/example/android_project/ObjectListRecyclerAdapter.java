package com.example.android_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.model.ObjectItem;
//import com.squareup.picasso.Picasso;

import java.util.List;


class ObjectViewHolder extends RecyclerView.ViewHolder{
    TextView name;
    TextView hand;
    TextView city;
    CheckBox cb;
    List<ObjectItem> data;
    ImageView objectImage;

    public ObjectViewHolder(@NonNull View itemView, ObjectListRecyclerAdapter.OnItemClickListener listener, List<ObjectItem> data) {
        super(itemView);
        this.data = data;
        name= itemView.findViewById(R.id.postlistrow_title);
        hand= itemView.findViewById(R.id.postlistrow_hand);
        city = itemView.findViewById(R.id.postlistrow_Location);
        objectImage= itemView.findViewById(R.id.postlistrow_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(ObjectItem objectItem, int pos) {
        name.setText(objectItem.name);
        hand.setText(objectItem.hand + "");
        city.setText(objectItem.city);
        if (objectItem.getImageUrl()  != null && objectItem.getImageUrl().length() > 5) {
//            Picasso.get().load(objectItem.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImage);
        }else{
            objectImage.setImageResource(R.drawable.example);
        }
    }
}

public class ObjectListRecyclerAdapter extends RecyclerView.Adapter<ObjectViewHolder>{
    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<ObjectItem> data;

    public void setData(List<ObjectItem> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public ObjectListRecyclerAdapter(LayoutInflater inflater, List<ObjectItem> data){
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public ObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.object_list_row,parent,false);
        return new ObjectViewHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectViewHolder holder, int position) {
        ObjectItem st = data.get(position);
        holder.bind(st,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}

