package com.example.sekyo.mongddangseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    Button facebookLoginButton;
    Button signUpButton;
    EditText idText;
    EditText pwText;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private List<String> permissionNeeds = Arrays.asList("email");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        setContentView(R.layout.activity_login);
        /*자동로그인시 바로 넘어가게해놓음*/
//        if (AccessToken.getCurrentAccessToken() != null) { //이미 로그인 여부 확인//
//            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//            startActivity(intent);
//            finish();
//            Log.d("Tag", "user id : " + AccessToken.getCurrentAccessToken().getUserId());
//        } else {
//            Log.d("Tag", "로그인을 하세요");
//        }
        callbackManager = CallbackManager.Factory.create();
        loginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Tag", "Login 성공");
                Log.d("Tag", "user id : " + permissionNeeds.get(0));
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.d("Tag", "Login 취소");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Tag", "Login 실패" + error.getLocalizedMessage());
            }
        });
        loginButton = (Button) findViewById(R.id.login_button);
        facebookLoginButton = (Button) findViewById(R.id.login_facebook_button);
        signUpButton = (Button) findViewById(R.id.sign_up_btn);
        idText = (EditText) findViewById(R.id.login_id);
        pwText = (EditText) findViewById(R.id.login_pw);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.getInstance().logInWithReadPermissions(LoginActivity.this, permissionNeeds);
                if (AccessToken.getCurrentAccessToken() != null) { //이미 로그인 여부 확인//
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);

                    finish();

                    Log.d("Tag", "user id : " + AccessToken.getCurrentAccessToken().getToken());
                    Log.d("Tag", "user id : " + permissionNeeds.get(1));
                } else {
                    Log.d("Tag", "로그인을 하세요");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
