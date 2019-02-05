package com.example.mehmood.splitbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.Utills.SplitBillUtility;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginFragment;
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

import static android.content.Context.MODE_PRIVATE;


public class LogInFragment extends Fragment {
    int RC_SIGN_IN = 0;    //Google Request code
    GoogleSignInClient mGoogleSignInClient;  // Google sign in client
    private LoginButton loginButton;   // facebook login button
    private CallbackManager callbackManager; // facebook callbackManager


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);



        // Set the dimensions of the sign-in button.
        SignInButton googleSignInButton = view.findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
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
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", "Completed");
                        storeData(object);
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "email,id,first_name");
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


        return view;
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
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            // Handle here for login failed
        }
    }

    // END OF GOOGLE SIGNIN



    //Here we will call An Api to store the data on Our Server.//
    private void storeData(JSONObject jsonObject) {
        Log.e("msg", jsonObject.toString());
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            editor.putString(getString(R.string.Name), jsonObject.get("first_name").toString());
            editor.putString(getString(R.string.email), jsonObject.get("email").toString());
            editor.putString(getString(R.string.id), jsonObject.get("id").toString());
            Log.e("email",jsonObject.get("email").toString());
            Log.e("name",jsonObject.get("first_name").toString());
            Log.e("id",jsonObject.get("id").toString());

            /*we can use the following url to retrive the user profile pic.

             * graph.facebook.com/<facebook_user_id>/picture?type=large
             *
             * */

        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.commit();

        //close the login fragment after storing data.
        Fragment fragment = getFragmentManager().findFragmentByTag(LoginFragment.class.getSimpleName());
        if(fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
        EventListFragment eventListFragment = new EventListFragment();
        SplitBillUtility.inflateFragment(eventListFragment, getActivity().getSupportFragmentManager(), R.id.fragementContainer, false, true, null);

    }

}
