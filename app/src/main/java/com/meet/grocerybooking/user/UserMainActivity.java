package com.meet.grocerybooking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.adpters.ProductAdapter;
import com.meet.grocerybooking.authentication.UserLogInActivity;
import com.meet.grocerybooking.models.ProductModel;
import com.meet.grocerybooking.user.ui.category.CategoryFragment;
import com.meet.grocerybooking.user.ui.chat.ChatFragment;
import com.meet.grocerybooking.user.ui.home.HomeFragment;
import com.meet.grocerybooking.user.ui.profile.ProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {

    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.navigation_home :
                    selectorFragment = new HomeFragment();
                    break;

                case R.id.navigation_category :
                    selectorFragment = new CategoryFragment();
                    break;

//                case R.id.navigation_upload :
//                    selectorFragment = new Upl();
//                    break;

                case R.id.navigation_chat :
                    selectorFragment = new ChatFragment();
                    break;

                case R.id.navigation_profile :
                    selectorFragment = new ProfileFragment();
                    break;
            }

            if (selectorFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
            }

            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

}