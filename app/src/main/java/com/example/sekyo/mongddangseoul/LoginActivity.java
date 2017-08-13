package com.example.sekyo.mongddangseoul;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static String address = "http://sekyo.cafe24app.com";

    Button loginButton;
    Button facebookLoginButton;
    Button signUpButton;
    EditText idText;
    EditText pwText;
    CheckBox autoLogin;

    SharedPreferences sharedPreferences;

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private List<String> permissionNeeds = Arrays.asList("email");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        facebookLoginButton = (Button) findViewById(R.id.login_facebook_button);
        signUpButton = (Button) findViewById(R.id.sign_up_btn);
        idText = (EditText) findViewById(R.id.login_id);
        pwText = (EditText) findViewById(R.id.login_pw);
        autoLogin = (CheckBox) findViewById(R.id.checkBox);


        //////////////////////////////////FACEBOOK//////////////////////////////////////////////////
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
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
        ////////////////////////////////////////////////////////////////////////////////////////////

        //Auto Login check
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id");
                    editor.remove("pw");
                    editor.commit();
                }
            }
        });

        if (sharedPreferences.getString("id", null) != null) {
            idText.setText(sharedPreferences.getString("id", null));
            pwText.setText(sharedPreferences.getString("pw", null));
            autoLogin.setChecked(true);
        }


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getApplicationContext())
                        .load(address + "/sign_in")
                        .setMultipartParameter("id", idText.getText().toString())
                        .setMultipartParameter("pw", pwText.getText().toString())
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                if (e == null) {
                                    if (result.equals("ok")) {
                                        if(autoLogin.isChecked()) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("id", idText.getText().toString());
                                            editor.putString("pw", pwText.getText().toString());
                                            editor.commit();
                                        }
                                        LoginInfo.id = idText.getText().toString();
                                        LoginInfo.pw = pwText.getText().toString();

                                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.equals("no_id")) {
                                        Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_LONG).show();
                                    } else if (result.equals("wrong_pw")) {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                    } else if (result.equals("db_error")) {
                                        Toast.makeText(getApplicationContext(), "서버 데이터베이스에 문제가 발생했습니다.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "네트워크 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

}
