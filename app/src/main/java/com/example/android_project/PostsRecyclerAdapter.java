package com.example.android_project;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.model.FirebaseStoreageModel;
import com.example.android_project.model.Firestore;
import com.example.android_project.model.Model;
//import com.example.android_project.model.ObjectItem;
import com.example.android_project.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;


class PostViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView city;
    TextView price;
    TextView hand;
    List<Post> data;
    ImageView picture;


    public PostViewHolder(@NonNull View itemView, List<Post> data, boolean is_edit_post) {
        super(itemView);
        picture = itemView.findViewById(R.id.postlistrow_image);
        title = itemView.findViewById(R.id.postlistrow_title);
//        description = itemView.findViewById(R.id.postslistrow_description);
        price = itemView.findViewById(R.id.postlistrow_price);
//        email = itemView.findViewById(R.id.postslistrow_email);
//        Button convert = itemView.findViewById(R.id.postslistrow_convert_btn);
        Button editPostBtn = itemView.findViewById(R.id.postlistrow_editBtn);
        if (!is_edit_post) {
            editPostBtn.setVisibility(View.GONE);
        }
        this.data = data;

        editPostBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pos = (int)price.getTag();
                Post post = data.get(pos);
                Bundle bundle = new Bundle();
                bundle.putString("post_id", post.id);
//                Navigation.findNavController(v).navigate(R.id.action_userPostsListFragment_to_editPostFragment, bundle);
            }
        });
    }

    public void bind(Post post, int pos) {
//        Model.instance().getBitMap(post.pic_path,picture);
//        picture.setImageBitmap(b);
        hand.setText(post.hand);
        title.setText(post.title);
        city.setText(post.city);
        price.setText(post.price + " NIS");
        price.setTag(pos);
        FirebaseStorage.getInstance().getReference().child("images/").child(post.imagePath+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(picture);
            }
        });
    }
}

public class PostsRecyclerAdapter extends  RecyclerView.Adapter<PostViewHolder>{
    OnPostClickListener listener;

    LayoutInflater inflater;
    List<Post> data;
    boolean is_edit_post;

    public void setData(List<Post> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    public void setOnPostClickListener(OnPostClickListener listener) {
        this.listener = listener;

    }
    public static interface OnPostClickListener{
        void onItemClick(int pos);
    }

    public PostsRecyclerAdapter(LayoutInflater inflater, List<Post> data, boolean is_edit_post )
    {
        this.data = data;
        this.inflater = inflater;
        this.is_edit_post = is_edit_post;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.object_list_row, parent, false);
        return new PostViewHolder(view, data, is_edit_post);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
    }

    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }
        return data.size();
    }


}