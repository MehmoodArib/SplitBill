package com.example.mehmood.splitbill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.mehmood.splitbill.Utills.SharedPreferencesUtility;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LogInActivity extends AppCompatActivity {
    int RC_SIGN_IN = 0;    //Google Request code
    GoogleSignInClient mGoogleSignInClient;  // Google sign in client
    private LoginButton loginButton;   // facebook login button
    private CallbackManager callbackManager; // facebook callbackManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        // Set the dimensions of the sign-in button.
        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });


        // facebook Login
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            object.put("ProfileUrl", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        storeData(object);
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "email,id,first_name,picture.type(large)");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        // End of Facebook Login


    }

    //Google SignIn
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            JSONObject obj = new JSONObject();
            try {
                obj.put("ProfileUrl", account.getPhotoUrl());
                obj.put("email", account.getEmail());
                obj.put("id", account.getId());
                obj.put("first_name", account.getDisplayName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            storeData(obj);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
            // Handle here for login failed
        }
    }

    // END OF GOOGLE SIGN IN


    //Here we will call An Api to store the data on Our Server.//
    private void storeData(JSONObject jsonObject) {
        Context context = this;
        try {
            SharedPreferencesUtility.getInstance(context).put(SharedPreferencesUtility.Key.name, jsonObject.get("first_name").toString());
            SharedPreferencesUtility.getInstance(context).put(SharedPreferencesUtility.Key.email, jsonObject.get("email").toString());
            SharedPreferencesUtility.getInstance(context).put(SharedPreferencesUtility.Key.id, jsonObject.get("id").toString());
            SharedPreferencesUtility.getInstance(context).put(SharedPreferencesUtility.Key.profileUrl, jsonObject.get("ProfileUrl").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
