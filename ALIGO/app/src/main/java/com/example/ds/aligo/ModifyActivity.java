package com.example.ds.aligo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ModifyActivity extends AppCompatActivity {
    EditText name, id, birth, password, passwordCheck, phone;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_modify);

        pref = getSharedPreferences("user", MODE_PRIVATE);
        editor = pref.edit();

        name = (EditText)findViewById(R.id.modifyName);
        id = (EditText)findViewById(R.id.modifyId);
        birth = (EditText)findViewById(R.id.modifyBirth);
        password = (EditText)findViewById(R.id.modifyPassword);
        passwordCheck = (EditText)findViewById(R.id.modifyPasswordCheck);
        phone = (EditText)findViewById(R.id.modifyPhone);

        birth.setText(pref.getString("생년월일",""));
        id.setText(pref.getString("아이디",""));
    }

    public void onModifyButton(View view) {
        //정보 저장하고 마이페이지로 돌아가기
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("수정되었습니다.");
        alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if(name.getText().toString().equals("")||birth.getText().toString().equals("")||id.getText().toString().equals("")||
                        password.getText().toString().equals("")||passwordCheck.getText().toString().equals("")||phone.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "입력되지 않은 정보가 있습니다.",Toast.LENGTH_LONG).show();
                }

                else if(!Pattern.matches("^[A-Za-z0-9]*.{6,20}$", password.getText().toString()))
                    Toast.makeText(getApplicationContext(),"비밀번호형식이 맞지 않습니다..",Toast.LENGTH_SHORT).show();

                else if(!(password.getText().toString().equals(passwordCheck.getText().toString())))
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();

                else if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phone.getText().toString()))
                    Toast.makeText(getApplicationContext(),"올바른 핸드폰 번호가 아닙니다.",Toast.LENGTH_SHORT).show();

                else {
                    editor.remove("이름");
                    editor.remove("비밀번호");
                    editor.remove("핸드폰");

                    editor.putString("이름", name.getText().toString());
                    editor.putString("비밀번호", password.getText().toString());
                    editor.putString("핸드폰", phone.getText().toString());
                    editor.commit();
                }
                Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
