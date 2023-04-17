package com.example.android_project;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_project.model.Model;
import com.example.android_project.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostItemViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemViewFragment extends Fragment {

    Post post = new Post();

    TextView titleTv;
    TextView descriptionTv;
    TextView handTv;
    TextView cityTv;
    TextView notesTv;
    ImageView picture;
    TextView emailTv;
    TextView userPhoneNumberTv;

    private String postId;

    public PostItemViewFragment() {
        // Required empty public constructor
    }


    public static PostItemViewFragment newInstance(){
        PostItemViewFragment fragment = new PostItemViewFragment();
        Bundle args = new Bundle();
//        args.putString("ID", objectId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            postId = bundle.getString("PostId");
            Log.d("ffff" , postId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post_item_view, container, false);
//
        titleTv = view.findViewById(R.id.post_view_title);
        handTv = view.findViewById(R.id.post_view_hand);
        descriptionTv= view.findViewById(R.id.post_view_description);
        notesTv= view.findViewById(R.id.post_view_notes);
        cityTv = view.findViewById(R.id.post_view_city);
        emailTv = view.findViewById(R.id.post_view_email);
        picture= view.findViewById(R.id.post_view_image);
//
      Model.instance().getPostView(postId, titleTv, descriptionTv, handTv,emailTv, cityTv,notesTv, picture);


        return view;
    }

    public void setObjectValues(){
        Log.d("nameeeeee", "name" + post.title);
        titleTv.setText(post.getTitle());
        descriptionTv.setText(post.getDescription());
        handTv.setText(post.getHand().toString());
        cityTv.setText(post.getCity());
        notesTv.setText(post.getNotes());
        emailTv.setText(post.getEmail());
        userPhoneNumberTv.setText(post.getPhoneNumber());

        FirebaseStorage.getInstance().getReference().child("images/").child(post.imagePath+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(picture);
            }
        });
    }
}