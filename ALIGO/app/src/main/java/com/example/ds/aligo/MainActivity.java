package com.example.ds.aligo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText loginId, loginPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        setContentView(R.layout.activity_main);

        loginId = (EditText)findViewById(R.id.loginId);
        loginPass=(EditText)findViewById(R.id.loginPass);

        loginId.setText("");
        loginPass.setText("");
    }

    public void onPasswordFindButtonClicked(View view) {
        FindPasswordDialog findPasswordDialog = new FindPasswordDialog(this);
        findPasswordDialog.show();
    }

    public void onLoginButton(View view) {
        SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
        String id = pref.getString("아이디",null);
        String password = pref.getString("비밀번호",null);

        if(loginId.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_LONG).show();
        else if(loginPass.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요.",Toast.LENGTH_LONG).show();
        else if(loginId.getText().toString().equals(id)&&loginPass.getText().toString().equals(password)){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"아이디와 비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
            loginPass.setText("");
            loginId.setText("");
        }
    }

    public void onJoinButton(View view) {
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
    }
}
