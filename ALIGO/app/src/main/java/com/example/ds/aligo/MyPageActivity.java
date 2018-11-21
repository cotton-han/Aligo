package com.example.ds.aligo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MyPageActivity extends AppCompatActivity {
    TextView email, name, phone;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        email = (TextView)findViewById(R.id.userEmail);
        name = (TextView)findViewById(R.id.userName);
        phone = (TextView)findViewById(R.id.userPhone);

        pref = getSharedPreferences("user", MODE_PRIVATE);
        name.setText(pref.getString("이름","OO")+"님");
        email.setText(pref.getString("아이디","없음"));

        StringBuffer sb= new StringBuffer(pref.getString("핸드폰","없음"));
        sb.insert(3,"-");
        sb.insert(8,"-");

        phone.setText(sb.toString());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,Menu.NONE,"홈");
        menu.add(0,1,Menu.NONE,"마이페이지");
        menu.add(0,2,Menu.NONE,"설정");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent); break;
            case 1:
                Intent intent2 = new Intent(getApplicationContext(), MyPageActivity.class); //마이페이지 화면으로 이동
                startActivity(intent2); break;
            case 2:
                Intent intent3 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent3); break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onLogoutButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onWithdrawButtonClicked(View view) {
        //회원정보 삭제
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원탈퇴");
        builder.setMessage("정말 회원탈퇴를 하시겠습니까?\n기존정보가 모두 사라집니다.");
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                //회원기록삭제
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onModifyButtonClicked(View view) {
        //비밀번호 확인 다이얼로그
        PasswordCheckDialog dialog = new PasswordCheckDialog(this,pref.getString("비밀번호",null));
        dialog.show();
    }

    public void onVersionButtonClicked(View view) {
        //다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("버전 정보는 V.2018.06.15");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onSettingButtonClicked(View view) {
        //설정페이지로 이동
        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }

    public void onInquiryButtonClicked(View view) {
        //전화연결
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이용문의");
        builder.setMessage("서비스센터에 연결하시겠습니까?");
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-123-4567"));
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
