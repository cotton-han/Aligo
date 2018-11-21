package com.example.ds.aligo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ListView cardListview;
    ListView cardImListview;
    ArrayList <String>list;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardListview = (ListView)findViewById(R.id.cardListview);
        cardImListview = (ListView)findViewById(R.id.cardImListview);

        list = new ArrayList<String>();
        //리스트뷰에 어댑터생성과 설정작업->삭제버튼구현까지 해야함
    }

    public void onAddCardClicked(View view) {
        Intent intent = new Intent(getApplicationContext(),CardActivity.class);
        startActivity(intent);
    }

    @Override
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

    public void onDeleteCardClicked(View view) {

    }

}
