package com.hpzhang.htablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义TabLayout
 * @author hpzhang
 */
public class MainActivity extends AppCompatActivity {

    MyTabLayout myTabLayout;
    String[] tags = new String[] {"测试","Java数据结构和算法", "C++", "数据结构","计算机","王者荣耀", "今天天气真的很不错啊", "哪里","青花瓷", "机锋网io节覅偶尔为奇偶覅问佛危机", "我擦。。。"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTabLayout = findViewById(R.id.myTabLayout);
        int margin = 20;

        for (int i = 0; i < tags.length; i++) {
            TextView tv = new TextView(this);
            tv.setTextColor(Color.BLACK);
            tv.setText(tags[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            tv.setLayoutParams(params);

            tv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            myTabLayout.addView(tv);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    Toast.makeText(MainActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
