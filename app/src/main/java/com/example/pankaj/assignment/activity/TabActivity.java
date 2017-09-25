package com.example.pankaj.assignment.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pankaj.assignment.R;
import com.example.pankaj.assignment.application.ApplicationData;
import com.example.pankaj.assignment.fragment.PopularFragment;
import com.example.pankaj.assignment.fragment.TopFragment;
import com.example.pankaj.assignment.model.UserInfo;
import com.example.pankaj.assignment.preferences.PreferenceManager;
import com.example.pankaj.assignment.sqlite.DatabaseHandler;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pankaj on 21-06-2017.
 */

public class TabActivity extends AppCompatActivity{
    private CallbackManager callbackManager;
    private com.facebook.login.widget.LoginButton facebookLoginButton;
    private AccessToken fbAccessToken;
    private AccessTokenTracker fbAccessTokenTracker;
    private LinearLayout linkedinLoginBtn;
    private EditText userNameEditText;
    private AnimationDrawable animationDrawable;
    private RelativeLayout relativeLayout;
    private String androidkey;
    private TextView tvSigninLinkedin;
    private Button btLinkedlogin;
    private final HashMap<String, String> valuesMap = new HashMap<>();
    private String personEmail;
    private String socialUniqueId;
    private PreferenceManager preferenceManager;
    private int checkstate;
    private ProgressDialog progressDialog;
    private DatabaseHandler databaseHandler;
    private String firstname;
    private String lastname;
    private String photoUrl;
    private String email;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fb_login);
         databaseHandler = new DatabaseHandler(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.dots.android",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e1)
        {
            Log.e("name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("no such an algorithm", e.toString());
        }
        catch (Exception e)
        {
            Log.e("exception", e.toString());
        }
         preferenceManager = new PreferenceManager(this);
        checkstate = preferenceManager.getPreferenceIntValues(ApplicationData.STATE);
        if(checkstate==1){
            Intent intent = new Intent(TabActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = (com.facebook.login.widget.LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        showProgressDialog();
                        //fbProfile = Profile.getCurrentProfile();
//                        Utils.setSharedStringPreference(Constants.PrefName.STATE,SingInActivity.class.getSimpleName());
                        fbAccessToken = loginResult.getAccessToken();
                        Log.d(TabActivity.class.getSimpleName(),"checkid:"+fbAccessToken);
                        final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        //Log.e("LoginActivity", response.toString());
                                        try {
                                            if(object.has("id")){
                                                photoUrl = "http://graph.facebook.com/" + object.getString("id") +
                                                        "/picture?type=large";
                                                /*Utils.setSharedStringPreference(Constants.PrefName.USER_PHOTO_URI, photoUrl);
                                                Utils.setSharedStringPreference(Constants.PrefName.SIGN_UP_PICTURE_URL, photoUrl);
                                                valuesMap.put(Constants.IntentName.SIGN_UP_PICTURE_URL, photoUrl);
                                                valuesMap.put(Constants.IntentName.SIGN_UP_SOCIAL_UNIQUE_ID, object.getString("id"));*/

                                                Log.i("user_photo", photoUrl);
                                                socialUniqueId = object.getString("id");
//                                                Utils.setSharedStringPreference(Constants.PrefName.SIGN_UP_SOCIAL_UNIQUE_ID,socialUniqueId);

                                            }
                                            //object.getString("id");
                                            if(object.has("email")){
                                                email = object.getString("email");
                                               /* Utils.setSharedStringPreference(Constants.PrefName.USER_REGISTERED_EMAIL,
                                                        object.getString("email"));
                                                valuesMap.put(Constants.IntentName.SIGN_UP_EMAIL_ID, object.getString("email"));
                                                Utils.setSharedStringPreference(Constants.PrefName.SIGN_UP_EMAIL_ID, object.getString("email"));*/

                                            }
                                            if(object.has("first_name")){
                                                firstname = object.getString("first_name");
                                              /*  valuesMap.put(Constants.IntentName.SIGN_UP_FIRST_NAME, object.getString("first_name"));
                                                Utils.setSharedStringPreference(Constants.PrefName.SIGN_UP_FIRST_NAME,object.getString("first_name"));*/

                                            }
                                            if(object.has("last_name")){
                                                lastname = object.getString("last_name");
                                              /*  valuesMap.put(Constants.IntentName.SIGN_UP_LAST_NAME, object.getString("last_name"));
                                                Utils.setSharedStringPreference(Constants.PrefName.SIGN_UP_LAST_NAME,object.getString("last_name"));*/

                                            }
                                            if(object.has("gender")){
                                                 gender = object.getString("gender");
                                                if(gender.equalsIgnoreCase("m")){
                                                    gender = getString(R.string.male);
                                                }
                                                else{
                                                    gender = getString(R.string.female);
                                                }
//                                                valuesMap.put(Constants.IntentName.SIGN_UP_GENDER, gender);
                                            }
//                                            Utils.setSharedStringPreference(Constants.PrefName.LOGIN_MODE, Constants.LOGIN_MODE_FACEBOOK);
//                                            Utils.setSharedStringPreference(Constants.PrefName.LOGIN_CHECK, Constants.LOGIN_MODE_FACEBOOK);
                                                databaseHandler.addUserInfo(new UserInfo(firstname,lastname,photoUrl,email,gender));
                                            callLoginService();
                                        } catch (JSONException e) {
                                            Log.e(TabActivity.class.getSimpleName(), e.getMessage(), e);
                                        }
                                    }
                                });

                        final Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email, birthday"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {}

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(TabActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );


    }

    private void showProgressDialog() {
        if(progressDialog != null && progressDialog.isShowing()){
            return;
        }
        progressDialog = ProgressDialog.show(this, getString(R.string.loading), getString(R.string.please_wait));
    }

    private void callLoginService() {
        hideLoadingDialog();
        Intent intent = new Intent(TabActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void hideLoadingDialog() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

}
