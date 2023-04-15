package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.databinding.FragmentSignupBinding;
import com.example.finalproject.model.Model;
import com.example.finalproject.model.User;

public class SignUpFragment extends Fragment {
    FragmentSignupBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
//        parentActivity.addMenuProvider(new MenuProvider() {
//            @Override
//            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menu.removeItem(R.id.signupFragment);
//            }
//
//            @Override
//            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
//                return false;
//            }
//        },this, Lifecycle.State.RESUMED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.singUpbtn.setOnClickListener(view1 -> {
            String name = "shoval";
            String email = "aharoni";
            String userId = "3152";
            String avatar = "3152";
            String phone = "0502030860";
            String city = "rhos";
//            String email = binding.emailSU.getText().toString();
//            String phone = binding.phoneSU.getText().toString();
//            String city = binding.citySU.getText().toString();
//            String name = binding.nameSU.getText().toString();
//            String userId = binding.idSU.getText().toString();
//            User user = new User(userId, name,avatar,email,city,phone);
            User user = new User(name,email,city,phone);
//            Model.instance().addUser(user,()->{
//                 Navigation.findNavController(view1).popBackStack();
//            });
        });

//        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.FragmentSignupBinding,false));
        return view;
    }

}