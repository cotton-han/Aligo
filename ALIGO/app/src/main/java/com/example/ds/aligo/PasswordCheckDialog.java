package com.example.ds.aligo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordCheckDialog extends Dialog {
    Context context;
    Button ok, cancel;
    String password;
    EditText passwordCheck;

    public PasswordCheckDialog(Context context, String password){
        super(context);
        this.context=context;
        this.password = password;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_passwordcheck);

        final Dialog dialog = this;

        passwordCheck = (EditText)findViewById(R.id.passwordCheck);
        cancel = (Button)findViewById(R.id.cancel);
        ok = (Button)findViewById(R.id.ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordCheck.getText().toString().equals(password)){
                    Intent intent = new Intent(context.getApplicationContext(), ModifyActivity.class);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context, "비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }
}
