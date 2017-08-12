package com.example.sekyo.mongddangseoul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class SignUpActivity extends AppCompatActivity {
    EditText idText;
    EditText pwText;
    EditText pwCheckText;
    EditText nameText;
    EditText phoneText;
    EditText mailText;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        idText = (EditText) findViewById(R.id.sign_up_id);
        pwText = (EditText) findViewById(R.id.sign_up_pw);
        pwCheckText = (EditText) findViewById(R.id.sign_up_pw_check);
        nameText = (EditText) findViewById(R.id.sign_up_name);
        phoneText = (EditText) findViewById(R.id.sign_up_phone);
        mailText = (EditText) findViewById(R.id.sign_up_mail);

        signUpButton = (Button) findViewById(R.id.signup_now);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getApplicationContext())
                        .load(LoginActivity.address + "/sign_up")
                        .setMultipartParameter("id",idText.getText().toString())
                        .setMultipartParameter("pw",pwText.getText().toString())
                        .setMultipartParameter("name",nameText.getText().toString())
                        .setMultipartParameter("phone",phoneText.getText().toString())
                        .setMultipartParameter("mail",mailText.getText().toString())
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                if(e == null) {
                                    if(result.equals("ok")){
                                        Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else if (result.equals("exist_id")){
                                        Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show();
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
