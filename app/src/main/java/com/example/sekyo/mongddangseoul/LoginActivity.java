package com.example.sekyo.mongddangseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    Button facebookLoginButton;
    Button signUpButton;
    EditText idText;
    EditText pwText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.login_button);
        facebookLoginButton = (Button)findViewById(R.id.login_facebook_button);
        signUpButton = (Button) findViewById(R.id.sign_up_btn);
        idText = (EditText) findViewById(R.id.login_id);
        pwText = (EditText) findViewById(R.id.login_pw);

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
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
