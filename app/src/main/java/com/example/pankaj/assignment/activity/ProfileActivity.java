package com.example.pankaj.assignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pankaj.assignment.R;
import com.example.pankaj.assignment.application.ApplicationData;
import com.example.pankaj.assignment.model.UserInfo;
import com.example.pankaj.assignment.preferences.PreferenceManager;
import com.example.pankaj.assignment.sqlite.DatabaseHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pankaj on 22-06-2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;
    private CircleImageView profile_image;
    private TextView ProfileName;
    private TextView ProfileEmail;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String photourl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setTitle("Profile");
         databaseHandler = new DatabaseHandler(this);
        initView();
        initData();


    }

    private void initData() {
        List<UserInfo> userInfoList = databaseHandler.getAllUserInfo(1);
        for(UserInfo userInfo:userInfoList){
            int id = userInfo.getId();
             firstname = userInfo.getFirstname();
             lastname = userInfo.getLastname();
             email = userInfo.getEmail();
             gender = userInfo.getGender();
             photourl = userInfo.getPhotourl();
        }
        if(!TextUtils.isEmpty(firstname)&&!TextUtils.isEmpty(lastname)){
            ProfileName.setText(firstname+" "+lastname);
        }else{
            ProfileName.setText(ApplicationData.EMPTY);
        }

        if(!TextUtils.isEmpty(email)){
            ProfileEmail.setText(email);
        }else{
            ProfileEmail.setText(ApplicationData.EMPTY);

        }
        if(!TextUtils.isEmpty(photourl)){
            Picasso.with(this)
                    .load(photourl)
                    .error(R.drawable.ic_userprofile)
                    .into(profile_image);
        }else {
            Picasso.with(this)
                    .load(R.drawable.ic_userprofile)
                    .into(profile_image);
        }

    }

    private void initView() {
         profile_image = (CircleImageView)findViewById(R.id.profile_image);
         ProfileName = (TextView) findViewById(R.id.ProfileName);
        ProfileEmail = (TextView)findViewById(R.id.ProfileEmail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
